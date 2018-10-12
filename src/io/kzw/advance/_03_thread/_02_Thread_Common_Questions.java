package io.kzw.advance._03_thread;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Java多线程.
 *
 * @author kzw on 2018/09/15.
 */
public final class _02_Thread_Common_Questions {

    /*
     * 1. 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
     */
    private static void test01() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "执行完毕");
            }
        };
        Thread thread1 = new Thread(runnable, "t1");
        Thread thread2 = new Thread(runnable, "t2");
        Thread thread3 = new Thread(runnable, "t3");
        try {
            /*
             * Thread[t1,5,main]执行完毕
             * Thread[t2,5,main]执行完毕
             * Thread[t3,5,main]执行完毕
             */
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
            thread3.start();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * 2. 在Java中Lock接口比synchronized块的优势是什么？
     * 你需要实现一个高效的缓存，它允许多个用户读，但只允许一个用户写，以此来保持它的完整性，你会怎样去实现它？
     *
     * 整体上来说Lock是synchronized的扩展版，Lock提供了无条件的、可轮询的(tryLock方法)、定时的(tryLock带参方法)、
     * 可中断的(lockInterruptibly)、可多条件队列的(newCondition方法)锁操作。
     *
     * 另外Lock的实现类基本都支持非公平锁(默认)和公平锁，synchronized只支持非公平锁，当然，在大部分情况下，非公平锁是高效的选择。
     */
    private static class ReaderAndWriter<K, V> {

        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        private final Map<K, V> map;

        ReaderAndWriter() {
            map = new HashMap<>();
        }

        // ---------------------   这是用lock()方法写的  ---------------------
        V put(K key, V value) {
            writeLock.lock();
            try {
                return map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        V get(K key) {
            readLock.lock();
            try {
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }

        // ---------------------   这是用tryLock()方法写的  ---------------------
        V putTrylock(K key, V value) {
            while (true) {
                writeLock.tryLock();
                try {
                    return map.put(key, value);
                } finally {
                    writeLock.unlock();
                }
            }
        }

        V getTrylock(K key) {
            while (true) {
                readLock.lock();
                try {
                    return map.get(key);
                } finally {
                    readLock.unlock();
                }
            }
        }

        static void test() {
            final ReaderAndWriter<String, Integer> cache = new ReaderAndWriter<>();
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < 100; i++) {
                exec.execute(() -> {
                    Random random = new Random();
                    int r = random.nextInt(100);
                    // 生成随机数，小于30的写入缓存，大于等于30则读取数字
                    if (r < 30) {
                        cache.put("x", r);
                    } else {
                        cache.get("x");
                    }
                });
            }
            exec.shutdown();
        }
    }

    /*
     * 3. 用Java实现阻塞队列
     *
     * 这是一个相对艰难的多线程面试问题，它能达到很多的目的。
     * 第一，它可以检测侯选者是否能实际的用Java线程写程序；
     * 第二，可以检测侯选者对并发场景的理解，并且你可以根据这个问很多问题。
     *
     * 如果他用wait()和notify()方法来实现阻塞队列，你可以要求他用最新的Java 5中的并发类来再写一次。
     */
    private static class MyBlocingQueue<E> {

        private final List<E> list;
        // 有大小限制的
        private final int limit;

        MyBlocingQueue(int limit) {
            list = new LinkedList<>();
            this.limit = limit;
        }

        // 用wait，notify写的,在list空或者满的时候效率会高一点，因为wait释放锁，然后等待唤醒
        private synchronized void put(E e) {
            while (list.size() == limit) {
                try {
                    wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            System.out.println("list : " + list.toString());
            System.out.println("put : " + e);
            list.add(e);
            notifyAll();
        }

        private synchronized E take() {
            while (list.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("list : " + list.toString());
            E remove = list.remove(0);
            System.out.println("take : " + remove);
            notifyAll();
            return remove;
        }

        // 新版实现
        private final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        private void putNew(E e) {
            lock.lock();
            try {
                while (list.size() == limit) {
                    try {
                        notFull.await();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                list.add(e);
                notEmpty.signalAll();
            } finally {
                lock.unlock();
            }
        }

        private E takeNew() {
            lock.lock();
            try {
                while (list.size() == 0) {
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                E remove = list.remove(0);
                notFull.signalAll();
                return remove;
            } finally {
                lock.unlock();
            }
        }

        static void test() {
            final MyBlocingQueue<Integer> myBlocingQueue = new MyBlocingQueue(10);
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < 100; i++) {
                exec.execute(() -> {
                    Random random = new Random();
                    int r = random.nextInt(100);
                    // 生成随机数,按照一定比率读取或者放入！
                    if (r < 30) {
                        myBlocingQueue.put(r);
                    } else {
                        myBlocingQueue.take();
                    }
                });
            }
            exec.shutdown();
        }
    }

    /*
     * 4. 用Java写代码来解决生产者——消费者问题
     *
     * 与上面的问题很类似，但这个问题更经典，有些时候面试都会问下面的问题。在Java中怎么解决生产者——消费者问题，当然有很多解决方法，
     * 我已经分享了一种用阻塞队列实现的方法。有些时候他们甚至会问怎么实现哲学家进餐问题。
     *
     * 生产者、消费者有很多的实现方法：
     * - 用wait() / notify()方法
     * - 用Lock的多Condition方法
     * - BlockingQueue阻塞队列方法
     *
     * 可以发现在上面实现阻塞队列题中，BlockingQueue的实现基本都用到了类似的实现，将BlockingQueue的实现方式稍微包装一下就成了一个生产者-消费者模式了。
     */
    private static class ProduceAndConsumer {

        private static class Produce extends Thread {

            private MyBlocingQueue<Integer> queue;

            Produce(MyBlocingQueue queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        int i = new Random().nextInt(100);
                        queue.put(i);
                        System.out.println("生产数据：" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private static class Consumer extends Thread {

            private MyBlocingQueue<Integer> queue;

            Consumer(MyBlocingQueue queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                // 开启无限循环
                while (true) {
                    try {
                        int take = queue.take();
                        System.out.println("消费数据：" + take);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        static void test() {
            MyBlocingQueue<Integer> queue = new MyBlocingQueue<>(10);
            Produce produce = new Produce(queue);
            produce.start();
            Consumer consumer = new Consumer(queue);
            consumer.start();
        }
    }

    /*
     * 5. 用Java写一个会导致死锁的程序，你将怎么解决？
     *
     * 这是我最喜欢的Java线程面试问题，因为即使死锁问题在写多线程并发程序时非常普遍，
     * 但是很多侯选者并不能写deadlock free code（无死锁代码？），他们很挣扎。
     * 只要告诉他们，你有N个资源和N个线程，并且你需要所有的资源来完成一个操作。
     * 为了简单这里的n可以替换为2，越大的数据会使问题看起来更复杂。通过避免Java中的死锁来得到关于死锁的更多信息。
     *
     * 产生死锁的四个必要条件：
     * - 互斥条件：一个资源每次只能被一个进程使用。
     * - 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
     * - 不剥夺条件:进程已获得的资源，在末使用完之前，不能强行剥夺。
     * - 循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。
     *
     * 如何避免死锁？
     * - 从死锁的四个必要条件来看，破坏其中的任意一个条件就可以避免死锁。
     * 但互斥条件是由资源本身决定的，不剥夺条件一般无法破坏，要实现的话得自己写更多的逻辑。
     * - 避免无限期的等待：用Lock.tryLock(),wait/notify等方法写出请求一定时间后，放弃已经拥有的锁的程序。
     * - 注意锁的顺序：以固定的顺序获取锁，可以避免死锁。
     * - 开放调用：即只对有请求的进行封锁。你应当只想你要运行的资源获取封锁，比如在上述程序中我在封锁的完全的对象资源。
     * 但是如果我们只对它所属领域中的一个感兴趣，那我们应当封锁住那个特殊的领域而并非完全的对象。
     * - 最后，如果能避免使用多个锁，甚至写出无锁的线程安全程序是再好不过了。
     */
    private static class SimpleDeadLock {

        private static class A extends Thread {

            private final Object lock1;
            private final Object lock2;

            A(Object lock1, Object lock2) {
                this.lock1 = lock1;
                this.lock2 = lock2;
            }

            @Override
            public void run() {
                // A先拿到了lock1锁
                synchronized (lock1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 休眠1秒后，尝试拿lock2锁，但是lock2锁被B持有无法释放，而B在等待A释放lock1锁才能继续走
                    synchronized (lock2) {
                        System.out.println("Hello A");
                    }
                }
            }
        }

        private static class B extends Thread {

            private final Object lock1;
            private final Object lock2;

            B(Object lock1, Object lock2) {
                this.lock1 = lock1;
                this.lock2 = lock2;
            }

            @Override
            public void run() {
                // B先拿到了lock2锁
                synchronized (lock2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 休眠1秒后，尝试拿lock1锁
                    synchronized (lock1) {
                        System.out.println("Hello B");
                    }
                }
            }
        }

        static void test() {
            final Object lock1 = new Object();
            final Object lock2 = new Object();
            A a = new A(lock1, lock2);
            a.start();
            B b = new B(lock1, lock2);
            b.start();
        }
    }

    /*
     * 6. 什么是原子操作，Java中的原子操作是什么？
     *
     * 非常简单的java线程面试问题，接下来的问题是你是否需要同步一个原子操作。
     *
     * 原子操作是不可分割的操作，一个原子操作中间是不会被其他线程打断的，所以不需要同步一个原子操作。
     * 多个原子操作合并起来后就不是一个原子操作了，就需要同步了。
     *
     * i++不是一个原子操作，它包含 读取-修改-写入 操作，在多线程状态下是不安全的。
     *
     * 另外，java内存模型允许将64位的读操作或写操作分解为2个32位的操作，所以对long和double类型的单次读写操作并不是原子的，
     * 注意使用volitile使他们成为原子操作??，应该只是保证可见性。
     */

    /*
     * 7. Java中的volatile关键是什么作用？怎样使用它？在Java中它跟synchronized方法有什么不同？
     *
     * 自从Java 5和Java内存模型改变以后，基于volatile关键字的线程问题越来越流行。
     * 应该准备好回答关于volatile变量怎样在并发环境中确保可见性。
     *
     * volatile关键字的作用是：保证变量的可见性。
     * 在java内存结构中，每个线程都是有自己独立的内存空间(此处指的线程栈)。
     * 当需要对一个共享变量操作时，线程会将这个数据从主存空间复制到自己的独立空间内进行操作，然后在某个时刻将修改后的值刷新到主存空间。
     * 这个中间时间就会发生许多奇奇怪怪的线程安全问题了，volatile就出来了，它保证读取数据时只从主存空间读取，
     * 修改数据直接修改到主存空间中去，这样就保证了这个变量对多个操作线程的可见性了。换句话说，被volatile修饰的变量，
     * 能保证该变量的 单次读或者单次写 操作是原子的。
     *
     * 但是线程安全是两方面需要的 原子性(指的是多条操作)和可见性。
     * volatile只能保证可见性，synchronized是两个均保证的。
     * volatile轻量级，只能修饰变量；synchronized重量级，还可修饰方法。
     * volatile不会造成线程的阻塞，而synchronized可能会造成线程的阻塞。
     *
     *
     * ####### java volatile不能保证原子性 #######
     * Java中long和double赋值不是原子操作，因为先写32位，再写后32位，分两步操作,这样就线程不安全了。如果改成下面的就线程安全了
     *
     * private volatile long number = 8;
     * 那么，为什么是这样？volatile关键字难道可以保证原子性？
     *
     * java程序员很熟悉的一句话：volatile仅仅用来保证该变量对所有线程的可见性，但不保证原子性。
     * 但是我们这里的例子，volatile似乎是有时候可以代替简单的锁，似乎加了volatile关键字就省掉了锁。这不是互相矛盾吗？
     *
     * 其实如果一个变量加了volatile关键字，就会告诉编译器和JVM的内存模型：这个变量是对所有线程共享的、可见的，
     * 每次jvm都会读取最新写入的值并使其最新值在所有CPU可见。所以说的是线程可见性，没有提原子性。
     */

    /*
     * 8. 什么是竞争条件(race condition)？你怎样发现和解决的？
     *
     * 这是一道出现在多线程面试的高级阶段的问题。大多数的面试官会问最近你遇到的竞争条件，以及你是怎么解决的。
     * 有些时间他们会写简单的代码，然后让你检测出代码的竞争条件。可以参考我之前发布的关于Java竞争条件的文章。
     * 在我看来这是最好的java线程面试问题之一，它可以确切的检测候选者解决竞争条件的经验。关于这方面最好的书是《java并发编程实战》。
     *
     * 竞争条件，在《java并发编程实战》叫做竞态条件：指设备或系统出现不恰当的执行时序，而得到不正确的结果。
     *
     * 如何对待竞态条件？
     * 首先，警惕复合操作，当多个原子操作合在一起的时候，并不一定仍然是一个原子操作，此时需要用同步的手段来保证原子性。
     * 另外，使用本身是线程安全的类，这样在很大程度上避免了未知的风险。
     */
    private static class LazyInitRace {

        private _02_Thread_Common_Questions instance = null;

        public _02_Thread_Common_Questions getInstance() {
            /*
             * 一种很常见的竞态条件类型:“先检查后执行”。根据某个检查结果来执行进一步的操作，但很有可能这个检查结果是失效的！
             * 还有很常见的竞态条件“读取-修改-写入”三连，在多线程条件下，三个小操作并不一定会放在一起执行的。
             */
            if (instance == null) {
                instance = new _02_Thread_Common_Questions();
            }
            return instance;
        }
    }

    /*
     * 9. 为什么我们调用start()方法时会执行run()方法，为什么我们不能直接调用run()方法？
     *
     * 这是另一个非常经典的java多线程面试问题。这也是我刚开始写线程程序时候的困惑。
     * 现在这个问题通常在电话面试或者是在初中级Java面试的第一轮被问到。这个问题的回答应该是这样的，
     * 当你调用start()方法时你将创建新的线程，并且执行在run()方法里的代码。但是如果你直接调用run()方法，
     * 它不会创建新的线程也不会执行调用线程的代码。
     *
     * 简单点来说：
     * new一个Thread，线程进入了新建状态;调用start()方法，线程进入了就绪状态，当分配到时间片后就可以开始运行了。
     * start()会执行线程的相应准备工作，然后自动执行run()方法的内容。是真正的多线程工作。
     * 而直接执行run()方法，会把run方法当成一个main线程下的普通方法去执行，并不会在某个线程中执行它，这并不是多线程工作。
     */

    /*
     * 10. 在Java中CycliBarriar和CountdownLatch有什么区别？
     *
     * 这个线程问题主要用来检测你是否熟悉JDK5中的并发包。这两个的区别是CyclicBarrier可以重复使用已经通过的障碍，
     * 而CountdownLatch不能重复使用。
     *
     * 还要注意一点的区别：
     * CountDownLatch : 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行。
     * CyclicBarrier : N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
     *
     * 这样应该就清楚一点了，对于CountDownLatch来说，重点是那个“一个线程”, 是它在等待，而另外那N的线程在把“某个事情”做完之后可以继续等待，
     * 可以终止。而对于CyclicBarrier来说，重点是那N个线程，他们之间任何一个没有完成，所有的线程都必须等待。
     * 从api上理解就是CountdownLatch有主要配合使用两个方法countDown()和await()，countDown()是做事的线程用的方法，
     * await()是等待事情完成的线程用个方法，这两种线程是可以分开的(下面例子:CountdownLatchTest2)，
     * 当然也可以是同一组线程(下面例子:CountdownLatchTest);CyclicBarrier只有一个方法await(),
     * 指的是做事线程必须大家同时等待，必须是同一组线程的工作。
     */
    private static class CountdownLatchTest {

        private final static int THREAD_NUM = 10;

        private static class CountdownLatchTask implements Runnable {

            private final CountDownLatch countDownLatch;
            private final String threadName;

            CountdownLatchTask(CountDownLatch countDownLatch, String threadName) {
                this.countDownLatch = countDownLatch;
                this.threadName = threadName;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " 执行完毕");
                countDownLatch.countDown();
            }
        }

        static void test() {
            CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < THREAD_NUM; i++) {
                exec.execute(new CountdownLatchTask(countDownLatch, "thread - " + i));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("大家都执行完成了，做总结性工作");
            exec.shutdown();
        }
    }

    private static class CyclicBarrierTest {

        private final static int THREAD_NUM = 10;

        private static class CountdownLatchTask implements Runnable {
            private final CyclicBarrier lock;
            private final String threadName;

            CountdownLatchTask(CyclicBarrier lock, String threadName) {
                this.lock = lock;
                this.threadName = threadName;
            }

            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(threadName + " 准备完成");
                    try {
                        lock.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(threadName + " 执行完成");
                }
            }
        }

        static void test() {
            CyclicBarrier lock = new CyclicBarrier(THREAD_NUM, () -> {
                System.out.println("大家都准备完成了");
            });
            ExecutorService exec = Executors.newCachedThreadPool();
            for (int i = 0; i < THREAD_NUM; i++) {
                exec.submit(new CountdownLatchTask(lock, "Thread-" + i));
            }
            exec.shutdown();
        }
    }

    public static void main(String[] args) {
        // test01();
        // ReaderAndWriter.test();
        // MyBlocingQueue.test();
        // ProduceAndConsumer.test();
        // SimpleDeadLock.test();
        // CountdownLatchTest.test();
        // CyclicBarrierTest.test();
    }
}
