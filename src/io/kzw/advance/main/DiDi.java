package io.kzw.advance.main;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 滴滴出行.
 *
 * @author kuang on 2018/11/30.
 */
public class DiDi {

    public static void main(String[] args) {
        int[] array = new int[]{5, 4, 3, 2, 1};
        mergeSort(array);
    }

    /**
     * 1. 第一面和一个小哥聊了一些项目细节，啥难点，解决方案啥的，还有一些java基础，集合，数据库原理，
     * 索引，隔离级别啥的，还算不错，小哥人也很nice。
     * 二面像是一位领导的面试，问了我上一个实习干啥了，问了我当时写的一个定时任务，然后给我电脑手撸用Java中多线程实现定时器，
     * 手撸堆排序，手撸两个字符串比较，设计表+一个sql语句，确实二面很糟糕，堆排啥的确实忘了，我知道没戏了，
     * 但是很生气，让我带走自己的简历?好吧第一次遇到这种情况（这拒绝方式太委婉了）。
     */

    /*
     * 堆排序.
     * <p>
     * 堆是一种特殊的树形数据结构，其每个节点都有一个值，通常提到的堆都是指一颗完全二叉树，
     * 根结点的值小于（或大于）两个子节点的值，同时，根节点的两个子树也分别是一个堆。
     *
     * <pre>
     * -----------------------------------------------------------------------
     * 小根堆：
     *
     * 逻辑结构------------------
     *             10
     *      |             |
     *      15           56
     *   |     |       |
     *  25    30      70
     *
     * 存储结构-------------------
     * 10 15 25 30 56 70
     *
     * <><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
     *
     * 大根堆：
     *
     * 逻辑结构------------------
     *              70
     *     |                 |
     *    56                30
     *  |     |            |
     *  25    15           10
     *
     * 存储结构-------------------
     * 70 56 30 25 15 10
     *
     * -----------------------------------------------------------------------
     * </pre>
     *
     * <p>
     * 堆排序就是利用堆（假设利用大顶堆）进行排序的方法。
     * 它的基本思想是，将待排序的序列构造成一个大顶堆。此时，整个序列的最大值就是堆顶的根节点。
     * 将它移走（其实就是将其与堆数组的末尾元素交换，此时末尾元素就是最大值），然后将剩余的 n-1 个序列重新构造成一个堆，
     * 这样就会得到 n 个元素中次大的值。如此反复执行，便能得到一个有序序列了。
     * <p>
     * 堆排序的实现需要解决的两个关键问题：
     * （1）将一个无序序列构成一个堆。
     * （2）输出堆顶元素后，调整剩余元素成为一个新堆。
     *
     * <p>
     * 平均O(NlogN) | 最好O(NlogN) | 最坏O(NlogN) | 空间O(1) | 不稳定
     *
     * @param array 待排序数组
     */
    private static void heapSort(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        // build heap
        for (int i = len / 2 + 1; i >= 0; i--) {
            // 从最后一个非叶节点开始调整，使之满足最大堆
            adjustHeap(array, i, len);
        }
        // exchange and adjust
        for (int j = len - 1; j >= 0; j--) {
            // 交换根节点与最后一个节点
            int temp = array[0];
            array[0] = array[j];
            array[j] = temp;
            // 调整剩下的节点，使它们满足最大堆
            // j是当做长度传过去的，所以，去掉了最后一个节点
            adjustHeap(array, 0, j);
        }
        System.out.println("heap sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    private static void adjustHeap(int array[], int i, int len) {
        // 记录当前节点值（父节点）
        int temp = array[i];
        // 向下遍历子节点
        for (int k = 2 * i + 1; k < len; k = k * 2 + 1) {
            // 如果存在右子节点，且右子节点大于左子节点
            if (k + 1 < len && array[k] < array[k + 1]) {
                // 指向右子节点
                k++;
            }
            // 如果子节点比当前节点（父节点）大，则将子节点值赋给父节点
            if (array[k] > temp) {
                array[i] = array[k];
                // 记录父节点的位置，最终要落到的位置
                i = k;
            } else {
                // 如果子节点小于父节点，则调整结束
                break;
            }
        }
        // 将当前父节点的值，最终落位入座
        array[i] = temp;
    }

    /*
     * 两个字符串比较
     */
    public static void compareToString() {
        /**
         * 使用 String.compareTo 方法：
         * compareTo() 的返回值是int, 它是先比较对应字符的大小(ASCII码顺序)
         *
         * 1、如果字符串相等返回值0
         * 2、如果第一个字符和参数的第一个字符不等,结束比较,返回他们之间的差值（ascii码值）（负值前字符串的值小于后字符串，正值前字符串大于后字符串）
         * 3、如果第一个字符和参数的第一个字符相等,则以第二个字符和参数的第二个字符做比较,以此类推,直至比较的字符或被比较的字符有一方全比较完,这时就比较字符的长度.
         */
        String s1 = "abc";
        String s2 = "abcd";
        String s3 = "abcdfg";
        String s4 = "1bcdfg";
        String s5 = "cdfg";
        System.out.println(s1.compareTo(s2)); // -1 (前面相等,s1长度小1)
        System.out.println(s1.compareTo(s3)); // -3 (前面相等,s1长度小3)
        System.out.println(s1.compareTo(s4)); // 48 ("a"的ASCII码是97,"1"的的ASCII码是49,所以返回48)
        System.out.println(s1.compareTo(s5)); // -2 ("a"的ASCII码是97,"c"的ASCII码是99,所以返回-2)
    }

    /**
     * 2. 面试过程：
     * 面试上来发个笔试题 我都惊呆了 我以为拿错了卷子，其实就是笔试题，就两题 第一题是c的 ，第二题是输出二叉树。
     * 面试中主要都是问java很底层的东西，tcp/IP UDP协议啦，与服务器通讯的过程。进程间通讯的问题，java是怎么做到的。
     */

    /*
     * TCP/IP通信和UDP通信之间的区别
     *
     * https://blog.csdn.net/qq_28775437/article/details/73340713
     */

    /**
     * 3. 一面：Android基本知识点Java：线程、内存模型、JVM，双清委派模型，锁Android：进程间通讯、架构设计等；
     * 二面：主要针对项目中的一个点展开详细讨论，设计一个网络库算法：string转为float。
     * 面试官的问题：
     * 问Android Handler底层机制。
     * 答linux管道机制。
     */

    /*
     * 双清委派模型
     *
     * Java虚拟机先从最核心的API开始查找，防止不可信的类扮演被信任的类。
     *
     * 启动类加载器 Bootstrap ClassLoader：加载<JAVA_HOME>\lib目录下核心库
     *
     * 扩展类加载器 Extension ClassLoader：加载<JAVA_HOME>\lib\ext目录下扩展包
     *
     * 应用程序类加载器 Application ClassLoader：  加载用户路径(classpath)上指定的类库
     *
     *  双亲委派模型
     *
     * 双亲委派模型要求除顶层启动类加载器外其余类加载器都应该有自己的父类加载器；类加载器之间通过复用关系来复用父加载器的代码。
     *
     * 双亲委派模型工作工程：
     *
     * 1.当Application ClassLoader 收到一个类加载请求时，他首先不会自己去尝试加载这个类，而是将这个请求委派给父类加载器Extension ClassLoader去完成。
     *
     * 2.当Extension ClassLoader收到一个类加载请求时，他首先也不会自己去尝试加载这个类，而是将请求委派给父类加载器Bootstrap ClassLoader去完成。
     *
     * 3.如果Bootstrap ClassLoader加载失败(在<JAVA_HOME>\lib中未找到所需类)，就会让Extension ClassLoader尝试加载。
     *
     * 4.如果Extension ClassLoader也加载失败，就会使用Application ClassLoader加载。
     *
     * 5.如果Application ClassLoader也加载失败，就会使用自定义加载器去尝试加载。
     *
     * 6.如果均加载失败，就会抛出ClassNotFoundException异常。
     *
     *  双亲委派模型的实现过程：
     *
     * 实现双亲委派模型的代码都集中在java.lang.ClassLoader的loadClass()方法中：
     * 首先会检查请求加载的类是否已经被加载过；
     * 若没有被加载过：
     * 递归调用父类加载器的loadClass();
     * 父类加载器为空后就使用启动类加载器加载；
     * 如果父类加载器和启动类加载器均无法加载请求，则调用自身的加载功能。
     *
     * 双亲委派模型的优点：
     *
     * Java类伴随其类加载器具备了带有优先级的层次关系，确保了在各种加载环境的加载顺序。
     * 保证了运行的安全性，防止不可信类扮演可信任的类。
     */

    /*
     * string转为float
     */
    public static void stringToFloat() {
        float f = 0.1f;
        String max = "1.7";
        DecimalFormat df = new DecimalFormat("#########.#");
        String s = df.format(Float.parseFloat(max) + f);
        System.out.println(s);
    }

    /*
     * Android Handler底层机制
     *
     * https://blog.csdn.net/jzlhll123/article/details/80396252
     */

    /**
     * 4. 面试岗位：Android工程师。滴滴17.11.06一面：
     * <p>
     * 1.crash率是怎么降低的？你具体是怎么做的介绍一下？
     * 2.基础优化工作，你都做了哪些，具体介绍一下？
     * 3.oom怎么优化？
     * 4.性能优化都有哪些方面，具体介绍一下要怎么做？
     * 5.view的事件体系介绍一下？
     * 6.java 8的语法特性介绍一下？
     * 7.webview的内存泄漏问题，你是怎么解决的？
     * 8.Anr要怎么解决介绍一下，怎么定位anr问题介绍一下？
     * 9.写一个归并排序？  
     * 10. 淘宝的图片加载是怎么做的，怎么做到加载那么多图还不内存泄漏？
     * 11.你对工作怎么看的？
     */

    /*
     * webview的内存泄漏问题: android 5.1及以上
     *
     * webview引起的内存泄漏主要是因为
     *
     * org.chromium.android_webview.AwContents 类中注册了component callbacks，但是未正常反注册而导致的。
     * org.chromium.android_webview.AwContents 类中有这两个方法 onAttachedToWindow 和 onDetachedFromWindow；
     * 系统会在attach和detach处进行注册和反注册component callback；
     *
     * 在onDetachedFromWindow() 方法的第一行中：
     *
     * if (isDestroyed())
     * 	return;
     *
     * 如果 isDestroyed() 返回 true 的话，那么后续的逻辑就不能正常走到，所以就不会执行unregister的操作；
     * 我们的activity退出的时候，都会主动调用 WebView.destroy() 方法，这会导致 isDestroyed() 返回 true；
     * destroy()的执行时间又在onDetachedFromWindow之前，所以就会导致不能正常进行unregister()。
     * 然后解决方法就是：让onDetachedFromWindow先走，在主动调用destroy()之前，把webview从它的parent上面移除掉。
     * ViewParent parent = mWebView.getParent();
     * if (parent != null) {
     *     ((ViewGroup) parent).removeView(mWebView);
     * }
     *
     * mWebView.destroy();
     * 完整的activity的onDestroy()方法：
     * @Override
     * protected void onDestroy() {
     *     if( mWebView!=null) {
     *
     *         // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
     *         // destory()
     *         ViewParent parent = mWebView.getParent();
     *         if (parent != null) {
     *             ((ViewGroup) parent).removeView(mWebView);
     *         }
     *
     *         mWebView.stopLoading();
     *         // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
     *         mWebView.getSettings().setJavaScriptEnabled(false);
     *         mWebView.clearHistory();
     *         mWebView.clearView();
     *         mWebView.removeAllViews();
     *         mWebView.destroy();
     *
     *     }
     *     super.on Destroy();
     * }
     */

    /*
     * android性能优化的一些点
     *
     * https://www.cnblogs.com/cr330326/p/8011523.html
     */

    /*
     * 归并排序，分治 + 合并 + 递归.
     *
     * <p>
     * 就是采用先 “分割” 再 “合并” 的思想。
     * 我们首先把一个未排序的序列从中间分割成2部分，再把2部分分成4部分，依次分割下去，直到分割成一个一个的数据，
     * 再把这些数据两两归并到一起，使之有序，不停的归并，最后成为一个排好序的序列。
     * <p>
     * <p>
     * 分割到最小单元为1，再合并:
     * <pre>
     * ------------------------------------------------------------------------------------------
     *                               14  12  15  13  11  16
     *                  |                                                       |
     *      14        12        15                                     13       11        16
     *      -----------------------
     *         |                |
     *      14   12             15
     *    -----------         -----
     *      |     |             |                                           同左边一样分割再合并
     *      14    12            15
     *     ----  ----          ----
     *          |               |
     *      12     14           15
     *     -----------         ----
     *                    |                                                      |
     *              12  14   15                                              11   13  16
     *             ---------------                                          --------------
     *                                                 |
     *                                      11  12  13  14  15  16
     * ------------------------------------------------------------------------------------------
     * </pre>
     *
     * <p>
     * 平均O(NlogN) | 最好O(NlogN) | 最坏O(NlogN) | 空间O(N) | 稳定
     *
     * @param array 待排序数组
     */
    private static void mergeSort(int[] array) {
        int len = array.length;
        int[] temp = new int[len];
        mergeSort(array, temp, 0, len - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void mergeSort(int[] array, int[] temp, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    private static void merge(int[] array, int[] temp, int left, int mid, int right) {
        // 从left到right拷贝一份数组
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }
        // 记录左右部分的索引，两段起始位置
        int pa = left;
        int pb = mid + 1;
        // 合并后数组的索引
        int index = left;
        while (pa <= mid && pb <= right) {
            // 哪一部分小，就拷贝哪一部分到新数组
            if (temp[pa] <= temp[pb]) {
                // 拷贝pa的
                array[index++] = temp[pa++];
            } else {
                // 拷贝pb的
                array[index++] = temp[pb++];
            }
        }
        // 处理没有拷贝完的部分，直接赋值到新数组
        while (pa <= mid) {
            array[index++] = temp[pa++];
        }
        while (pb <= right) {
            array[index++] = temp[pb++];
        }
    }

    /**
     * 5. 问的比较详细，基础很重要。算法很重要，好好准备。下一家。然后就是需要我们自己去学习了。
     * 什么数组实现栈啊数组实现队列啊。多线程状态啊。多线程操作啊。android启动流程以及绘制流程，事件分发啊。
     */

    /*
     * 数组实现队列
     */
    public static class ArrayQueue {

        public Object[] array;
        public int head;
        public int tail;
        public int size;

        public ArrayQueue(int capacity) {
            array = new Object[capacity];
        }

        public Object peek() {
            if (size == 0)
                throw new RuntimeException("queue is empty");
            return array[head];
        }

        public void push(Object object) {
            if (size == array.length)
                throw new RuntimeException("queue is full");
            ++size;
            array[tail] = object;
            tail = tail == array.length - 1 ? 0 : tail + 1;
        }

        public Object pop() {
            if (size == 0)
                throw new RuntimeException("queue is empty");
            --size;
            int temp = head;
            head = head == array.length - 1 ? 0 : head + 1;
            return array[temp];
        }
    }

    /**
     * 6. 面试有4轮，1轮android技术面，主要是面试一些基础知识，常用的四大组件，view的绘制，事件的分发，handler等等。
     * 2轮还是技术面，倾向于逻辑，直接拿出实际的问题，来考验逻辑能力，需要有条理的给出思路。
     * 3轮还是技术面，不过是做服务器的，直接问算法。4轮HR面。从1点开始一直面试到5点。
     *
     * 45匹马，9个赛道，用最短的次数排出前3。
     */

    /*
     * 有36匹马6个跑道，用最少比赛次数算出跑最快的前3匹马:
     *
     * 36匹马分6个组,分别为A、B、C、D、E、F组.
     * 第一轮,每个组各跑一次,取每组前三名,标识为A1、A2、A3,B1、B2、B3,以此类推.
     *
     * 第二轮,每个组的第一名（A1——F1）拉出来跑一次,假设名次是：A1第一名,B1第二名,C1第三名.
     * 则：
     *
     * 1.后三名及其所在组的其余组员均被淘汰（小组头名都没能进前三,当然是全部淘汰啦）
     * 2.两战全胜的A1已经提前夺冠了.
     * 3.由于A1已经占去了一个名额,只剩两个名额了,则B3、C3可以淘汰了.而且由于C1的最好成绩也只能是第三名了,所以C2也可以淘汰了.
     *
     * 第三轮,A2、A3、B1、B2、C1五匹马跑,取前两名.
     * 其中第一轮跑6次,第二轮第三轮都各只跑1次,一共8次.
     */

    /**
     * 7. 滴滴Android技术面试总共三轮，依次是高工以上级别、部门经理、总监顺序面试。
     * 第一轮面我的是Android架构师，之后了解到技术确实很厉害，在公司内部造了很多牛逼的轮子。面试的主要考察点是各种基础，
     * 包含Java中的集合、多线程、锁，例如代码写死锁，设计模式，代理模式的代码实现，海量数据的处理。
     * 这些都是考察技术基本功，校招有刷题经验的人很容易过，毕竟这些题很常见。
     * <p>
     * 第二轮面试主要问项目经历，本人两年经验不到，之前一直做Android迭代的工作，自定义动画做的多。然后面试官问了些fragment底层源码原理的东东，
     * 因为这块源码没看过，当然一脸懵逼，之后回想出来该问题的目的：很多Android架构涉及到fragment的操作，所以需要了解底层原理。
     * 之后又问道ndk底层开发东东，特别深，也是一脸懵逼。之后问多线程锁的机制的问题：锁的优化，读写锁的原理，让后要求自己实现一个读写锁，
     * 如果没看过读写锁的实现代码，当然也是一脸懵逼。总之二面问得很深，很多问题没打出来，感觉很悲观。面完后真以为挂了，但是没想到有三面了。
     * <p>
     * 之后总结二面通过的原因：a.一面确实不错，给自己打90分，b.二面面试官为人随和不苛刻，c.如果二面问题答得很好，面试通过后期望级别必定更高。
     * <p>
     * 第三轮技术总监面试，主要问了项目经历，考察解决问题的思路，最后让设计一个网络框架，大概设计了必要的接口，然后说了下大概的流程其中涉及到的一些关键点。
     * 最后问下个人技术爱好等等。
     * <p>
     * 面试成功总结：1.基础要扎实，参考一面提到的问题，不仅仅是Android相关的。2.面试表现要积极，要有精气神在，让面试官感觉你很有活力。
     * 3.一开始不会的问题也要尽量去想，切记要冷静不能慌张，面试官想通过该问题来考察应聘者的解决问题的思路。
     * 4.面试也得看运气了，包含面试的问题，当然最终要点是面试官不苛刻挑剔。
     * <p>
     * 面试官的问题：
     * 问面试问题见面经：Java中的集合、多线程、锁，例如代码写死锁，设计模式，代理模式的代码实现，海量数据的处理，fragment源码等等。
     * 答回答网上搜索吧，这样做更详细系统。
     */

    /*
     * 基于synchronized实现读写锁
     */
    class MyReadWriteLock {

        private volatile int read;
        private volatile int write;

        public MyReadWriteLock() {
            this.read = 0;
            this.write = 0;
        }

        public synchronized void readLock() throws InterruptedException {
            if (write > 0) {
                wait();
            }
            read++;
        }

        public synchronized void readUnLock() {
            read--;
            notifyAll();
        }

        public synchronized void writeLock() throws InterruptedException {
            if (read > 0 || write > 0) {
                wait();
            }
            write++;
        }

        public synchronized void writeUnLock() {
            write--;
            notifyAll();
        }
    }

    /*
     * 海量数据的处理
     *
     * https://blog.csdn.net/hitxueliang/article/details/52153476
     *
     * 1.分治
     *
     * 基本上处理海量数据的问题，分治思想都是能够解决的，只不过一般情况下不会是最优方案，但可以作为一个baseline，
     * 可以逐渐优化子问题来达到一个较优解。传统的归并排序就是分治思想，涉及到大量无法加载到内存的文件、排序等问题都可以用这个方法解决。
     *
     * 适用场景:数据量大无法加载到内存
     *
     * 技能链接:归并排序
     *
     * 2.哈希(Hash)
     *
     * 个人感觉Hash是最为粗暴的一种方式，但粗暴却高效，唯一的缺点是耗内存，需要将数据全部载入内存。
     *
     * 适用场景:快速查找，需要总数据量可以放入内存
     *
     * 3.bit(位集或BitMap)
     *
     * 位集这种思想其实简约而不简单，有很多扩展和技巧。比如多位表示一个数据(能够表示存在和数量问题)，
     * BloomFilter(布隆过滤器就是一个典型的扩展)，在实际工作中应用场景很多，比如消息过滤等，读者需要掌握，
     * 但对于布隆过滤器使用有一些误区和不清楚的地方，读者可以看下面这篇博客避免这些性能上的误区。
     *
     * 适用场景：可进行数据的快速查找，判重
     *
     * 技能链接:布隆过滤器使用的性能误区
     *
     * 4.堆(Heap)
     *
     * 堆排序是一种比较通用的TopN问题解决方案，能够满足绝大部分的求最值的问题，读者需要掌握堆的基本操作和思想。
     *
     * 适用场景:处理海量数据中TopN的问题(最大或最小)，要求N不大，使得堆可以放入内存
     *
     * 技能链接:排序算法-Heap排序
     */
}
