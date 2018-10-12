package io.kzw.advance._01_struct_algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Android数据结构篇.
 *
 * @author kzw on 2018/09/15.
 */
public final class _07_AndroidStruct {

    /*
     * ###### 1、常用的五种并发包
     *
     * ConcurrentHashMap
     * CopyOnWriteArrayList
     * CopyOnWriteArraySet
     * ArrayBlockingQueue
     * LinkedBlockingQueue
     *
     * 1.1 ConcurrentHashMap
     *
     * 线程安全的HashMap的实现
     *
     * 数据结构：一个指定个数的Segment数组，数组中的每一个元素Segment相当于一个HashTable（一个HashEntry[]）
     * 扩容的话，只需要扩自己的Segment而非整个table扩容
     *
     *  @SuppressWarnings("unchecked")
     *  Segment<K,V>[] segments = (Segment<K,V>[]) new Segment<?,?>[DEFAULT_CONCURRENCY_LEVEL];
     *
     * key与value均不可以为null，而hashMap可以
     *
     * 向map添加元素
     *
     * 根据key获取key.hashCode的hash值
     *
     * 根据hash值算出将要插入的Segment
     *
     * 根据hash值与Segment中的HashEntry的容量-1按位与获取将要插入的HashEntry的index
     * 若HashEntry[index]中的HashEntry链表有与插入元素相同的key和hash值，根据onlyIfAbsent决定是否替换旧值
     *
     * 若没有相同的key和hash，直接返回将新节点插入链头，原来的头节点设为新节点的next（采用的方式与HashMap一致，都是HashEntry替换的方法）
     * ConcurrentHashMap基于concurrencyLevel划分出多个Segment来存储key-value，这样的话put的时候只锁住当前的Segment，
     * 可以避免put的时候锁住整个map，从而减少了并发时的阻塞现象.
     *
     * 从map中获取元素
     * 根据key获取key.hashCode的hash值
     * 根据hash值与找到相应的Segment
     * 根据hash值与Segment中的HashEntry的容量-1按位与获取HashEntry的index
     * 遍历整个HashEntry[index]链表，找出hash和key与给定参数相等的HashEntry，例如e
     * 如没找到e，返回null
     * 如找到e，获取e.value
     * 如果e.value!=null，直接返回
     * 如果e.value==null,则先加锁，等并发的put操作将value设置成功后，再返回value值
     *
     * 对于get操作而言，基本没有锁，只有当找到了e且e.value等于null,有可能是当下的这个HashEntry刚刚被创建，value属性还没有设置成功，
     * 这时候我们读到是该HashEntry的value的默认值null,所以这里加锁，等待put结束后，返回value值
     *
     * 加锁情况（分段锁）：
     * put
     * get中找到了hash与key都与指定参数相同的HashEntry，但是value==null的情况
     * remove
     * size()：三次尝试后，还未成功，遍历所有Segment，分别加锁（即建立全局锁）
     *
     *
     * 1.2、CopyOnWriteArrayList
     *
     * 线程安全且在读操作时无锁的ArrayList
     * 采用的模式就是"CopyOnWrite"（即写操作-->包括增加、删除，使用复制完成）
     * 底层数据结构是一个Object[]，初始容量为0，之后每增加一个元素，容量+1，数组复制一遍
     * 遍历的只是全局数组的一个副本，即使全局数组发生了增删改变化，副本也不会变化，所以不会发生并发异常。
     * 但是，可能在遍历的过程中读到一些刚刚被删除的对象
     * 增删改上锁、读不上锁
     * 读多写少且脏数据影响不大的并发情况下，选择CopyOnWriteArrayList
     *
     *
     * 1.3、CopyOnWriteArraySet
     *
     * 基于CopyOnWriteArrayList，不添加重复元素
     *
     *
     * 1.4、ArrayBlockingQueue
     *
     * 基于数组、先进先出、线程安全，可实现指定时间的阻塞读写，并且容量可以限制
     * 组成：一个对象数组 + 1把锁ReentrantLock + 2个条件Condition
     *
     * 三种入队对比
     * offer(E e)：如果队列没满，立即返回true； 如果队列满了，立即返回false-->不阻塞
     * put(E e)：如果队列满了，一直阻塞，直到数组不满了或者线程被中断-->阻塞
     * offer(E e, long timeout, TimeUnit unit)：在队尾插入一个元素,，如果数组已满，则进入等待，直到出现以下三种情况：-->阻塞
     * 被唤醒
     * 等待时间超时
     * 当前线程被中断
     * 三种出对对比
     *
     * poll()：如果没有元素，直接返回null；如果有元素，出队
     * take()：如果队列空了，一直阻塞，直到数组不为空或者线程被中断-->阻塞
     * poll(long timeout, TimeUnit unit)：如果数组不空，出队；如果数组已空且已经超时，返回null；
     * 如果数组已空且时间未超时，则进入等待，直到出现以下三种情况：
     * 被唤醒
     * 等待时间超时
     * 当前线程被中断
     * 需要注意的是，数组是一个必须指定长度的数组，在整个过程中，数组的长度不变，队头随着出入队操作一直循环后移
     * 锁的形式有公平与非公平两种
     *
     * 在只有入队高并发或出队高并发的情况下，因为操作数组，且不需要扩容，性能很高
     *
     * 1.5、LinkedBlockingQueue
     *
     * 基于链表实现，读写各用一把锁，在高并发读写操作都多的情况下，性能优于ArrayBlockingQueue
     * 组成一个链表 + 两把锁 + 两个条件
     * 默认容量为整数最大值，可以看做没有容量限制
     * 三种入队与三种出队与上边完全一样，只是由于LinkedBlockingQueue的的容量无限，在入队过程中，没有阻塞等待
     */

