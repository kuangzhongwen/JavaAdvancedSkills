package io.kzw.advance.main;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.*;

/**
 * 美团.
 *
 * @author kuang on 2018/11/29.
 */
public class MeiTuan {

    public static void main(String[] args) {
//        reverseString0("kuangzhongwen");
//        reverseString1("kuangzhongwen");

//        Collection<String> collection = new ArrayList<>();
//        collection.add("test0");
//        collection.add("test1");
//        removeElementFromCollect(collection, "test0");
//        zipString();
//        binarySearch();
//        int[] array = new int[] {5, 4, 3, 2, 1};
//        quickSort(array);
//        String[] strings = new String[] {"1", "5", "8"};
//        sumString(strings);
    }

    /**
     * 1. 字符串倒序
     */
    public static void reverseString0(String source) {
        if (source.length() == 1)
            System.out.println(source);
        else {
            // char数组实现
            char[] chars = source.toCharArray();
            for (int i = chars.length - 1; i >= 0; --i) {
                System.out.print(chars[i]);
            }
        }
    }

    public static void reverseString1(String source) {
        if (source.length() == 1)
            System.out.println(source);
        else {
            // StringBuilder自带的reverse
            System.out.println(new StringBuilder(source).reverse().toString());
        }
    }

    /**
     * 2. 手写栈的实现
     */
    public static class Stack {

        private Object[] array;
        private int top = -1;

        public Stack() {
            array = new Object[16];
        }

        public Stack(int capacity) {
            array = new Object[capacity];
        }

        public void push(Object o) {
            if (isFull()) {
                // 扩容
                array = Arrays.copyOf(array, 32);
            }
            array[++top] = o;
        }

        public Object pop() throws RuntimeException {
            if (isEmpty())
                throw new RuntimeException("is empty");
            return array[--top];
        }

        public Object peek() throws RuntimeException {
            if (isEmpty())
                throw new RuntimeException("is empty");
            return array[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == array.length - 1;
        }
    }