    /*
     * ###### 2. List和Set和Map的实现方式以及存储方式？
     *
     * 2.1 List常用实现方式有：ArrayList和LinkedList
     *
     * ArrayList 的存储方式：数组，查询快
     * LinkedList的存储方式：链表，插入，删除快
     *
     *
     * 2.2 Set常用实现方式有：HashSet和TreeSet
     *
     * HashSet的存储方式：哈希码算法，加入的对象需要实现hashcode（）方法，快速查找元素
     * TreeSet的存储方式：按序存放，想要有序就要实现Comparable接口
     *
     * 附加：
     * 集合框架提供了2个实用类：collections（排序，复制、查找）和Arrays对数组进行（排序，复制、查找）
     *
     *
     * 2.3 Map常用实现方式有：HashMap和TreeMap
     *
     * HashMap的存储方式：哈希码算法，快速查找键值
     * TreeMap存储方式：对键按序存放
     * 具体：
     *
     * 2.4 集合
     *
     * 2.41 Collection(单列集合)
     * List(有序,可重复)
     *
     * 2.42 ArrayList
     * 底层数据结构是数组,查询快,增删慢
     * 线程不安全,效率高
     *
     * 2.43 Vector
     * 底层数据结构是数组,查询快,增删慢
     * 线程安全,效率低
     *
     * 2.44 LinkedList
     * 底层数据结构是链表,查询慢,增删快
     * 线程不安全,效率高
     *
     * 2.45 Set(无序,唯一)
     * HashSet
     * 底层数据结构是哈希表。
     * 哈希表依赖两个方法：hashCode()和equals()
     *
     * 执行顺序：
     * 首先判断hashCode()值是否相同
     * 是：继续执行equals(),看其返回值
     * 是true:说明元素重复，不添加
     * 是false:就直接添加到集合
     * 否：就直接添加到集合
     * 最终：
     * 自动生成hashCode()和equals()即可
     *
     * 2.46 LinkedHashSet
     * 底层数据结构由链表和哈希表组成。
     * 由链表保证元素有序。
     * 由哈希表保证元素唯一。
     *
     * 2.47 TreeSet
     * 底层数据结构是红黑树。(是一种自平衡的二叉树)
     * 如何保证元素唯一性呢
     * 根据比较的返回值是否是0来决定
     * 如何保证元素的排序呢
     * 两种方式
     * 自然排序(元素具备比较性)
     * 让元素所属的类实现Comparable接口
     * 比较器排序(集合具备比较性)
     * 让集合接收一个Comparator的实现类对象
     *
     * 2.48 Map(双列集合)
     * A:Map集合的数据结构仅仅针对键有效，与值无关。
     * B:存储的是键值对形式的元素，键唯一，值可重复。
     *
     * 2.49 HashMap
     * 底层数据结构是哈希表。线程不安全，效率高
     * 哈希表依赖两个方法：hashCode()和equals()
     * 执行顺序：
     * 首先判断hashCode()值是否相同
     * 是：继续执行equals(),看其返回值
     * 是true:说明元素重复，不添加
     * 是false:就直接添加到集合
     * 否：就直接添加到集合
     * 最终：
     * 自动生成hashCode()和equals()即可
     *
     * 2.50 LinkedHashMap
     * 底层数据结构由链表和哈希表组成。
     * 由链表保证元素有序。
     * 由哈希表保证元素唯一。
     *
     * 2.51 Hashtable
     * 底层数据结构是哈希表。线程安全，效率低
     * 哈希表依赖两个方法：hashCode()和equals()
     * 执行顺序：
     * 首先判断hashCode()值是否相同
     * 是：继续执行equals(),看其返回值
     * 是true:说明元素重复，不添加
     * 是false:就直接添加到集合
     * 否：就直接添加到集合
     *
     * 最终：
     * 自动生成hashCode()和equals()即可
     *
     *
     * 2.52 TreeMap
     * 底层数据结构是红黑树。(是一种自平衡的二叉树)
     * 如何保证元素唯一性呢
     * 根据比较的返回值是否是0来决定
     * 如何保证元素的排序呢
     * 两种方式
     * 自然排序(元素具备比较性)
     * 让元素所属的类实现Comparable接口
     * 比较器排序(集合具备比较性)
     * 让集合接收一个Comparator的实现类对象
     */