    /**
     * 3. 移除集合里满足条件的元素
     */
    public static void removeElementFromCollect(Collection<String> collection, String dest) {
        for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); ) {
            if (iterator.next().equals(dest)) {
                iterator.remove();
            }
        }
        System.out.println(Arrays.toString(collection.toArray()));
    }

    /**
     * 4. 输出二叉树每一层最左边的节点
     */
    public static class PrintTreeRightNode {

        private static class TreeNode {
            String data;
            TreeNode left;
            TreeNode right;
        }

        /**
         * 按层遍历使用队列 - 广度优先
         */
        public static void printTreeRightNode(TreeNode head) {
            if (head == null)
                return;
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.add(head);
            // 按层遍历，开始，和结束
            int start = 0;
            int end = 1;
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                start++;
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
                // 这一层遍历完毕
                if (start == end) {
                    start = 0;
                    // 记录下一层的个数
                    end = queue.size();
                    System.out.println(queue.peek().data);
                }
            }
        }
    }

    /**
     * 5. java内存GC原理
     */

    /*
     * 一、GC的基本原理：
     *
     * GC是什么? 为什么要有GC呢?
     * GC是垃圾收集的意思（Garbage Collection）,内存处理是编程人员容易出现问题的地方，忘记或者错误的内存回收会导致程序或系统的不稳定甚至崩溃，
     * Java提供的GC功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的，Java语言没有提供释放已分配内存的显示操作方法。
     *
     * 所以，Java的内存管理实际上就是对象的管理，其中包括对象的分配和释放。
     *
     * 对于Java程序员来说，分配对象使用new关键字；释放对象时，只要将对象所有引用赋值为null，让程序不能够再访问到这个对象，我们称该对象为&quot;
     * 不可达的&quot;.GC将负责回收所有&quot;不可达&quot;对象的内存空间。
     *
     * 对于GC来说，当程序员创建对象时，GC就开始监控这个对象的地址、大小以及使用情况。通常，GC采用有向图的方式记录和管理堆（heap）中的所有对象。
     * 通过这种方式确定哪些对象是&quot;可达的&quot;，哪些对象是&quot;不可达的&quot;.当GC确定一些对象为&quot;不可达&quot;时，
     * GC就有责任回收这些内存空间。但是，为了保证GC能够在不同平台实现的问题，Java规范对GC的很多行为都没有进行严格的规定。
     * 例如，对于采用什么类型的回收算法、什么时候进行回收等重要问题都没有明确的规定。因此，不同的JVM的实现者往往有不同的实现算法。
     * 这也给Java程序员的开发带来行多不确定性。本文研究了几个与GC工作相关的问题，努力减少这种不确定性给Java程序带来的负面影响。
     *
     * 二、增量式GC(Incremental GC)：
     *
     * GC在JVM中通常是由一个或一组进程来实现的，它本身也和用户程序一样占用heap空间，运行时也占用CPU.当GC进程运行时，应用程序停止运行。
     * 因此，当GC运行时间较长时，用户能够感到Java程序的停顿，另外一方面，如果GC运行时间太短，则可能对象回收率太低，这意味着还有很多应该回收的对象没有被回收，
     * 仍然占用大量内存。因此，在设计GC的时候，就必须在停顿时间和回收率之间进行权衡。一个好的GC实现允许用户定义自己所需要的设置，例如有些内存有限有设备，
     * 对内存的使用量非常敏感，希望GC能够准确的回收内存，它并不在意程序速度的放慢。另外一些实时网络游戏，就不能够允许程序有长时间的中断。
     * 增量式GC就是通过一定的回收算法，把一个长时间的中断，划分为很多个小的中断，通过这种方式减少GC对用户程序的影响。
     * 虽然，增量式GC在整体性能上可能不如普通GC的效率高，但是它能够减少程序的最长停顿时间。
     *
     * Sun JDK提供的HotSpot JVM就能支持增量式GC.HotSpot JVM缺省GC方式为不使用增量GC，为了启动增量GC，我们必须在运行Java程序时增加-Xincgc的参数。
     * HotSpot JVM增量式GC的实现是采用Train GC算法。它的基本想法就是，将堆中的所有对象按照创建和使用情况进行分组（分层），
     * 将使用频繁高和具有相关性的对象放在一队中，随着程序的运行，不断对组进行调整。当GC运行时，它总是先回收最老的（最近很少访问的）的对象，
     * 如果整组都为可回收对象，GC将整组回收。这样，每次GC运行只回收一定比例的不可达对象，保证程序的顺畅运行。
     *
     * 三、详解finalize函数：
     *
     * finalize 是位于Object类的一个方法，该方法的访问修饰符为protected，由于所有类为Object的子类，因此用户类很容易访问到这个方法。
     * 由于，finalize函数没有自动实现链式调用，我们必须手动的实现，因此finalize函数的最后一个语句通常是 super.finalize（）。通过这种方式，
     * 我们可以实现从下到上实现finalize的调用，即先释放自己的资源，然后再释放父类的资源。
     *
     * 根据Java语言规范，JVM保证调用finalize函数之前，这个对象是不可达的，但是JVM不保证这个函数一定会被调用。另外，规范还保证finalize函数最多运行一次。
     *
     * 很多Java初学者会认为这个方法类似与C++中的析构函数，将很多对象、资源的释放都放在这一函数里面。其实，这不是一种很好的方式。
     * 原因有三，其一，GC为了能够支持finalize函数，要对覆盖这个函数的对象作很多附加的工作。其二，在finalize运行完成之后，该对象可能变成可达的，
     * GC还要再检查一次该对象是否是可达的。因此，使用 finalize会降低GC的运行性能。其三，由于GC调用finalize的时间是不确定的，因此通过这种方式释放资源也是不确定的。
     *
     * 通常，finalize用于一些不容易控制、并且非常重要资源的释放，例如一些I/O的操作，数据的连接。这些资源的释放对整个应用程序是非常关键的。
     * 在这种情况下，程序员应该以通过程序本身管理（包括释放）这些资源为主，以finalize函数释放资源方式为辅，形成一种双保险的管理机制，
     * 而不应该仅仅依靠finalize来释放资源。
     *
     * 下面给出一个例子说明，finalize函数被调用以后，仍然可能是可达的，同时也可说明一个对象的finalize只可能运行一次。
     *
     *
     * 运行结果：
     * This is finalize
     *
     * MyObject还活着：此例子中，需要注意的是虽然MyObject对象在finalize中变成可达对象，但是下次回收时候，finalize却不再被调用，因为finalize函数最多只调用一次。
     *
     * 四、Java程序如何与GC进行交互：
     *
     * Java2 增强了内存管理功能，增加了一个java.lang.ref包，其中定义了三种引用类。这三种引用类分别为SoftReference、WeakReference和 PhantomReference.
     * 通过使用这些引用类，程序员可以在一定程度与GC进行交互，以便改善GC的工作效率。这些引用类的引用强度介于可达对象和不可达对象之间。
     *
     * 创建一个引用对象也非常容易，例如如果你需要创建一个Soft Reference对象，那么首先创建一个对象，并采用普通引用方式（可达对象）；
     * 然后再创建一个SoftReference引用该对象；最后将普通引用设置为null.通过这种方式，这个对象就只有一个Soft Reference引用。同时，
     * 我们称这个对象为Soft Reference 对象。
     *
     * Soft Reference的主要特点是据有较强的引用功能。只有当内存不够的时候，才进行回收这类内存，因此在内存足够的时候，它们通常不被回收。
     * 另外，这些引用对象还能保证在Java抛出OutOfMemory 异常之前，被设置为null.它可以用于实现一些常用图片的缓存，实现Cache的功能，
     * 保证最大限度的使用内存而不引起OutOfMemory.以下给出这种引用类型的使用伪代码；
     *
     * Weak 引用对象与Soft引用对象的最大不同就在于：GC在进行回收时，需要通过算法检查是否回收Soft引用对象，而对于Weak引用对象，GC总是进行回收。
     * Weak引用对象更容易、更快被GC回收。虽然，GC在运行时一定回收Weak对象，但是复杂关系的Weak对象群常常需要好几次 GC的运行才能完成。
     * Weak引用对象常常用于Map结构中，引用数据量较大的对象，一旦该对象的强引用为null时，GC能够快速地回收该对象空间。
     *
     * Phantom 引用的用途较少，主要用于辅助finalize函数的使用。Phantom对象指一些对象，它们执行完了finalize函数，并为不可达对象，
     * 但是它们还没有被GC回收。这种对象可以辅助finalize进行一些后期的回收工作，我们通过覆盖Reference的clear（）方法，增强资源回收机制的灵活性。
     *
     * 五、一些Java编程的建议：
     *
     * 根据GC的工作原理，我们可以通过一些技巧和方式，让GC运行更加有效率，更加符合应用程序的要求。一些关于程序设计的几点建议：
     *
     * 1.最基本的建议就是尽早释放无用对象的引用。大多数程序员在使用临时变量的时候，都是让引用变量在退出活动域（scope）后，自动设置为 null.
     * 我们在使用这种方式时候，必须特别注意一些复杂的对象图，例如数组，队列，树，图等，这些对象之间有相互引用关系较为复杂。对于这类对象，
     * GC 回收它们一般效率较低。如果程序允许，尽早将不用的引用对象赋为null.这样可以加速GC的工作。
     *
     * 2.尽量少用finalize函数。finalize函数是Java提供给程序员一个释放对象或资源的机会。但是，它会加大GC的工作量，因此尽量少采用finalize方式回收资源。
     *
     * 3.如果需要使用经常使用的图片，可以使用soft应用类型。它可以尽可能将图片保存在内存中，供程序调用，而不引起OutOfMemory.
     *
     * 4.注意集合数据类型，包括数组，树，图，链表等数据结构，这些数据结构对GC来说，回收更为复杂。另外，注意一些全局的变量，以
     * 及一些静态变量。这些变量往往容易引起悬挂对象（dangling reference），造成内存浪费。
     *
     * 5.当程序有一定的等待时间，程序员可以手动执行System.gc（），通知GC运行，但是Java语言规范并不保证GC一定会执行。使用增量式GC可以缩短Java程序的暂停时间。
     */

    /**
     * 6. Android中常用的知识，可能会设计到framework，线程同步这些
     */
    /*
     * 参考framework笔记，再复习一遍。
     */

    /**
     * 7. 谁持有了Handler的引用
     */

    /*
     * 为了避免因为handler持有activity导致的内存泄漏 可以将内部类写成静态内部类
     *
     * static class myHandler extends Handler {
     * //弱引用<引用外部类>
     * WeakReference<Activity> reference;
     *
     *     myHandler(Activity activity) {
     *         //构造创建弱引用
     *         reference = new WeakReference<Activity>(activity);
     *     }
     *     @Override
     *     public void handleMessage(Message msg) {
     *         //通过弱引用获取外部类.
     *
     *
     *         Activity activity = reference.get();
     *         //进行非空再操作
     *         if (activity != null) {
     *             switch (msg.what) {
     *                 case 1:
     *                 Log.e("--", "10空");
     *
     *                 //doSomething
     *
     *                 TextView a = activity.getWindow().getDecorView().findViewById(R.id.ida);
     *                 a.setText("this is b");
     *                 Toast.makeText(activity, a.getText(), Toast.LENGTH_LONG).show();
     *             break;
     *             }
     *         }else {
     *             Log.e("--","2空");
     * // Toast.makeText(activity.getApplicationContext(),"meile",Toast.LENGTH_LONG).show();
     * }
     * }
     * }
     */

    /**
     * 8. view事件分发机制，onTouchEvent返回值的作用。
     */
    /*
     * https://www.cnblogs.com/linjzong/p/4191891.html
     */

    /**
     * 9. okhttp的优缺点
     */
    /*
     * https://blog.csdn.net/lxcay9/article/details/52182239
     */

    /**
     * 10. activity的启动流程。答startActivity以后，zygote启动进程，通过AMS启动
     */
    /*
     * 参考framework笔记，再复习一遍。
     *
     * 剖析Activity、Window、ViewRootImpl和View之间的关系:
     * window.addView() -> root = new ViewRootImpl(view.getContext(), display);
     *
     * https://blog.csdn.net/jiang19921002/article/details/78977560
     */

    /**
     * 11. 一面问到了HTTP请求流程、Okhttp框架、Java多线程、Bitmap内存优化等；二面主要是聊项目经历，
     * 问了EventBus、Okhttp、Bitmap内存分配等内容，还写了一到算法题。
     */
    /*
     * 一次完整的http请求流程：
     * https://blog.csdn.net/skyxmstar/article/details/68059927
     *
     * 1。 三次握手，建立tcp连接，可以传输数据了。
     * 2。 客户端向服务器发送请求，如get, post, put, delete。
     * 3。 客户端向服务器发送请求头信息。
     * 4。 服务器应答，返回状态码。
     * 5。 服务器发送应答头信息。
     * 6。 服务器发送数据。
     * 7。 关闭连接。
     *
     * 三次握手：
     * 第一次
     * 第一次握手：建立连接时，客户端发送syn包（syn=j）到服务器，并进入SYN_SENT状态，等待服务器确认；SYN：同步序列编号（Synchronize Sequence Numbers）。
     *
     * 第二次
     * 第二次握手：服务器收到syn包，必须确认客户的SYN（ack=j+1），同时自己也发送一个SYN包（syn=k），即SYN+ACK包，此时服务器进入SYN_RECV状态；
     *
     * 第三次
     * 第三次握手：客户端收到服务器的SYN+ACK包，向服务器发送确认包ACK(ack=k+1），此包发送完毕，客户端和服务器进入ESTABLISHED（TCP连接成功）状态，完成三次握手。
     * 完成三次握手，客户端与服务器开始传送数据，在上述过程中，还有一些重要的概念：
     */

    /**
     * 12. listview优化，自己写一个堆栈功能的类，深度搜索，字符长压缩。
     */
    /*
     * 堆栈功能的类，参考 2. 手写栈的实现
     */

    /*
     * ###### 图的遍历之 深度优先搜索和广度优先搜索
     *
     * ####### 深度优先搜索介绍
     *
     * 1. 图的深度优先搜索(Depth First Search)，和树的先序遍历比较类似。
     *
     * 它的思想：假设初始状态是图中所有顶点均未被访问，则从某个顶点v出发，首先访问该顶点，
     * 然后依次从它的各个未被访问的邻接点出发深度优先搜索遍历图，直至图中所有和v有路径相通的顶点都被访问到。
     * 若此时尚有其他顶点未被访问到，则另选一个未被访问的顶点作起始点，重复上述过程，直至图中所有顶点都被访问到为止。
     * 显然，深度优先搜索是一个递归的过程。
     *
     * 2. 深度优先搜索图解
     * 2.1 无向图的深度优先搜索
     *
     * ------------------------------------------------------------------------------------------------
     * 下面以"无向图"为例，来对深度优先搜索进行演示。
     *
     * A --------- F --------- G --------- E
     * |  \
     * |   \
     * |    \
     * |     \
     * |      \
     * |       \
     * |        \
     * |         \
     * |          \
     * C --------- D
     * |
     * |
     * |
     * |
     * |
     * |
     * B
     *                   (G1)
     *
     * 对上面的图G1进行深度优先遍历，从顶点A开始。
     *
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/iterator/02.jpg?raw=true
     *
     * 第1步：访问A。
     * 第2步：访问(A的邻接点)C。
     *        在第1步访问A之后，接下来应该访问的是A的邻接点，即"C,D,F"中的一个。
     *        但在本文的实现中，顶点ABCDEFG是按照顺序存储，C在"D和F"的前面，因此，先访问C。
     * 第3步：访问(C的邻接点)B。
     *        在第2步访问C之后，接下来应该访问C的邻接点，即"B和D"中一个(A已经被访问过，就不算在内)。
     *        而由于B在D之前，先访问B。
     * 第4步：访问(C的邻接点)D。
     *        在第3步访问了C的邻接点B之后，B没有未被访问的邻接点；因此，返回到访问C的另一个邻接点D。
     * 第5步：访问(A的邻接点)F。
     *       前面已经访问了A，并且访问完了"A的邻接点B的所有邻接点(包括递归的邻接点在内)"；
     *       因此，此时返回到访问A的另一个邻接点F。
     * 第6步：访问(F的邻接点)G。
     * 第7步：访问(G的邻接点)E。
     *
     * 因此访问顺序是：A C B D F G E
     * ------------------------------------------------------------------------------------------------
     *
     *
     *
     * 2.2 有向图的深度优先搜索
     *
     * ------------------------------------------------------------------------------------------------
     * 下面以"有向图"为例，来对深度优先搜索进行演示。
     *
     *                 A
     *                 |
     *                 |
     *                 ↓
     *       C <------ B -------> F
     *       ^   \    | ^         |
     *       |    \   | |         |
     *       |     ↓  ↓ |         ↓
     *       D <------ E          G
     *
     *                (G2)
     *
     * 对上面的图G2进行深度优先遍历，从顶点A开始。
     *
     * 第1步：访问A。
     * 第2步：访问B。
     *       在访问了A之后，接下来应该访问的是A的出边的另一个顶点，即顶点B。
     * 第3步：访问C。
     *       在访问了B之后，接下来应该访问的是B的出边的另一个顶点，即顶点C,E,F。在本文实现的图中，
     *       顶点ABCDEFG按照顺序存储，因此先访问C。
     * 第4步：访问E。
     *       接下来访问C的出边的另一个顶点，即顶点E。
     * 第5步：访问D。
     *       接下来访问E的出边的另一个顶点，即顶点B,D。顶点B已经被访问过，因此访问顶点D。
     * 第6步：访问F。
     *       接下应该回溯"访问A的出边的另一个顶点F"。
     * 第7步：访问G。
     *
     * 因此访问顺序是：（按照出边方向）A B C E D F G
     * ------------------------------------------------------------------------------------------------
     *
     *
     * ####### 广度优先搜索的图文介绍
     *
     * 1. 广度优先搜索介绍
     * 广度优先搜索算法(Breadth First Search)，又称为"宽度优先搜索"或"横向优先搜索"，简称BFS。
     *
     * 它的思想是：从图中某顶点v出发，在访问了v之后依次访问v的各个未曾访问过的邻接点，然后分别从这些邻接点出发依次访问它们的邻接点，
     * 并使得“先被访问的顶点的邻接点先于后被访问的顶点的邻接点被访问，直至图中所有已被访问的顶点的邻接点都被访问到。
     * 如果此时图中尚有顶点未被访问，则需要另选一个未曾被访问过的顶点作为新的起始点，重复上述过程，直至图中所有顶点都被访问到为止。
     * 换句话说，广度优先搜索遍历图的过程是以v为起点，由近至远，依次访问和v有路径相通且路径长度为1,2...的顶点。
     *
     * 2. 广度优先搜索图解
     *
     * 2.1 无向图的广度优先搜索
     * ------------------------------------------------------------------------------------------------
     * 下面以"无向图"为例，来对广度优先搜索进行演示。还是以上面的图G1为例进行说明。
     *
     * A --------- F --------- G --------- E
     * |  \
     * |   \
     * |    \
     * |     \
     * |      \
     * |       \
     * |        \
     * |         \
     * |          \
     * C --------- D
     * |
     * |
     * |
     * |
     * |
     * |
     * B
     *
     * 广度优先搜索 (分层级，如A是第一层，CDF第二层，BG第三层，E第四层）
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/iterator/05.jpg?raw=true
     *
     * 第1步：访问A。
     * 第2步：依次访问C,D,F。
     *       在访问了A之后，接下来访问A的邻接点。
     *       前面已经说过，在本文实现中，顶点ABCDEFG按照顺序存储的，C在"D和F"的前面，因此，先访问C。
     *       再访问完C之后，再依次访问D,F。
     * 第3步：依次访问B,G。
     *       在第2步访问完C,D,F之后，再依次访问它们的邻接点。首先访问C的邻接点B，再访问F的邻接点G。
     * 第4步：访问E。
     *       在第3步访问完B,G之后，再依次访问它们的邻接点。只有G有邻接点E，因此访问G的邻接点E。
     *
     * 因此访问顺序是：A C D F B G E
     * ------------------------------------------------------------------------------------------------
     *
     * 2.2 有向图的广度优先搜索
     *
     * ------------------------------------------------------------------------------------------------
     * 下面以"有向图"为例，来对广度优先搜索进行演示。还是以上面的图G2为例进行说明。
     *
     *                 A
     *                 |
     *                 |
     *                 ↓
     *       C <------ B -------> F
     *       ^   \    | ^         |
     *       |    \   | |         |
     *       |     ↓  ↓ |         ↓
     *       D <------ E          G
     *
     * 广度优先搜索（分层级，A是第一层，B是第二层，CEF是第三层，DG是第四层）
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/iterator/06.jpg?raw=true
     *
     * 第1步：访问A。
     * 第2步：访问B。
     * 第3步：依次访问C,E,F。
     *       在访问了B之后，接下来访问B的出边的另一个顶点，即C,E,F。
     *       前面已经说过，在本文实现中，顶点ABCDEFG按照顺序存储的，因此会先访问C，再依次访问E,F。
     * 第4步：依次访问D,G。
     *       在访问完C,E,F之后，再依次访问它们的出边的另一个顶点。还是按照C,E,F的顺序访问，C的已经全部访问过了，
     *       那么就只剩下E,F；先访问E的邻接点D，再访问F的邻接点G。
     *
     * 因此访问顺序是：A B C E F D G
     * ------------------------------------------------------------------------------------------------
     */
    public static class MatrixGraphic {

        // 顶点集合
        private char[] mVexs;
        // 邻接矩阵
        private int[][] mMatrix;

        /**
         * 创建图（自己输入数据）
         *
         * @param dg 是否有向
         */
        public MatrixGraphic(boolean dg) {
            // 输入"顶点数"和"边数"
            System.out.printf("input vertex number: ");
            int vlen = readInt();
            System.out.printf("input edge number: ");
            int elen = readInt();
            if (vlen < 1 || elen < 1 || elen > (vlen * (vlen - 1))) {
                System.out.printf("input error: invalid parameters!\n");
                return;
            }

            // 初始化"顶点"
            mVexs = new char[vlen];
            for (int i = 0; i < vlen; i++) {
                System.out.printf("vertex(%d): ", i);
                mVexs[i] = readChar();
            }

            // 初始化"边"，以顶点个数来初始化，横排，竖排都为顶点个数
            mMatrix = new int[vlen][vlen];
            for (int i = 0; i < elen; i++) {
                // 读取边的起始顶点和结束顶点
                System.out.printf("edge(%d):", i);
                char c1 = readChar();
                char c2 = readChar();
                int p1 = getPosition(c1);
                int p2 = getPosition(c2);
                if (p1 == -1 || p2 == -1) {
                    System.out.printf("input error: invalid edge!\n");
                    return;
                }
                // 存在边
                mMatrix[p1][p2] = 1;
                if (!dg)
                    // 如果无向
                    mMatrix[p2][p1] = 1;
            }
        }

        /**
         * 创建图(用已提供的矩阵)
         * <p>
         *
         * @param vexs  顶点数组
         * @param edges 边数组
         * @param dg    是否有向
         */
        public MatrixGraphic(char[] vexs, char[][] edges, boolean dg) {
            // 初始化"顶点数"和"边数"
            int vlen = vexs.length;
            int elen = edges.length;

            // 初始化"顶点"
            mVexs = new char[vlen];
            for (int i = 0; i < vlen; i++)
                mVexs[i] = vexs[i];

            // 初始化"边"
            mMatrix = new int[vlen][vlen];
            for (int i = 0; i < elen; i++) {
                // 读取边的起始顶点和结束顶点
                int p1 = getPosition(edges[i][0]);
                int p2 = getPosition(edges[i][1]);

                mMatrix[p1][p2] = 1;
                if (!dg)
                    // 如果无向
                    mMatrix[p2][p1] = 1;
            }
        }

        /**
         * 返回ch位置
         */
        private int getPosition(char ch) {
            for (int i = 0; i < mVexs.length; i++)
                if (mVexs[i] == ch)
                    return i;
            return -1;
        }

        /**
         * 读取一个输入字符
         */
        private char readChar() {
            char ch = '0';
            do {
                try {
                    ch = (char) System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));

            return ch;
        }

        /**
         * 读取一个输入字符
         */
        private int readInt() {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        }

        /**
         * 打印矩阵队列图
         */
        public void print() {
            System.out.printf("Martix Graph:\n");
            for (int i = 0; i < mVexs.length; i++) {
                for (int j = 0; j < mVexs.length; j++)
                    System.out.printf("%d ", mMatrix[i][j]);
                System.out.printf("\n");
            }
        }

        /**
         * 返回顶点v的第一个邻接顶点的索引，失败则返回-1
         */
        private int firstVertex(int v) {
            // 边界判断
            if (v < 0 || v > (mVexs.length - 1))
                return -1;

            // 顺序查找
            for (int i = 0; i < mVexs.length; i++)
                if (mMatrix[v][i] == 1)
                    return i;

            return -1;
        }

        /**
         * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
         */
        private int nextVertex(int v, int w) {
            // 边界判断
            if (v < 0 || v > (mVexs.length - 1) || w < 0 || w > (mVexs.length - 1))
                return -1;

            for (int i = w + 1; i < mVexs.length; i++)
                if (mMatrix[v][i] == 1)
                    return i;

            return -1;
        }

        /**
         * 深度优先搜索遍历图
         */
        public void DFS() {
            // 顶点访问标记
            boolean[] visited = new boolean[mVexs.length];
            // 初始化所有顶点都没有被访问
            for (int i = 0; i < mVexs.length; i++) {
                visited[i] = false;
            }
            System.out.printf("DFS: ");
            for (int i = 0; i < mVexs.length; i++) {
                if (!visited[i]) {
                    DFS(i, visited);
                }
            }
            System.out.printf("\n");
        }

        private void DFS(int i, boolean[] visited) {
            // 已访问这个顶点
            visited[i] = true;
            System.out.printf("%c ", mVexs[i]);
            // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
            for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
                if (!visited[w])
                    // 递归调用
                    DFS(w, visited);
            }
        }

        /**
         * 广度优先搜索（类似于树的层次遍历）
         */
        public void BFS() {
            int head = 0;
            int rear = 0;
            // 辅助队列
            int[] queue = new int[mVexs.length];
            boolean[] isVisited = new boolean[mVexs.length];
            for (int i = 0; i < mVexs.length; i++) {
                isVisited[i] = false;
            }
            for (int i = 0; i < mVexs.length; i++) {
                if (!isVisited[i]) {
                    isVisited[i] = true;
                    queue[rear++] = i;
                }
                while (head != rear) {
                    int j = queue[head++];
                    for (int w = firstVertex(j); w >= 0; w = nextVertex(j, w)) {
                        if (!isVisited[w]) {
                            isVisited[w] = true;
                            queue[rear++] = w;
                        }
                    }
                }
            }
        }

        public static void test(boolean dg) {
            char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
            char[][] edges = new char[][]{
                    {'A', 'C'},
                    {'A', 'D'},
                    {'A', 'F'},
                    {'B', 'C'},
                    {'C', 'D'},
                    {'E', 'G'},
                    {'F', 'G'}};
            MatrixGraphic pG;

            // 自定义"图"(输入矩阵队列)
            //pG = new MatrixUDG();
            // 采用已有的"图"
            pG = new MatrixGraphic(vexs, edges, dg);

            // 打印图
            pG.print();
            // 深度优先遍历
            pG.DFS();
            // 广度优先遍历
            pG.BFS();
        }
    }

    /*
     * 字符长压缩
     *
     * 利用字符重复出现的次数，编写一个方法，实现基本的字符串压缩功能。比如，字符串“aabcccccaaa”经压缩会变成“a2b1c5a3”。
     * 若压缩后的字符串没有变短，则返回原先的字符串。
     */
    public static void zipString() {
//        String source = "aaabbccccd";
        String source = "abcdefgg";
        source += " ";
        int len = source.length();
        StringBuilder stringBuilder = new StringBuilder(len);
        char[] chars = source.toCharArray();
        int count = 1;
        for (int i = 1; i < len; i++) {
            if (chars[i] == chars[i - 1]) {
                ++count;
            } else {
                stringBuilder.append(chars[i - 1]);
                if (count != 1) {
                    stringBuilder.append(count);
                    count = 1;
                }
            }
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * 13. 在线笔试过了，就安排面试，一面是个妹子，感觉很亲切，但是也问了很多，
     * 单例模式（写一个），
     * 工厂模式，
     * java的跨平台，
     * 写二分查找的程序，
     * 问项目，有一道智力题，总体不难
     * <p>
     * 二面很虐，
     * java虚拟机，
     * dalvik虚拟机，
     * 数据库左连接，
     * 文件系统的inode，
     * jvm和dalik的区别，
     * cpu计算器的实现，
     * 技术排序，
     * tcp的窗口机制，
     * defaut 和protected的区别，
     * 数据路的性能优化，
     * 泛型等
     * <p>
     * 写程序:求任意两个节点的最近父节点，基数排序, 问的很广，很多都没答上来
     */
    /*
     * 单例模式
     */
    public static class Singleton {

        private static Singleton instance;

        private Singleton() {
        }

        public static Singleton getInstance() {
            // 使用双重检查进一步做了优化，可以避免整个方法被锁，只对需要锁的代码部分加锁，可以提高执行效率。
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }

    public static class Singleton1 {
        // 私有构造
        private Singleton1() {
        }

        // 静态内部类
        private static class InnerObject {
            private static Singleton1 single = new Singleton1();
        }

        public static Singleton1 getInstance() {
            return InnerObject.single;
        }

        // 注意反序列化
        private Object readResolve() throws ObjectStreamException {
            return getInstance();
        }
    }

    /*
     * 工厂模式
     */
    public interface Shape {

        void onDraw();
    }

    public static class Circle implements Shape {

        @Override
        public void onDraw() {
            System.out.println("Circle draw");
        }
    }

    public static class Rectangle implements Shape {

        @Override
        public void onDraw() {
            System.out.println("Rectangle draw");
        }
    }

    public static class ShapeFactory {

        public static Shape getInstance(String type) {
            if (type.equals("circle"))
                return new Circle();
            else if (type.equals("rectangle"))
                return new Rectangle();
            return null;
        }
    }

    /*
     * 二分查找
     */
    public static void binarySearch() {
        int[] array = new int[]{1, 2, 3, 4, 5, 6};
        int key = 5;
        int low = 0;
        int high = array.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (key < array[mid]) {
                high = mid - 1;
            } else if (key > array[mid]) {
                low = mid + 1;
            } else {
                System.out.println("find = " + mid);
                break;
            }
        }
    }

    /*
     * java虚拟机
     *
     * https://www.cnblogs.com/IUbanana/p/7067362.html
     */

    /*
     * dalvik虚拟机
     *
     * https://www.cnblogs.com/lxjshuju/p/7191910.html
     *
     * 画个漫画，告诉你什么是即时编译器JIT:
     * https://www.sohu.com/a/169704040_464084
     * https://blog.csdn.net/sunxianghuang/article/details/52094859
     */

    /*
     * 数据库左连接
     *
     * https://blog.csdn.net/plg17/article/details/78758593
     */

    /*
     * 数据库的性能优化
     *
     * http://www.trinea.cn/android/database-performance/
     */

    /**
     *  14. 首先问一些很常年不用的问题，甚至还有ec和as的差异，不知道问这种问题有什么卵用。难道还有两拨人一拨用as一拨用ec？
     *  一会问java 的static关键字的作用范围，一会又问线程池的实现原理。两个问题的难度跨度这么大，很难想象到底招那种水平的。
     */
    /*
     * https://www.cnblogs.com/dongguacai/p/6030187.html
     *
     * 线程池中的核心线程数，当提交一个任务时，线程池创建一个新线程执行任务，直到当前线程数等于corePoolSize；
     * 如果当前线程数为corePoolSize，继续提交的任务被保存到阻塞队列中，等待被执行；
     * 如果阻塞队列满了，那就创建新的线程执行当前任务；
     * 直到线程池中的线程数达到maxPoolSize, 这时再有任务来，只能执行reject()处理该任务；
     *
     * newFixedThreadPool()
     * 说明：初始化一个指定线程数的线程池，其中corePoolSize == maxiPoolSize，使用LinkedBlockingQuene作为阻塞队列
     * 特点：即使当线程池没有可执行任务时，也不会释放线程。
     * newCachedThreadPool()
     * 说明：初始化一个可以缓存线程的线程池，默认缓存60s，线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；
     * 特点：在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源；当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销；
     * 因此，使用时要注意控制并发的任务数，防止因创建大量的线程导致而降低性能。
     * newSingleThreadExecutor()
     * 说明：初始化只有一个线程的线程池，内部使用LinkedBlockingQueue作为阻塞队列。
     * 特点：如果该线程异常结束，会重新创建一个新的线程继续执行任务，唯一的线程可以保证所提交任务的顺序执行
     * newScheduledThreadPool()
     * 特定：初始化的线程池可以在指定的时间内周期性的执行所提交的任务，在实际的业务场景中可以使用该线程池定期的同步数据。
     *
     * 总结：除了newScheduledThreadPool的内部实现特殊一点之外，其它线程池内部都是基于ThreadPoolExecutor类（Executor的子类）实现的。
     *
     * 注：关于workQueue参数的取值,JDK提供了4种阻塞队列类型供选择：
     * ArrayBlockingQueue：基于数组结构的有界阻塞队列，按FIFO排序任务；
     * LinkedBlockingQuene：基于链表结构的阻塞队列，按FIFO排序任务，吞吐量通常要高于
     * SynchronousQuene：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于ArrayBlockingQuene；
     * PriorityBlockingQuene：具有优先级的无界阻塞队列；
     */

    /**
     *  15. 8.10下午1：50到6：50参加美团面试，经过三轮技术面试。
     *
     *  技术面试点如下：
     *  1.HashMap实现原理及内存结构；
     *  2.TreeMap实现原理，没有回答出来。加强红黑树算法学习；
     *  3.volatile关键字；
     *  4.JVM分代技术；
     *  5.JVM内存回收方法（没答完全）；
     *  6.数据库索引实现原理(B+B-B*树学习)；
     *  7.REDIS实现分布式锁；
     *  8.AOP实现原理, 动态代理实现原理；
     *  9.类锁及对象锁；
     *  10.数据库事务隔离；
     *  11.分布式事务；
     *  12.NIO与BIO区别,引出AIO编程题：
     *  1.快速排序(其它排序算法学习)；
     *  2.字符串求和；
     *  3.单例模式；
     *  4.SQL分组排序。
     */

    /*
     * HashMap实现原理及内存结构
     *
     * https://www.cnblogs.com/chengxiao/p/6059914.html
     *
     * Entry[]
     * Entry: key, value, hash, Entry next
     */

    /**
     * TreeMap实现原理
     * <p>
     * https://blog.csdn.net/chenssy/article/details/26668941
     * <p>
     * //比较器，因为TreeMap是有序的，通过comparator接口我们可以对TreeMap的内部排序进行精密的控制
     * private final Comparator<? super K> comparator;
     * //TreeMap红-黑节点，为TreeMap的内部类
     * private transient Entry<K,V> root = null;
     * //容器大小
     * private transient int size = 0;
     * //TreeMap修改次数
     * private transient int modCount = 0;
     * //红黑树的节点颜色--红色
     * private static final boolean RED = false;
     * //红黑树的节点颜色--黑色
     * private static final boolean BLACK = true;
     * <p>
     * Entry:
     * //键
     * K key;
     * //值
     * V value;
     * //左孩子
     * Entry<K,V> left = null;
     * //右孩子
     * Entry<K,V> right = null;
     * //父亲
     * Entry<K,V> parent;
     * //颜色
     * boolean color = BLACK;
     */

    /*
     * B树：二叉树，每个结点只存储一个关键字，等于则命中，小于走左结点，大于走右结点；
     *
     * B-树：多路搜索树，每个结点存储M/2到M个关键字，非叶子结点存储指向关键字范围的子结点；
     *      所有关键字在整颗树中出现，且只出现一次，非叶子结点可以命中；
     *
     * B+树：在B-树基础上，为叶子结点增加链表指针，所有关键字都在叶子结点中出现，非叶子结点作为叶子结点的索引；B+树总是到叶子结点才命中；
     *
     * B*树：在B+树基础上，为非叶子结点也增加链表指针，将结点的最低利用率从1/2提高到2/3；
     */

    /*
     * Java IO和NIO的区别：
     *
     * https://www.cnblogs.com/xiaoxi/p/6576588.html
     */

    /*
     * 动态代理是目前使用的比较广泛技术，大家都比较熟悉的框架比如Spring中的AOP特性就用到了动态代理
     *
     * https://blog.csdn.net/z956281507/article/details/79121211
     */

    /*
     * 快速排序
     *
     * 分治 + 递归
     *
     * 平均O(NlogN) | 最好O(NlogN) | 最坏O(N²) | 空间O(logN) - O(N²) | 不稳定
     */
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int p = partation(array, left, right);
            quickSort(array, left, p - 1);
            quickSort(array, p + 1, right);
        }
    }

    private static int partation(int array[], int left, int right) {
        int p = left;
        // 基准值
        int pv = array[left];
        // 往右遍历数组，如果小于基准，则与基准交换
        for (int i = p + 1; i <= right; i++) {
            // 小于基准值
            if (array[i] < pv) {
                // 基准前移
                p++;
                // 交换
                if (p != i) {
                    int temp = array[p];
                    array[p] = array[i];
                    array[i] = temp;
                }
            }
        }
        // 最后，将新的基准的值赋值给最左边
        array[left] = array[p];
        array[p] = pv;
        // 返回划分位置
        return p;
    }

    /*
     * 类锁及对象锁
     *
     * https://blog.csdn.net/qq_25302451/article/details/78764580
     */

    /*
     * 字符串求和
     */
    public static void sumString(String[] str) {
        int sum = 0;
        for (String s : str) {
            sum += Integer.parseInt(s);
        }
        System.out.println("sum = " + sum);
    }

    /**
     * 16. 美团的笔试题目，算法程序题居多，最后有安卓的、前端什么的题，具体的也不太记得了。
     * 美团的题目做的不多，程序题有一道没做。感觉还是不难的，题目拿起来一看，第一道不就是leetcode上的吗，很简单。
     * 面试官的问题：
     * 问进程和线程的区别；进程间同步的方式有问到如何编程实现a^
     * <p>
     * 答我就说用二分的思想。还有几道题，我忘了。我问他，我的表现怎么样，他说挺好的。
     * 就这样，顺利进入第二面，第二面基本上是没问什么操作系统和网络的题目，就直接出算法题了，有如何判断一个二叉树是另一棵二叉树的子树；
     * 像打印机一样，倒过来打印一棵树，比如一个树是这样的， 输出4、5、6、2、3、1，这个就用层次遍历，存储遍历过的节点，
     * 在每一层的结尾存储该层的个数。。。 总之，是做出来了，二面的面试官也是挺幽默的，不会冷，不会面瘫。
     * 二面也就这样过了，顺利进入3面。3面就问了一两个HR经常问的问题，具体我也忘了，然后，就直接出题让你做了，面试官是毫无表情，这
     * 下倒是有点压力了，好吧，你出题把。在聊了最擅长的科目是什么后，还有问到自己的代码量等，还出了编程题，题目有 1）实
     * 现 char* upcase(const char* src, int len)。 2) 在类似6，7，8，1，2，3，4，5 的序列中用二分查找某个数。这道题我做过，不怕。
     */
    private static class TestSubTree {

        private static class Node {
            int data;
            Node left;
            Node right;

            Node(int data) {
                this.data = data;
            }
        }

        Node root;

        void insert(int data) {
            root = insert(data, root);
        }

        Node insert(int data, Node root) {
            if (root == null) {
                root = new Node(data);
                return root;
            }
            Node current = root;
            while (true) {
                if (data > current.data) {
                    if (current.right == null) {
                        current.right = new Node(data);
                        break;
                    }
                    current = current.right;
                } else {
                    if (current.left == null) {
                        current.left = new Node(data);
                        break;
                    }
                    current = current.left;
                }
            }
            return root;
        }

        /**
         * 判断 以node2为根的树是否是 node1 为根的树 的 子树
         */
        boolean isSubTree(Node node1, Node node2) {
            boolean result = false;
            // 只有node1 和 node2 都不为空时,才去判断.其他情况node2都不是node1的子树
            if (node1 != null && node2 != null) {
                if (node1.data == node2.data) {
                    result = hasSameNode(node1, node2);
                }
                if (!result)
                    result = isSubTree(node1.left, node2);
                if (!result)
                    result = isSubTree(node1.right, node2);
            }
            return result;
        }

        boolean hasSameNode(Node node1, Node node2) {
            if (node2 == null)
                return true;
            if (node1 == null)
                return false;
            if (node1.data != node2.data)
                return false;
            return hasSameNode(node1.left, node2.left) && hasSameNode(node1.right, node2.right);
        }

        boolean sameTree(Node node1, Node node2) {
            if ((node1 == null && node2 != null) || (node1 != null && node2 == null))
                return false;
            if (node1 == null && node2 == null)
                return true;
            if (node1.data != node2.data)
                return false;
            return sameTree(node1.left, node2.left) && sameTree(node1.right, node2.right);
        }
    }

    /*
     * 倒过来打印一棵树，比如一个树是这样的， 输出4、5、6、2、3、1，这个就用层次遍历，存储遍历过的节点
     */
    public static class ReversePrintTree {

        private static class Node {
            int data;
            Node left;
            Node right;
        }

        public static void reverstPrint(Node node) {
            List<List<Integer>> result = new ArrayList<>();
            Map<Integer, List<Integer>> map = new HashMap<>();
            // 从第一层开始
            fillmap(map, node, 1);
            for (int i = map.size() - 1; i >= 0; i--) {
                result.add(map.get(i));
            }
            // 最终的结果就是result
        }

        private static void fillmap(Map<Integer, List<Integer>> map, Node node, int level) {
            if (node == null)
                return;
            if (map.containsKey(level)) {
                map.get(level).add(node.data);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(node.data);
                map.put(level, list);
            }
            fillmap(map, node.left, ++level);
            fillmap(map, node.right, ++level);
        }
    }

    /*
     * 反转链表
     */
    public static class ReversePrintNode {

        private static class Node {
            int data;
            Node next;
        }

        public static void revertPrint(Node node) {
            Node revert = null;
            Node current = node;
            while (current != null) {
                Node temp = current;
                current = current.next;
                temp.next = revert;
                revert = temp;
            }
            // reverse就是逆序的
        }
    }
}