    /*
     * ###### 3.数组和链表的区别
     *
     * 数组静态分配内存，链表动态分配内存；
     * 数组在内存中连续，链表不连续；
     * 数组元素在栈区，链表元素在堆区；
     * 数组利用下标定位，时间复杂度为O(1)，链表定位元素时间复杂度O(n)；
     *
     * 数组插入或删除元素的时间复杂度O(n)，链表的时间复杂度O(1)。
     */

    /*
     * ###### 4. 二叉树的深度优先遍历和广度优先遍历的具体实现
     */
    public static class Node<E extends Comparable<E>> {
        E value;
        Node left;
        Node right;

        Node(E value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public static class BinarySortTree<E extends Comparable<E>> {

        private Node<E> root;

        BinarySortTree() {
            root = null;
        }

        public void insertNode(E value) {
            if (root == null) {
                root = new Node<>(value);
                return;
            }
            Node<E> currentNode = root;
            while (true) {
                if (value.compareTo(currentNode.value) > 0) {
                    if (currentNode.right == null) {
                        currentNode.right = new Node<>(value);
                        break;
                    }
                    currentNode = currentNode.right;
                } else {
                    if (currentNode.left == null) {
                        currentNode.left = new Node<>(value);
                        break;
                    }
                    currentNode = currentNode.left;
                }
            }
        }

        public Node<E> getRoot() {
            return root;
        }

        /**
         * 先序遍历二叉树（递归）根左右
         */
        public void preOrderTraverse(Node<E> node) {
            System.out.print(node.value + " ");
            if (node.left != null)
                preOrderTraverse(node.left);
            if (node.right != null)
                preOrderTraverse(node.right);
        }

        /**
         * 中序遍历二叉树（递归）左根右
         */
        public void inOrderTraverse(Node<E> node) {
            if (node.left != null)
                inOrderTraverse(node.left);
            System.out.print(node.value + " ");
            if (node.right != null)
                inOrderTraverse(node.right);
        }

        /**
         * 后序遍历二叉树（递归）左右根
         */
        public void postOrderTraverse(Node<E> node) {
            if (node.left != null)
                postOrderTraverse(node.left);
            if (node.right != null)
                postOrderTraverse(node.right);
            System.out.print(node.value + " ");
        }

        /**
         * 先序遍历二叉树（非递归）根左右
         */
        public void preOrderTraverseNoRecursion(Node<E> root) {
            // 利用栈
            LinkedList<Node> stack = new LinkedList<>();
            Node<E> currentNode;
            // add first
            stack.push(root);
            while (!stack.isEmpty()) {
                currentNode = stack.pop();
                System.out.print(currentNode.value + " ");
                if (currentNode.right != null)
                    stack.push(currentNode.right);
                if (currentNode.left != null)
                    stack.push(currentNode.left);
            }
        }

        /**
         * 中序遍历二叉树（非递归）左根右
         */
        public void inOrderTraverseNoRecursion(Node<E> root) {
            // 利用栈
            LinkedList<Node<E>> stack = new LinkedList<>();
            Node<E> currentNode = root;
            while (currentNode != null || !stack.isEmpty()) {
                // 一直循环到二叉排序树最左端的叶子结点（currentNode是null）
                while (currentNode != null) {
                    stack.push(currentNode);
                    currentNode = currentNode.left;
                }
                currentNode = stack.pop();
                System.out.print(currentNode.value + " ");
                currentNode = currentNode.right;
            }
        }

        /**
         * 后序遍历二叉树（非递归）左右根
         */
        public void postOrderTraverseNoRecursion(Node<E> root) {
            // 利用栈
            LinkedList<Node<E>> stack = new LinkedList<>();
            Node<E> currentNode = root;
            Node<E> rightNode = null;
            while (currentNode != null || !stack.isEmpty()) {
                // 一直循环到二叉排序树最左端的叶子结点（currentNode是null）
                while (currentNode != null) {
                    stack.push(currentNode);
                    currentNode = currentNode.left;
                }
                currentNode = stack.pop();
                // 当前结点没有右结点或上一个结点（已经输出的结点）是当前结点的右结点，则输出当前结点
                while (currentNode.right == null || currentNode.right == rightNode) {
                    System.out.print(currentNode.value + " ");
                    rightNode = currentNode;
                    if (stack.isEmpty()) {
                        return; // root以输出，则遍历结束
                    }
                    currentNode = stack.pop();
                }
                stack.push(currentNode); //还有右结点没有遍历
                currentNode = currentNode.right;
            }
        }

        /**
         * 广度优先遍历二叉树，又称层次遍历二叉树
         */
        public void breadthFirstTraverse(Node<E> root) {
            // 利用队列
            Queue<Node<E>> queue = new LinkedList<>();
            Node<E> currentNode;
            queue.offer(root);
            while (!queue.isEmpty()) {
                currentNode = queue.poll();
                System.out.print(currentNode.value + " ");
                if (currentNode.left != null)
                    queue.offer(currentNode.left);
                if (currentNode.right != null)
                    queue.offer(currentNode.right);
            }
        }
    }

    /*
     * ###### 5. 堆的实现
     */

    // 最大堆，元素是可比较的
    public static class MaxHeap<E extends Comparable> {

        // 用数组实现堆
        private ArrayList<E> list = new ArrayList<>();

        public MaxHeap() {
        }

        public MaxHeap(E[] objects) {
            for (int i = 0; i < objects.length; i++) {
                add(objects[i]);
            }
        }

        public void add(E object) {
            list.add(object);
            int currentIndex = list.size() - 1;
            while (currentIndex > 0) {
                //找到该结点的父结点
                int parentIndex = (currentIndex - 1) / 2;
                if (list.get(currentIndex).compareTo(list.get(parentIndex)) > 0) {
                    // 如果当前结点的值大于父结点的值，就交换
                    E temp = list.get(currentIndex);
                    list.set(currentIndex, list.get(parentIndex));
                    list.set(parentIndex, temp);
                } else {
                    // 继续遍历
                    currentIndex = parentIndex;
                }
            }
        }

        /**
         * 删除并返回根结点，堆的特点是移除了根结点，最后还是堆
         */
        public E remove() {
            if (list.size() == 0)
                return null;
            // 根结点
            E removeObject = list.get(0);
            // 把最后一个结点放在根结点位置，重新设置根结点的值
            list.set(0, list.get(list.size() - 1));
            // 删除最后一个结点，此时它已被设置城根结点
            list.remove(list.size() - 1);
            int currentIndex = 0;
            while (currentIndex < list.size()) {
                // 左子树的索引
                int leftChildIndex = 2 * currentIndex + 1;
                // 右子树的索引
                int rightChildIndex = 2 * currentIndex + 2;
                if (leftChildIndex >= list.size()) {
                    break;
                }
                // 比较左右结点的值，使maxIndex指向值最大的结点
                int maxIndex = leftChildIndex;
                if (rightChildIndex < list.size()) {
                    if (list.get(maxIndex).compareTo(list.get(rightChildIndex)) < 0) {
                        maxIndex = rightChildIndex;
                    }
                }
                // 如果当前结点的值小于其左右结点的最大值，就交换两个结点
                if (list.get(currentIndex).compareTo(list.get(maxIndex)) < 0) {
                    E temp = list.get(maxIndex);
                    list.set(maxIndex, list.get(currentIndex));
                    list.set(currentIndex, temp);
                    currentIndex = maxIndex;
                } else {
                    break;
                }
            }
            return removeObject;
        }

        public int getSize() {
            return list.size();
        }
    }

    /*
     * ###### 6. 堆和栈的区别
     *
     * 首先在数据结构上要知道堆栈，尽管我们这么称呼它，但实际上堆栈是两种数据结构：堆和栈。
     *
     * 堆和栈都是一种数据项按序排列的数据结构。
     *
     * 栈就像装数据的桶或箱子
     *
     * 我们先从大家比较熟悉的栈说起吧，它是一种具有后进先出性质的数据结构，也就是说后存放的先取，先存放的后取。
     *
     * 这就如同我们要取出放在箱子里面底下的东西（放入的比较早的物体），我们首先要移开压在它上面的物体（放入的比较晚的物体）。
     *
     * 堆像一棵倒过来的树
     *
     * 而堆就不同了，堆是一种经过排序的树形数据结构，每个结点都有一个值。
     * 通常我们所说的堆的数据结构，是指二叉堆。
     * 堆的特点是根结点的值最小（或最大），且根结点的两个子树也是一个堆。
     * 由于堆的这个特性，常用来实现优先队列，堆的存取是随意，这就如同我们在图书馆的书架上取书，虽然书的摆放是有顺序的，
     * 但是我们想取任意一本时不必像栈一样，先取出前面所有的书，书架这种机制不同于箱子，我们可以直接取出我们想要的书。
     *
     *
     * #### 内存分配中的栈和堆
     *
     * 堆栈空间分配
     *
     * 栈（操作系统）：由操作系统自动分配释放 ，存放函数的参数值，局部变量的值等。其操作方式类似于数据结构中的栈。
     *
     * 堆（操作系统）： 一般由程序员分配释放， 若程序员不释放，程序结束时可能由OS回收，分配方式倒是类似于链表。
     * 堆栈缓存方式
     *
     * 栈使用的是一级缓存， 他们通常都是被调用时处于存储空间中，调用完毕立即释放。
     *
     * 堆则是存放在二级缓存中，生命周期由虚拟机的垃圾回收算法来决定（并不是一旦成为孤儿对象就能被回收）。
     * 所以调用这些对象的速度要相对来得低一些。
     */

    /*
     * ###### 7. 堆和树的区别
     *
     * 以小根堆为例，堆的特点是双亲结点的关键字必然小于等于孩子结点的关键字，而两个孩子结点的关键字没有次序规定
     * 而二叉排序树中，每个双亲结点的关键字均大于左子树结点的关键字，均小于右子树j结点的关键字，也就是说，
     * 每个双亲结点的左右孩子的关键字有次序关系。
     *
     * 这样，当对两种树执行中序遍历后，二叉排序树会得到一个有序的序列，而堆不一定。
     */

    public static void main(String[] args) {
    }
}
