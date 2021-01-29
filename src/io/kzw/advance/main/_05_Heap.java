package io.kzw.advance.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 堆相关.
 *
 * @author kzw on 2018/09/17.
 */
public final class _05_Heap {

    // ================ from java数据结构----堆 https://www.cnblogs.com/g177w/p/8469399.html ================

    /*
     * 堆：堆是一种树，由它实现的优先级队列的插入和删除的时间复杂度都是O(logn)，
     *
     * 用堆实现的优先级队列虽然和数组实现相比较删除慢了些，但插入的时间快的多了。
     *
     * 当速度很重要且有很多插入操作时，可以选择堆来实现优先级队列。
     *
     * java的堆和数据结构堆：java的堆是程序员用new能得到的计算机内存的可用部分。而数据结构的堆是一种特殊的二叉树。
     *
     * 1) 堆是具有如下特点的二叉树：
     *
     * 它是完全二叉树，也就是说除了树的最后一层节点不需要是满的，其他的每一层从左到右都必须是满的。
     *
     * 2) 它常常用数组实现。
     *
     * ----------------------------------------------------------------------------------
     *                                  100
     *                 90                |                  80
     *                 |                                    |
     *       30        |       69                  50       |       70
     *       |                 |                   |
     *       |                 |                   |
     *       |                 |                   |
     *    20    10         40     55            45    5
     *
     *    可以看出：从根到叶子节点的每一条路径，节点都是按降序或者升序排列的
     *
     *    数组表现：（按照广度优先遍历 - 按层次，深度优先的话是前序遍历，中序遍历，后序遍历）
     *
     *    [100, 90, 80, 30, 69, 50, 70, 20, 10, 40, 55, 45, 5]
     * ----------------------------------------------------------------------------------
     *
     * 3) 堆中每一个节点都满足堆的条件，也就是说每一个关键字的值都大于或等于这个节点的子节点的关键字值。
     * 　堆是完全二叉树的事实说明了表示堆的数组中没有空项，即从0-->n-1的每个数据单元都有数据项。
     *
     * 4) 堆在存储器中的表示是数组，堆只是一个概念上的表示。
     *
     * 5) 堆的弱序：堆和二叉搜索树相比是弱序的，在二叉搜索树中，当前节点的值总是比左子节点的值大，
     *    却比它的右子节点的值小，因此按序遍历相对容易。
     *
     *    而堆的组织规则弱，它只要求从根到叶子节点的每一条路径，节点都是按降序或者升序排列的。
     *    同一节点的左右子节点都没有规律。因此，堆不支持按序遍历，也不能在堆上便利的查找指定关键字，
     *
     *    因为在查找的过程中，没有足够的信息决定选择通过节点的两个那一个走向下一层。
     *    它也不能在少于O(logn)的时间内删除一个指定的节点，因为没有办法找到这个节点。
     *
     *    因此，堆的这种近乎无序的规则似乎毫无用处，不过对于快速移除最大节点的操作，以及快速插入新节点的操作，
     *    这种顺序已经足够了。这些操作是使用堆作为优先级队列所需要的全部操作。
     *
     *    (快速移除最大结点，快速插入新结点；而查找和删除比较慢）
     *
     * 6) 移除操作：移除是指删掉关键字值最大的节点，即根节点。移除思路如下：
     *
     *    6.1 移走根;
     *    6.2 把左后一个节点移到根的位置;
     *    6.3 直到它在一个大于它的节点之下，小于它的节点之上为止。
     *
     *    说明：在被筛选节点的每个暂时停留的位置，向下筛选的算法总是要检查那一个子节点更大，
     *    然后目标节点和较大的子节点交换位置，如果要把目标节点和较小的子节点交换，
     *    那么这个子节点就会变成大子节点的父节点，这就违背了堆的条件。
     *
     * 7) 堆的插入：插入使用向上筛选，节点最后插入到数组最后第一个空着的单元中，数组容量大小增加1。
     *
     * 　 说明：向上筛选的算法比向下筛选的算法相对简单，因为它不需要比较两个子节点关键字值的大小，节点只有一个父节点。
     *    目标节点主要和它的父亲节点换位即可。
     *
     * 8) 用数组表示一棵树时，如果数组中节点的索引位x，则
     *
     *    a.它的父节点的下标是：(x-1)/2；
     *    b.它的左子节点的下标为 2*x + 1；
     *    c.它的右子节点的下标是 2*x + 2；
     *
     * 10) 堆的效率：上述操作的时间复杂度是：O(logn)。
     *
     * 11) 堆排序实现思路：使用insert()向堆中插入所有无序的数据项，然后重复使用remove()方法，就可以按序移除所有数据项，
     *     它的效率和快速排序类似，都是O(NlogN)，但快排稍微快些，因为堆插入时的向下筛选多出的比较所占用的时间。
     */

    public static class Node {

        private int key;

        public Node(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }

    public static class Heap {

        // 数组实现
        private Node[] heapArray;

        // 最大尺寸
        private int maxSize;

        // 当前尺寸
        private int currentSize;

        public Heap(int size) {
            maxSize = size;
            heapArray = new Node[maxSize];
            currentSize = 0;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public boolean insert(int key) {
            if (currentSize == maxSize)
                return false;
            Node newNode = new Node(key);
            heapArray[currentSize] = newNode;
            trickleUp(currentSize++);
            return true;
        }

        private void trickleUp(int index) {
            // 父结点
            int parent = (index - 1) / 2;
            Node bottom = heapArray[index];
            // 根结点到某个结点是逆序或者升序的，如果当前结点的值比父结点还大，互换顺序，直到当前结点的值比父结点小
            while (index > 0 && heapArray[parent].getKey() < bottom.getKey()) {
                heapArray[index] = heapArray[parent];
                index = parent;
                parent = (parent - 1) / 2;
            }
            heapArray[index] = bottom;
        }

        public Node remove() {
            // 移除根结点
            Node root = heapArray[0];
            // 最后一个元素移到根处
            heapArray[0] = heapArray[--currentSize];
            trickleDown(0);
            return root;
        }

        private void trickleDown(int index) {
            int largeChild;
            Node top = heapArray[index];
            while (index < currentSize / 2) {
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                if (rightChild < currentSize && heapArray[leftChild].getKey() <
                        heapArray[rightChild].getKey()) {
                    largeChild = rightChild;
                } else {
                    largeChild = leftChild;
                }
                if (top.getKey() >= heapArray[largeChild].getKey()) {
                    break;
                }
                heapArray[index] = heapArray[largeChild];
                index = largeChild;
            }
            heapArray[index] = top;
        }

        public boolean change(int index, int newvalue) {
            if (index < 0 || index >= currentSize)
                return false;
            int oldvalue = heapArray[index].getKey();
            heapArray[index].setKey(newvalue);
            if (oldvalue < newvalue)
                trickleUp(index);
            else
                trickleDown(index);
            return true;
        }

        public void displayHeap() {
            System.out.print("heapArray : ");
            for (int i = 0; i < currentSize; i++) {
                if (heapArray[i] != null)
                    System.out.print(heapArray[i].getKey() + "  ");
                else
                    System.out.print("--");
            }
            System.out.println("");
            int nBlanks = 32;
            int itemsPerrow = 1;
            int column = 0;
            int j = 0;
            String dots = "........................";
            System.out.println(dots + dots);
            while (currentSize > 0) {
                if (column == 0)
                    for (int i = 0; i < nBlanks; i++) {
                        System.out.print(" ");
                    }
                System.out.print(heapArray[j].getKey());
                if (++j == currentSize)
                    break;
                if (++column == itemsPerrow) {
                    nBlanks /= 2;
                    itemsPerrow *= 2;
                    column = 0;
                    System.out.println();
                } else
                    for (int i = 0; i < nBlanks * 2 - 2; i++)
                        System.out.print(' ');
            }
            System.out.println("\n" + dots + dots);
        }

        public void displayArray() {
            for (int i = 0; i < maxSize; i++)
                System.out.print(heapArray[i].getKey() + " ");
            System.out.println();
        }

        public void insertAt(int index, Node newnode) {
            heapArray[index] = newnode;
        }

        public void incrementSize() {
            currentSize++;
        }
    }

    /**
     * 基于堆的排序----堆排序
     */
    public static void heapSort() {
        int size;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        size = in.nextInt();
        Heap theheap = new Heap(size);
        for (int i = 0; i < size; i++) {
            int random = (int) (Math.random() * 100);
            Node node = new Node(random);
            theheap.insertAt(i, node);
            theheap.incrementSize();
        }
        System.out.print("random: ");
        theheap.displayArray();
        for (int i = size / 2 - 1; i >= 0; i--) {
            theheap.trickleDown(i);
        }
        System.out.print("heap: ");
        theheap.displayArray();
        theheap.displayHeap();
        for (int i = size - 1; i >= 0; i--) {
            Node node = theheap.remove();
            theheap.insertAt(i, node);
        }
        System.out.print("sorted: ");
        theheap.displayArray();
    }

    // ================ from 数据结构与算法系列 目录 https://blog.csdn.net/l_215851356/article/details/77659462 ================

    /*
     * 堆和二叉堆的介绍
     *
     * 堆的定义
     *
     * 堆(heap)，这里所说的堆是数据结构中的堆，而不是内存模型中的堆。堆通常是一个可以被看做一棵树，它满足下列性质：
     * [性质一] 堆中任意节点的值总是不大于(不小于)其子节点的值；
     * [性质二] 堆总是一棵完全树。
     *
     * 将任意节点不大于其子节点的堆叫做最小堆或小根堆，而将任意节点不小于其子节点的堆叫做最大堆或大根堆。
     *
     * 常见的堆有二叉堆、左倾堆、斜堆、二项堆、斐波那契堆等等。
     *
     * ----------------------------------------------------------------------------------------------
     * 二叉堆的定义
     *
     * 二叉堆是完全二元树或者是近似完全二元树，它分为两种：最大堆和最小堆。
     *
     * 最大堆：父结点的键值总是大于或等于任何一个子节点的键值；
     * 最小堆：父结点的键值总是小于或等于任何一个子节点的键值。示意图如下：
     *
     * 最大堆：
     *                                           110
     *                                            |
     *                  100                                                90
     *           |              |                                  |                |
     *           |              |                                  |                |
     *           40             80                                20                60
     *       |       |       |      |
     *       |       |       |      |
     *       |       |       |      |
     *       10      30      50     70
     *
     * 数组表现：（按照广度优先遍历 - 按层次，深度优先的话是前序遍历，中序遍历，后序遍历）
     * [110, 100, 90, 40, 80, 20, 60, 10, 30, 50, 70]
     *
     *
     * 最小堆：
     *                                           10
     *                                            |
     *                  20                                                40
     *           |              |                                  |                |
     *           |              |                                  |                |
     *           30             60                                80                100
     *       |       |       |      |
     *       |       |       |      |
     *       |       |       |      |
     *       50      70      90     100
     *
     * 数组表现：（按照广度优先遍历 - 按层次，深度优先的话是前序遍历，中序遍历，后序遍历）
     * [10, 20, 40, 30, 60, 80, 100, 50, 70, 90, 100]
     *
     * 二叉堆一般都通过"数组"来实现。
     *
     * 数组实现的二叉堆，父节点和子节点的位置存在一定的关系。
     *
     * 有时候，我们将"二叉堆的第一个元素"放在数组索引0的位置，有时候放在1的位置。
     * 当然，它们的本质一样(都是二叉堆)，只是实现上稍微有一丁点区别。
     *
     * 假设"第一个元素"在数组中的索引为 0 的话，则父节点和子节点的位置关系如下：
     * (01) 索引为i的左孩子的索引是 (2*i+1);
     * (02) 索引为i的左孩子的索引是 (2*i+2);
     * (03) 索引为i的父结点的索引是 floor((i-1)/2);
     * ----------------------------------------------------------------------------------------------
     */

    /**
     * 二叉堆(最大堆)
     */
    public static class MaxHeap<T extends Comparable<T>> {

        // 队列(实际上是动态数组ArrayList的实例)
        private List<T> mHeap;

        public MaxHeap() {
            this.mHeap = new ArrayList<>();
        }

        /**
         * 最大堆的向下调整算法
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
         *     end   -- 截至范围(一般为数组中最后一个元素的索引)
         */
        protected void filterdown(int start, int end) {
            // 当前(current)节点的位置
            int c = start;
            // 左(left)孩子的位置
            int l = 2 * c + 1;
            // 当前(current)节点的大小
            T tmp = mHeap.get(c);

            while (l <= end) {
                int cmp = mHeap.get(l).compareTo(mHeap.get(l + 1));
                // "l"是左孩子，"l+1"是右孩子
                if (l < end && cmp < 0)
                    // 左右两孩子中选择较大者，即mHeap[l+1]
                    l++;
                cmp = tmp.compareTo(mHeap.get(l));
                if (cmp >= 0)
                    // 调整结束
                    break;
                else {
                    mHeap.set(c, mHeap.get(l));
                    c = l;
                    l = 2 * l + 1;
                }
            }
            mHeap.set(c, tmp);
        }

        /**
         * 删除最大堆中的data
         *
         * 返回值：
         *      0，成功
         *     -1，失败
         */
        public int remove(T data) {
            // 如果"堆"已空，则返回-1
            if (mHeap.isEmpty() == true)
                return -1;

            // 获取data在数组中的索引
            int index = mHeap.indexOf(data);
            if (index == -1)
                return -1;

            int size = mHeap.size();
            // 用最后元素填补
            mHeap.set(index, mHeap.get(size - 1));
            // 删除最后的元素
            mHeap.remove(size - 1);

            if (mHeap.size() > 1)
                // 从index号位置开始自上向下调整为最小堆
                filterdown(index, mHeap.size() - 1);

            return 0;
        }

        /**
         * 最大堆的向上调整算法(从start开始向上直到0，调整堆)
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
         */
        protected void filterup(int start) {
            // 当前节点(current)的位置
            int c = start;
            // 父(parent)结点的位置
            int p = (c - 1) / 2;
            // 当前节点(current)的大小
            T tmp = mHeap.get(c);

            while (c > 0) {
                int cmp = mHeap.get(p).compareTo(tmp);
                if (cmp >= 0)
                    break;
                else {
                    mHeap.set(c, mHeap.get(p));
                    c = p;
                    p = (p - 1) / 2;
                }
            }
            mHeap.set(c, tmp);
        }

        /**
         * 将data插入到二叉堆中
         */
        public void insert(T data) {
            int size = mHeap.size();
            // 将"数组"插在表尾
            mHeap.add(data);
            // 向上调整堆
            filterup(size);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mHeap.size(); i++)
                sb.append(mHeap.get(i) + " ");

            return sb.toString();
        }
    }

    private static void testMaxHeap() {
        int i;
        int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        MaxHeap<Integer> tree = new MaxHeap<>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
            tree.insert(a[i]);
        }

        System.out.printf("\n== 最 大 堆: %s", tree);

        i = 85;
        tree.insert(i);
        System.out.printf("\n== 添加元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);

        i = 90;
        tree.remove(i);
        System.out.printf("\n== 删除元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);
        System.out.printf("\n");
    }

    /**
     * 二叉堆(最小堆)
     */
    public static class MinHeap<T extends Comparable<T>> {

        // 存放堆的数组
        private List<T> mHeap;

        public MinHeap() {
            this.mHeap = new ArrayList<T>();
        }

        /**
         * 最小堆的向下调整算法
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
         *     end   -- 截至范围(一般为数组中最后一个元素的索引)
         */
        protected void filterdown(int start, int end) {
            // 当前(current)节点的位置
            int c = start;
            // 左(left)孩子的位置
            int l = 2 * c + 1;
            // 当前(current)节点的大小
            T tmp = mHeap.get(c);

            while (l <= end) {
                int cmp = mHeap.get(l).compareTo(mHeap.get(l + 1));
                // "l"是左孩子，"l+1"是右孩子
                if (l < end && cmp > 0)
                    // 左右两孩子中选择较小者，即mHeap[l+1]
                    l++;

                cmp = tmp.compareTo(mHeap.get(l));
                if (cmp <= 0)
                    // 调整结束
                    break;
                else {
                    mHeap.set(c, mHeap.get(l));
                    c = l;
                    l = 2 * l + 1;
                }
            }
            mHeap.set(c, tmp);
        }

        /**
         * 最小堆的删除
         *
         * 返回值：
         *     成功，返回被删除的值
         *     失败，返回null
         */
        public int remove(T data) {
            // 如果"堆"已空，则返回-1
            if (mHeap.isEmpty() == true)
                return -1;

            // 获取data在数组中的索引
            int index = mHeap.indexOf(data);
            if (index == -1)
                return -1;

            int size = mHeap.size();
            // 用最后元素填补
            mHeap.set(index, mHeap.get(size - 1));
            // 删除最后的元素
            mHeap.remove(size - 1);

            if (mHeap.size() > 1)
                // 从index号位置开始自上向下调整为最小堆
                filterdown(index, mHeap.size() - 1);
            return 0;
        }

        /**
         * 最小堆的向上调整算法(从start开始向上直到0，调整堆)
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
         */
        protected void filterup(int start) {
            // 当前节点(current)的位置
            int c = start;
            // 父(parent)结点的位置
            int p = (c - 1) / 2;
            // 当前节点(current)的大小
            T tmp = mHeap.get(c);

            while (c > 0) {
                int cmp = mHeap.get(p).compareTo(tmp);
                if (cmp <= 0)
                    break;
                else {
                    mHeap.set(c, mHeap.get(p));
                    c = p;
                    p = (p - 1) / 2;
                }
            }
            mHeap.set(c, tmp);
        }

        /**
         * 将data插入到二叉堆中
         */
        public void insert(T data) {
            int size = mHeap.size();
            // 将"数组"插在表尾
            mHeap.add(data);
            // 向上调整堆
            filterup(size);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mHeap.size(); i++)
                sb.append(mHeap.get(i) + " ");

            return sb.toString();
        }
    }

    private static void testMinHeap() {
        int i;
        int a[] = {80, 40, 30, 60, 90, 70, 10, 50, 20};
        MinHeap<Integer> tree = new MinHeap<>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
            tree.insert(a[i]);
        }

        System.out.printf("\n== 最 小 堆: %s", tree);

        i = 15;
        tree.insert(i);
        System.out.printf("\n== 添加元素: %d", i);
        System.out.printf("\n== 最 小 堆: %s", tree);

        i = 10;
        tree.remove(i);
        System.out.printf("\n== 删除元素: %d", i);
        System.out.printf("\n== 最 小 堆: %s", tree);
        System.out.printf("\n");
    }

    /*
     * 左斜堆
     *
     * 左倾堆(leftist tree 或 leftist heap)，又被成为左偏树、左偏堆，最左堆等。
     *
     * 它和二叉堆一样，都是优先队列实现方式。当优先队列中涉及到"对两个优先队列进行合并"的问题时，
     * 二叉堆的效率就无法令人满意了，而本文介绍的左倾堆，则可以很好地解决这类问题。
     *
     * 左倾堆的定义:
     * ------------------------------------------------------------------
     *                              10
     *                               |
     *             24                                  12
     *         |        |                        |             |
     *         |        |                        |             |
     *        30        36                      20             16
     *                                        |
     *                                        |
     *                                        40
     *
     * 上面是一颗左倾树，它的节点除了和二叉树的节点一样具有左右子树指针外，还有两个属性：键值和零距离。
     *
     * (01) 键值的作用是来比较节点的大小，从而对节点进行排序。
     * (02) 零距离(英文名NPL，即Null Path Length)则是从一个节点到一个"最近的不满节点"的路径长度。
     *      不满节点是指该该节点的左右孩子至少有有一个为NULL。叶节点的NPL为0，NULL节点的NPL为-1。
     *
     * 左倾堆有以下几个基本性质：
     * [性质1] 节点的键值小于或等于它的左右子节点的键值。
     * [性质2] 节点的左孩子的NPL >= 右孩子的NPL。
     * [性质3] 节点的NPL = 它的右孩子的NPL + 1。
     * ------------------------------------------------------------------
     *
     * 合并操作是左倾堆的重点。合并两个左倾堆的基本思想如下：
     * (01) 如果一个空左倾堆与一个非空左倾堆合并，返回非空左倾堆。
     * (02) 如果两个左倾堆都非空，那么比较两个根节点，取较小堆的根节点为新的根节点。将"较小堆的根节点的右孩子"和"较大堆"进行合并。
     * (03) 如果新堆的右孩子的NPL > 左孩子的NPL，则交换左右孩子。
     * (04) 设置新堆的根节点的NPL = 右子堆NPL + 1
     */

    /**
     * 左倾堆
     */
    public static class LeftistHeap<T extends Comparable<T>> {

        // 根结点
        private LeftistNode<T> mRoot;

        private class LeftistNode<T extends Comparable<T>> {
            // 关键字(键值)
            T key;
            // 零路经长度(Null Path Length)
            int npl;
            // 左孩子
            LeftistNode<T> left;
            // 右孩子
            LeftistNode<T> right;

            public LeftistNode(T key, LeftistNode<T> left, LeftistNode<T> right) {
                this.key = key;
                this.npl = 0;
                this.left = left;
                this.right = right;
            }

            public String toString() {
                return "key:" + key;
            }
        }

        public LeftistHeap() {
            mRoot = null;
        }

        /**
         * 前序遍历"左倾堆"
         */
        private void preOrder(LeftistNode<T> heap) {
            if (heap != null) {
                System.out.print(heap.key + " ");
                preOrder(heap.left);
                preOrder(heap.right);
            }
        }

        public void preOrder() {
            preOrder(mRoot);
        }

        /**
         * 中序遍历"左倾堆"
         */
        private void inOrder(LeftistNode<T> heap) {
            if (heap != null) {
                inOrder(heap.left);
                System.out.print(heap.key + " ");
                inOrder(heap.right);
            }
        }

        public void inOrder() {
            inOrder(mRoot);
        }

        /**
         * 后序遍历"左倾堆"
         */
        private void postOrder(LeftistNode<T> heap) {
            if (heap != null) {
                postOrder(heap.left);
                postOrder(heap.right);
                System.out.print(heap.key + " ");
            }
        }

        public void postOrder() {
            postOrder(mRoot);
        }

        /**
         * 合并"左倾堆x"和"左倾堆y"
         */
        private LeftistNode<T> merge(LeftistNode<T> x, LeftistNode<T> y) {
            if (x == null) return y;
            if (y == null) return x;

            // 合并x和y时，将x作为合并后的树的根；
            // 这里的操作是保证: x的key < y的key
            if (x.key.compareTo(y.key) > 0) {
                LeftistNode<T> tmp = x;
                x = y;
                y = tmp;
            }

            // 将x的右孩子和y合并，"合并后的树的根"是x的右孩子。
            x.right = merge(x.right, y);

            // 如果"x的左孩子为空" 或者 "x的左孩子的npl<右孩子的npl"
            // 则，交换x和y
            if (x.left == null || x.left.npl < x.right.npl) {
                LeftistNode<T> tmp = x.left;
                x.left = x.right;
                x.right = tmp;
            }
            if (x.right == null || x.left == null)
                x.npl = 0;
            else
                x.npl = (x.left.npl > x.right.npl) ? (x.right.npl + 1) : (x.left.npl + 1);

            return x;
        }

        public void merge(LeftistHeap<T> other) {
            this.mRoot = merge(this.mRoot, other.mRoot);
        }

        /**
         * 新建结点(key)，并将其插入到左倾堆中
         *
         * 参数说明：
         *     key 插入结点的键值
         */
        public void insert(T key) {
            LeftistNode<T> node = new LeftistNode<T>(key, null, null);

            // 如果新建结点失败，则返回。
            if (node != null)
                this.mRoot = merge(this.mRoot, node);
        }

        /**
         * 删除根结点
         *
         * 返回值：
         *     返回被删除的节点的键值
         */
        public T remove() {
            if (this.mRoot == null)
                return null;

            T key = this.mRoot.key;
            LeftistNode<T> l = this.mRoot.left;
            LeftistNode<T> r = this.mRoot.right;

            this.mRoot = null;          // 删除根节点
            this.mRoot = merge(l, r);   // 合并左右子树

            return key;
        }

        /**
         * 销毁左倾堆
         */
        private void destroy(LeftistNode<T> heap) {
            if (heap == null)
                return;

            if (heap.left != null)
                destroy(heap.left);
            if (heap.right != null)
                destroy(heap.right);

            heap = null;
        }

        public void clear() {
            destroy(mRoot);
            mRoot = null;
        }

        /**
         * 打印"左倾堆"
         *
         * key        -- 节点的键值
         * direction  --  0，表示该节点是根节点;
         *               -1，表示该节点是它的父结点的左孩子;
         *                1，表示该节点是它的父结点的右孩子。
         */
        private void print(LeftistNode<T> heap, T key, int direction) {
            if (heap != null) {
                if (direction == 0)    // heap是根节点
                    System.out.printf("%2d(%d) is root\n", heap.key, heap.npl);
                else                // heap是分支节点
                    System.out.printf("%2d(%d) is %2d's %6s child\n", heap.key, heap.npl, key,
                            direction == 1 ? "right" : "left");

                print(heap.left, heap.key, -1);
                print(heap.right, heap.key, 1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }
    }

    private static void testLeftistHeap() {
        int a[] = {10, 40, 24, 30, 36, 20, 12, 16};
        int b[] = {17, 13, 11, 15, 19, 21, 23};
        LeftistHeap<Integer> ha = new LeftistHeap<>();
        LeftistHeap<Integer> hb = new LeftistHeap<>();

        System.out.printf("== 左倾堆(ha)中依次添加: ");
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
            ha.insert(a[i]);
        }
        System.out.printf("\n== 左倾堆(ha)的详细信息: \n");
        ha.print();


        System.out.printf("\n== 左倾堆(hb)中依次添加: ");
        for (int i = 0; i < b.length; i++) {
            System.out.printf("%d ", b[i]);
            hb.insert(b[i]);
        }
        System.out.printf("\n== 左倾堆(hb)的详细信息: \n");
        hb.print();

        // 将"左倾堆hb"合并到"左倾堆ha"中。
        ha.merge(hb);
        System.out.printf("\n== 合并ha和hb后的详细信息: \n");
        ha.print();
    }

    /*
     * 斜堆的介绍
     *
     * 斜堆(Skew heap)也叫自适应堆(self-adjusting heap)，它是左倾堆的一个变种。
     * 和左倾堆一样，它通常也用于实现优先队列。它的合并操作的时间复杂度也是O(lg n)。
     *
     * 相比于左倾堆，斜堆的节点没有"零距离"这个属性。除此之外，它们斜堆的合并操作也不同。斜堆的合并操作算法如下：
     * (01) 如果一个空斜堆与一个非空斜堆合并，返回非空斜堆。
     * (02) 如果两个斜堆都非空，那么比较两个根节点，取较小堆的根节点为新的根节点。将"较小堆的根节点的右孩子"和"较大堆"进行合并。
     * (03) 合并后，交换新堆根节点的左孩子和右孩子。
     *      第(03)步是斜堆和左倾堆的合并操作差别的关键所在，如果是左倾堆，则合并后要比较左右孩子的零距离大小，
     *      若右孩子的零距离 > 左孩子的零距离，则交换左右孩子；最后，在设置根的零距离。
     */

    /*
     * 二项堆
     *
     * 二项堆是二项树的 集合 。在了解二项堆之前，先对二项树进行介绍。
     *
     * ---------------------------------------------------------------------------
     * 二项树是一种递归定义的有序树。它的递归定义如下：
     * (01) 二项树B0只有一个结点；
     * (02) 二项树Bk由两棵二项树B(k-1)组成的，其中一棵树是另一棵树根的最左孩子。
     *
     * 如下：
     *
     *
     *
     *                                     o
     *                               /   / |
     *                o           o     o  o
     *              / |         / |     |
     *       o    o   o        o  o     o
     *       |    |            |
     *  o    o    o            o
     *  B0   B1     B2                B3
     *
     *
     *  上面B0, B1, B2, B3都是二项树，对比前面提到的二项树的定义：B0只有一个结点，B1由两个B0
     *  组成，B2由两个B1组成，B3由两个B2组成。而且，当两颗相同的二项树组成另一颗树时，其中一棵树
     *  是另一棵树的最左孩子。
     *
     *
     *
     *  二项树的性质：
     *
     *  [性质一] Bk共有2的k次方个节点。
     *          如上图所示，B0有2的0次方=1节点，B1有2的1次方=2个节点，B2有2的2次方=4个节点，...
     *
     *  [性质二] Bk的高度为k。
     *          如上图所示，B0的高度为0，B1的高度为1，B2的高度为2，...
     *
     *  [性质三] Bk在深度i处恰好有C(k,i)个节点，其中i=0,1,2,...,k。
     *         C(k,i)是高中数学中阶乘元素，例如，C(10,3)=(10*9*8) / (3*2*1)=240
     *         B4中深度为0的节点C(4,0)=1
     *         B4中深度为1的节点C(4,1)= 4 / 1 = 4
     *         B4中深度为2的节点C(4,2)= (4*3) / (2*1) = 6
     *         B4中深度为3的节点C(4,3)= (4*3*2) / (3*2*1) = 4
     *         B4中深度为4的节点C(4,4)= (4*3*2*1) / (4*3*2*1) = 1
     *         合计得到B4的节点分布是(1,4,6,4,1)。
     *
     * [性质四] 根的度数为k，它大于任何其它节点的度数。
     *         节点的度数是该结点拥有的子树的数目。
     * --------------------------------------------------------------------------------
     *
     *
     *
     * --------------------------------------------------------------------------------------
     * 二项堆的介绍
     *
     * 二项堆和之前所讲的堆(二叉堆、左倾堆、斜堆)一样，也是用于实现优先队列的。
     *
     * 二项堆是指满足以下性质的二项树的 集合 ：
     * (01) 每棵二项树都满足最小堆性质。即，父节点的关键字 <= 它的孩子的关键字。
     * (02) 不能有两棵或以上的二项树具有相同的度数(包括度数为0)。换句话说，具有度数k的二项树有0个或1个。
     *
     * (B0)               (B2)                                       (B3)
     *  13 --------------> 11 ------------------------------------->    6
     *                   / |                                       /   /|
     *                  /  |                                      /   / |
     *                 24  48                                    /   /  |
     *                 |                                        18   9  23
     *                 |                                      / |    |
     *                 52                                    /  |    |
     *                                                      /   |    |
     *                                                     20   35   31
     *
     *  上图就是一棵二项堆，它由二项树B0、B2和B3组成。
     *  对比二项堆的定义：(01)二项树B0、B2、B3都是最小堆；(02)二项堆不包含相同度数的二项树。
     *  节点的度数是该结点拥有的子树的数目。
     *
     *  二项堆的第(01)个性质保证了二项堆的最小节点就是某个二项树的根节点，
     *  第(02)个性质则说明结点数为n的二项堆最多只有log{n} + 1棵二项树。
     *  实际上，将包含n个节点的二项堆，表示成若干个2的指数和(或者转换成二进制)，则每一个2个指数都对应一棵二项树。
     *  例如，13(二进制是1101)的2个指数和为13=23 + 22+ 20, 因此具有13个节点的二项堆由度数为3, 2, 0的三棵二项树组成。
     * --------------------------------------------------------------------------------------
     */

    /**
     * 二项堆
     */
    public static class BinomialHeap<T extends Comparable<T>> {

        // 根结点
        private BinomialNode<T> mRoot;

        private class BinomialNode<T extends Comparable<T>> {
            // 关键字(键值)
            T key;
            // 度数
            int degree;
            // 左孩子
            BinomialNode<T> child;
            // 父节点
            BinomialNode<T> parent;
            // 兄弟节点
            BinomialNode<T> next;

            public BinomialNode(T key) {
                this.key = key;
                this.degree = 0;
                this.child = null;
                this.parent = null;
                this.next = null;
            }

            public String toString() {
                return "key:" + key;
            }
        }

        public BinomialHeap() {
            mRoot = null;
        }

        /**
         * 获取二项堆中的最小节点的键值
         */
        public T minimum() {
            if (mRoot == null)
                return null;

            // x是用来遍历的当前节点
            BinomialNode<T> x, prev_x;
            // y是最小节点
            BinomialNode<T> y, prev_y;

            prev_x = mRoot;
            x = mRoot.next;
            prev_y = null;
            y = mRoot;
            // 找到最小节点
            while (x != null) {
                if (x.key.compareTo(y.key) < 0) {
                    y = x;
                    prev_y = prev_x;
                }
                prev_x = x;
                x = x.next;
            }

            return y.key;
        }

        /**
         * 合并两个二项堆：将child合并到root中
         */
        private void link(BinomialNode<T> child, BinomialNode<T> root) {
            child.parent = root;
            child.next = root.child;
            root.child = child;
            root.degree++;
        }

        /**
         * 将h1, h2中的根表合并成一个按度数递增的链表，返回合并后的根节点
         */
        private BinomialNode<T> merge(BinomialNode<T> h1, BinomialNode<T> h2) {
            if (h1 == null) return h2;
            if (h2 == null) return h1;

            // root是新堆的根，h3用来遍历h1和h3的。
            BinomialNode<T> pre_h3, h3, root = null;

            pre_h3 = null;
            // 整个while，h1, h2, pre_h3, h3都在往后顺移
            while ((h1 != null) && (h2 != null)) {

                if (h1.degree <= h2.degree) {
                    h3 = h1;
                    h1 = h1.next;
                } else {
                    h3 = h2;
                    h2 = h2.next;
                }

                if (pre_h3 == null) {
                    pre_h3 = h3;
                    root = h3;
                } else {
                    pre_h3.next = h3;
                    pre_h3 = h3;
                }

                if (h1 != null) {
                    h3.next = h1;
                } else {
                    h3.next = h2;
                }
            }
            return root;
        }

        /**
         * 合并二项堆：将h1, h2合并成一个堆，并返回合并后的堆
         */
        private BinomialNode<T> union(BinomialNode<T> h1, BinomialNode<T> h2) {
            BinomialNode<T> root;

            // 将h1, h2中的根表合并成一个按度数递增的链表root
            root = merge(h1, h2);
            if (root == null)
                return null;

            BinomialNode<T> prev_x = null;
            BinomialNode<T> x = root;
            BinomialNode<T> next_x = x.next;
            while (next_x != null) {

                if ((x.degree != next_x.degree)
                        || ((next_x.next != null) && (next_x.degree == next_x.next.degree))) {
                    // Case 1: x.degree != next_x.degree
                    // Case 2: x.degree == next_x.degree == next_x.next.degree
                    prev_x = x;
                    x = next_x;
                } else if (x.key.compareTo(next_x.key) <= 0) {
                    // Case 3: x.degree == next_x.degree != next_x.next.degree
                    //      && x.key    <= next_x.key
                    x.next = next_x.next;
                    link(next_x, x);
                } else {
                    // Case 4: x.degree == next_x.degree != next_x.next.degree
                    //      && x.key    >  next_x.key
                    if (prev_x == null) {
                        root = next_x;
                    } else {
                        prev_x.next = next_x;
                    }
                    link(x, next_x);
                    x = next_x;
                }
                next_x = x.next;
            }

            return root;
        }

        /**
         * 将二项堆other合并到当前堆中
         */
        public void union(BinomialHeap<T> other) {
            if (other != null && other.mRoot != null)
                mRoot = union(mRoot, other.mRoot);
        }

        /**
         * 新建key对应的节点，并将其插入到二项堆中。
         */
        public void insert(T key) {
            BinomialNode<T> node;
            // 禁止插入相同的键值
            if (contains(key) == true) {
                System.out.printf("insert failed: the key(%s) is existed already!\n", key);
                return;
            }

            node = new BinomialNode<>(key);
            if (node == null)
                return;

            mRoot = union(mRoot, node);
        }

        /**
         * 反转二项堆root，并返回反转后的根节点
         */
        private BinomialNode<T> reverse(BinomialNode<T> root) {
            BinomialNode<T> next;
            BinomialNode<T> tail = null;

            if (root == null)
                return root;

            root.parent = null;
            while (root.next != null) {
                next = root.next;
                root.next = tail;
                tail = root;
                root = next;
                root.parent = null;
            }
            root.next = tail;

            return root;
        }

        /**
         * 移除二项堆root中的最小节点，并返回删除节点后的二项树
         */
        private BinomialNode<T> extractMinimum(BinomialNode<T> root) {
            if (root == null)
                return root;

            // x是用来遍历的当前节点
            BinomialNode<T> x, prev_x;
            // y是最小节点
            BinomialNode<T> y, prev_y;

            prev_x = root;
            x = root.next;
            prev_y = null;
            y = root;
            // 找到最小节点
            while (x != null) {
                if (x.key.compareTo(y.key) < 0) {
                    y = x;
                    prev_y = prev_x;
                }
                prev_x = x;
                x = x.next;
            }

            // root的根节点就是最小根节点
            if (prev_y == null)
                root = root.next;
            else
                // root的根节点不是最小根节点
                prev_y.next = y.next;

            // 反转最小节点的左孩子，得到最小堆child；
            // 这样，就使得最小节点所在二项树的孩子们都脱离出来成为一棵独立的二项树(不包括最小节点)
            BinomialNode<T> child = reverse(y.child);
            // 将"删除最小节点的二项堆child"和"root"进行合并。
            root = union(root, child);

            // help GC
            y = null;
            return root;
        }

        public void extractMinimum() {
            mRoot = extractMinimum(mRoot);
        }

        /**
         * 减少关键字的值：将二项堆中的节点node的键值减小为key。
         */
        private void decreaseKey(BinomialNode<T> node, T key) {
            if (key.compareTo(node.key) >= 0 || contains(key) == true) {
                System.out.println("decrease failed: the new key(" + key + ") " +
                        "is existed already, or is no smaller than current key(" + node.key + ")");
                return;
            }
            node.key = key;

            BinomialNode<T> child, parent;
            child = node;
            parent = node.parent;
            while (parent != null && child.key.compareTo(parent.key) < 0) {
                // 交换parent和child的数据
                T tmp = parent.key;
                parent.key = child.key;
                child.key = tmp;

                child = parent;
                parent = child.parent;
            }
        }

        /**
         * 增加关键字的值：将二项堆中的节点node的键值增加为key。
         */
        private void increaseKey(BinomialNode<T> node, T key) {
            if (key.compareTo(node.key) <= 0 || contains(key) == true) {
                System.out.println("increase failed: the new key(" + key + ") is existed already, " +
                        "or is no greater than current key(" + node.key + ")");
                return;
            }
            node.key = key;

            BinomialNode<T> cur = node;
            BinomialNode<T> child = cur.child;
            while (child != null) {

                if (cur.key.compareTo(child.key) > 0) {
                    // 如果"当前节点" < "它的左孩子"，
                    // 则在"它的孩子中(左孩子 和 左孩子的兄弟)"中，找出最小的节点；
                    // 然后将"最小节点的值" 和 "当前节点的值"进行互换
                    BinomialNode<T> least = child;    // least是child和它的兄弟中的最小节点
                    while (child.next != null) {
                        if (least.key.compareTo(child.next.key) > 0)
                            least = child.next;
                        child = child.next;
                    }
                    // 交换最小节点和当前节点的值
                    T tmp = least.key;
                    least.key = cur.key;
                    cur.key = tmp;

                    // 交换数据之后，再对"原最小节点"进行调整，使它满足最小堆的性质：父节点 <= 子节点
                    cur = least;
                    child = cur.child;
                } else {
                    child = child.next;
                }
            }
        }

        /**
         * 更新二项堆的节点node的键值为key
         */
        private void updateKey(BinomialNode<T> node, T key) {
            if (node == null)
                return;

            int cmp = key.compareTo(node.key);
            if (cmp < 0)
                // key < node.key
                decreaseKey(node, key);
            else if (cmp > 0)
                // key > node.key
                increaseKey(node, key);
            else
                System.out.println("No need to update!!!");
        }

        /**
         * 将二项堆中键值oldkey更新为newkey
         */
        public void update(T oldkey, T newkey) {
            BinomialNode<T> node;

            node = search(mRoot, oldkey);
            if (node != null)
                updateKey(node, newkey);
        }

        /**
         * 查找：在二项堆中查找键值为key的节点
         */
        private BinomialNode<T> search(BinomialNode<T> root, T key) {
            BinomialNode<T> child;
            BinomialNode<T> parent = root;

            parent = root;
            while (parent != null) {
                if (parent.key.compareTo(key) == 0)
                    return parent;
                else {
                    if ((child = search(parent.child, key)) != null)
                        return child;
                    parent = parent.next;
                }
            }

            return null;
        }

        /**
         * 二项堆中是否包含键值key
         */
        public boolean contains(T key) {
            return search(mRoot, key) != null ? true : false;
        }

        /**
         * 删除节点：删除键值为key的节点
         */
        private BinomialNode<T> remove(BinomialNode<T> root, T key) {
            if (root == null)
                return root;

            BinomialNode<T> node;

            // 查找键值为key的节点
            if ((node = search(root, key)) == null)
                return root;

            // 将被删除的节点的数据数据上移到它所在的二项树的根节点
            BinomialNode<T> parent = node.parent;
            while (parent != null) {
                // 交换数据
                T tmp = node.key;
                node.key = parent.key;
                parent.key = tmp;

                // 下一个父节点
                node = parent;
                parent = node.parent;
            }

            // 找到node的前一个根节点(prev)
            BinomialNode<T> prev = null;
            BinomialNode<T> pos = root;
            while (pos != node) {
                prev = pos;
                pos = pos.next;
            }
            // 移除node节点
            if (prev != null)
                prev.next = node.next;
            else
                root = node.next;

            root = union(root, reverse(node.child));

            // help GC
            node = null;

            return root;
        }

        public void remove(T key) {
            mRoot = remove(mRoot, key);
        }

        /**
         * 打印"二项堆"
         *
         * 参数说明：
         *     node       -- 当前节点
         *     prev       -- 当前节点的前一个节点(父节点or兄弟节点)
         *     direction  --  1，表示当前节点是一个左孩子;
         *                    2，表示当前节点是一个兄弟节点。
         */
        private void print(BinomialNode<T> node, BinomialNode<T> prev, int direction) {
            while (node != null) {
                if (direction == 1)    // node是根节点
                    System.out.printf("\t%2d(%d) is %2d's child\n", node.key, node.degree, prev.key);
                else                // node是分支节点
                    System.out.printf("\t%2d(%d) is %2d's next\n", node.key, node.degree, prev.key);

                if (node.child != null)
                    print(node.child, node, 1);

                // 兄弟节点
                prev = node;
                node = node.next;
                direction = 2;
            }
        }

        public void print() {
            if (mRoot == null)
                return;

            BinomialNode<T> p = mRoot;
            System.out.printf("== 二项堆( ");
            while (p != null) {
                System.out.printf("B%d ", p.degree);
                p = p.next;
            }
            System.out.printf(")的详细信息：\n");

            int i = 0;
            p = mRoot;
            while (p != null) {
                i++;
                System.out.printf("%d. 二项树B%d: \n", i, p.degree);
                System.out.printf("\t%2d(%d) is root\n", p.key, p.degree);

                print(p.child, p, 1);
                p = p.next;
            }
            System.out.printf("\n");
        }
    }

    private static class TestBinomialHeap {

        private static final boolean DEBUG = false;

        // 共7个 = 1+2+4
        private static int a[] = {12, 7, 25, 15, 28, 33, 41};
        // 共13个 = 1+4+8
        private static int b[] = {18, 35, 20, 42, 9,
                31, 23, 6, 48, 11,
                24, 52, 13};

        // 验证"二项堆的插入操作"
        public static void testInsert() {
            BinomialHeap<Integer> ha = new BinomialHeap<Integer>();

            // 二项堆ha
            System.out.printf("== 二项堆(ha)中依次添加: ");
            for (int i = 0; i < a.length; i++) {
                System.out.printf("%d ", a[i]);
                ha.insert(a[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 二项堆(ha)的详细信息: \n");
            ha.print();
        }

        // 验证"二项堆的合并操作"
        public static void testUnion() {
            BinomialHeap<Integer> ha = new BinomialHeap<Integer>();
            BinomialHeap<Integer> hb = new BinomialHeap<Integer>();

            // 二项堆ha
            System.out.printf("== 二项堆(ha)中依次添加: ");
            for (int i = 0; i < a.length; i++) {
                System.out.printf("%d ", a[i]);
                ha.insert(a[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 二项堆(ha)的详细信息: \n");
            ha.print();

            // 二项堆hb
            System.out.printf("== 二项堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            // 打印二项堆hb
            System.out.printf("== 二项堆(hb)的详细信息: \n");
            hb.print();

            // 将"二项堆hb"合并到"二项堆ha"中。
            ha.union(hb);
            // 打印二项堆ha的详细信息
            System.out.printf("== 合并ha和hb后的详细信息:\n");
            ha.print();
        }

        // 验证"二项堆的删除操作"
        public static void testDelete() {
            BinomialHeap<Integer> hb = new BinomialHeap<Integer>();

            // 二项堆hb
            System.out.printf("== 二项堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            // 打印二项堆hb
            System.out.printf("== 二项堆(hb)的详细信息: \n");
            hb.print();

            // 将"二项堆hb"合并到"二项堆ha"中。
            hb.remove(20);
            System.out.printf("== 删除节点20后的详细信息: \n");
            hb.print();
        }

        // 验证"二项堆的更新(减少)操作"
        public static void testDecrease() {
            BinomialHeap<Integer> hb = new BinomialHeap<Integer>();

            // 二项堆hb
            System.out.printf("== 二项堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            // 打印二项堆hb
            System.out.printf("== 二项堆(hb)的详细信息: \n");
            hb.print();

            // 将节点20更新为2
            hb.update(20, 2);
            System.out.printf("== 更新节点20->2后的详细信息: \n");
            hb.print();
        }

        // 验证"二项堆的更新(减少)操作"
        public static void testIncrease() {
            BinomialHeap<Integer> hb = new BinomialHeap<Integer>();

            // 二项堆hb
            System.out.printf("== 二项堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            // 打印二项堆hb
            System.out.printf("== 二项堆(hb)的详细信息: \n");
            hb.print();

            // 将节点6更新为60
            hb.update(6, 60);
            System.out.printf("== 更新节点6->60后的详细信息: \n");
            hb.print();
        }

        public static void main(String[] args) {
            // 1. 验证"二项堆的插入操作"
            testInsert();
            // 2. 验证"二项堆的合并操作"
            //testUnion();
            // 3. 验证"二项堆的删除操作"
            //testDelete();
            // 4. 验证"二项堆的更新(减少)操作"
            //testDecrease();
            // 5. 验证"二项堆的更新(增加)操作"
            //testIncrease();
        }
    }

    /*
     * 斐波那契堆
     *
     * 斐波那契堆(Fibonacci heap)是一种可合并堆，可用于实现合并优先队列。
     * 它比二项堆具有更好的平摊分析性能，它的合并操作的时间复杂度是O(1)。
     *
     * 与二项堆一样，它也是由一组堆最小有序树组成，并且是一种可合并堆。
     *
     * 与二项堆不同的是，斐波那契堆中的树不一定是二项树；而且二项堆中的树是有序排列的，但是斐波那契堆中的树都是有根而无序的。
     *
     * --------------------------------------------------------------------------------------
     *
     *         6 ------------ 41 ---------- 28 ------- 7 ------------- 11 --------- 13
     *       / | \                           |        /  \            /  \
     *      /  |  \                          |       /    \          /    \
     *     9   18  \                        33      12    15        48    24
     *         |   20                                      |              |
     *         |   / \                                     |              |
     *         35 42 23                                   25              52
     *               |
     *               |
     *               31
     * --------------------------------------------------------------------------------------
     */

    /**
     * 斐波那契堆
     */
    public static class FibHeap {

        // 堆中节点的总数
        private int keyNum;
        // 最小节点(某个最小堆的根节点)
        private FibNode min;

        private class FibNode {
            // 关键字(键值)
            int key;
            // 度数
            int degree;
            // 左兄弟
            FibNode left;
            // 右兄弟
            FibNode right;
            // 第一个孩子节点
            FibNode child;
            // 父节点
            FibNode parent;
            // 是否被删除第一个孩子
            boolean marked;

            public FibNode(int key) {
                this.key = key;
                this.degree = 0;
                this.marked = false;
                this.left = this;
                this.right = this;
                this.parent = null;
                this.child = null;
            }
        }

        public FibHeap() {
            this.keyNum = 0;
            this.min = null;
        }

        /**
         * 将node从双链表移除
         */
        private void removeNode(FibNode node) {
            node.left.right = node.right;
            node.right.left = node.left;
        }

        /**
         * 将node堆结点加入root结点之前(循环链表中)
         *   a …… root
         *   a …… node …… root
         */
        private void addNode(FibNode node, FibNode root) {
            node.left = root.left;
            root.left.right = node;
            node.right = root;
            root.left = node;
        }

        /**
         * 将节点node插入到斐波那契堆中
         */
        private void insert(FibNode node) {
            if (keyNum == 0)
                min = node;
            else {
                addNode(node, min);
                if (node.key < min.key)
                    min = node;
            }

            keyNum++;
        }

        /**
         * 新建键值为key的节点，并将其插入到斐波那契堆中
         */
        public void insert(int key) {
            FibNode node;

            node = new FibNode(key);
            if (node == null)
                return;

            insert(node);
        }

        /**
         * 将双向链表b链接到双向链表a的后面
         */
        private void catList(FibNode a, FibNode b) {
            FibNode tmp;

            tmp = a.right;
            a.right = b.right;
            b.right.left = a;
            b.right = tmp;
            tmp.left = b;
        }

        /**
         * 将other合并到当前堆中
         */
        public void union(FibHeap other) {
            if (other == null)
                return;

            if ((this.min) == null) {
                // this无"最小节点"
                this.min = other.min;
                this.keyNum = other.keyNum;
                other = null;
            } else if ((other.min) == null) {
                // this有"最小节点" && other无"最小节点"
                other = null;
            } else {
                // this有"最小节点" && other有"最小节点"
                // 将"other中根链表"添加到"this"中
                catList(this.min, other.min);

                if (this.min.key > other.min.key)
                    this.min = other.min;
                this.keyNum += other.keyNum;
                other = null;
                ;
            }
        }

        /**
         * 将"堆的最小结点"从根链表中移除，
         * 这意味着"将最小节点所属的树"从堆中移除!
         */
        private FibNode extractMin() {
            FibNode p = min;

            if (p == p.right)
                min = null;
            else {
                removeNode(p);
                min = p.right;
            }
            p.left = p.right = p;

            return p;
        }

        /**
         * 将node链接到root根结点
         */
        private void link(FibNode node, FibNode root) {
            // 将node从双链表中移除
            removeNode(node);
            // 将node设为root的孩子
            if (root.child == null)
                root.child = node;
            else
                addNode(node, root.child);

            node.parent = root;
            root.degree++;
            node.marked = false;
        }

        /**
         * 合并斐波那契堆的根链表中左右相同度数的树
         */
        private void consolidate() {
            // 计算log2(keyNum)，floor意味着向上取整！
            // ex. log2(13) = 3，向上取整为4。
            int maxDegree = (int) Math.floor(Math.log(keyNum) / Math.log(2.0));
            int D = maxDegree + 1;
            FibNode[] cons = new FibNode[D + 1];

            for (int i = 0; i < D; i++)
                cons[i] = null;

            // 合并相同度的根节点，使每个度数的树唯一
            while (min != null) {
                // 取出堆中的最小树(最小节点所在的树)
                FibNode x = extractMin();
                // 获取最小树的度数
                int d = x.degree;
                // cons[d] != null，意味着有两棵树(x和y)的"度数"相同。
                while (cons[d] != null) {
                    // y是"与x的度数相同的树"
                    FibNode y = cons[d];
                    // 保证x的键值比y小
                    if (x.key > y.key) {
                        FibNode tmp = x;
                        x = y;
                        y = tmp;
                    }

                    // 将y链接到x中
                    link(y, x);
                    cons[d] = null;
                    d++;
                }
                cons[d] = x;
            }
            min = null;

            // 将cons中的结点重新加到根表中
            for (int i = 0; i < D; i++) {

                if (cons[i] != null) {
                    if (min == null)
                        min = cons[i];
                    else {
                        addNode(cons[i], min);
                        if ((cons[i]).key < min.key)
                            min = cons[i];
                    }
                }
            }
        }

        /**
         * 移除最小节点
         */
        public void removeMin() {
            if (min == null)
                return;

            FibNode m = min;
            // 将min每一个儿子(儿子和儿子的兄弟)都添加到"斐波那契堆的根链表"中
            while (m.child != null) {
                FibNode child = m.child;

                removeNode(child);
                if (child.right == child)
                    m.child = null;
                else
                    m.child = child.right;

                addNode(child, min);
                child.parent = null;
            }

            // 将m从根链表中移除
            removeNode(m);
            // 若m是堆中唯一节点，则设置堆的最小节点为null；
            // 否则，设置堆的最小节点为一个非空节点(m.right)，然后再进行调节。
            if (m.right == m)
                min = null;
            else {
                min = m.right;
                consolidate();
            }
            keyNum--;

            m = null;
        }

        /**
         * 获取斐波那契堆中最小键值；失败返回-1
         */
        public int minimum() {
            if (min == null)
                return -1;

            return min.key;
        }

        /**
         * 修改度数
         */
        private void renewDegree(FibNode parent, int degree) {
            parent.degree -= degree;
            if (parent.parent != null)
                renewDegree(parent.parent, degree);
        }

        /**
         * 将node从父节点parent的子链接中剥离出来，
         * 并使node成为"堆的根链表"中的一员。
         */
        private void cut(FibNode node, FibNode parent) {
            removeNode(node);
            renewDegree(parent, node.degree);
            // node没有兄弟
            if (node == node.right)
                parent.child = null;
            else
                parent.child = node.right;

            node.parent = null;
            node.left = node.right = node;
            node.marked = false;
            // 将"node所在树"添加到"根链表"中
            addNode(node, min);
        }

        /**
         * 对节点node进行"级联剪切"
         *
         * 级联剪切：如果减小后的结点破坏了最小堆性质，
         *     则把它切下来(即从所在双向链表中删除，并将
         *     其插入到由最小树根节点形成的双向链表中)，
         *     然后再从"被切节点的父节点"到所在树根节点递归执行级联剪枝
         */
        private void cascadingCut(FibNode node) {
            FibNode parent = node.parent;

            if (parent != null) {
                if (node.marked == false)
                    node.marked = true;
                else {
                    cut(node, parent);
                    cascadingCut(parent);
                }
            }
        }

        /**
         * 将斐波那契堆中节点node的值减少为key
         */
        private void decrease(FibNode node, int key) {
            if (min == null || node == null)
                return;

            if (key > node.key) {
                System.out.printf("decrease failed: the new key(%d) is no smaller than current key(%d)\n",
                        key, node.key);
                return;
            }

            FibNode parent = node.parent;
            node.key = key;
            if (parent != null && (node.key < parent.key)) {
                // 将node从父节点parent中剥离出来，并将node添加到根链表中
                cut(node, parent);
                cascadingCut(parent);
            }

            // 更新最小节点
            if (node.key < min.key)
                min = node;
        }

        /**
         * 将斐波那契堆中节点node的值增加为key
         */
        private void increase(FibNode node, int key) {
            if (min == null || node == null)
                return;

            if (key <= node.key) {
                System.out.printf("increase failed: the new key(%d) is no greater than current key(%d)\n", key, node.key);
                return;
            }

            // 将node每一个儿子(不包括孙子,重孙,...)都添加到"斐波那契堆的根链表"中
            while (node.child != null) {
                FibNode child = node.child;
                removeNode(child);               // 将child从node的子链表中删除
                if (child.right == child)
                    node.child = null;
                else
                    node.child = child.right;

                addNode(child, min);       // 将child添加到根链表中
                child.parent = null;
            }
            node.degree = 0;
            node.key = key;

            // 如果node不在根链表中，
            //     则将node从父节点parent的子链接中剥离出来，
            //     并使node成为"堆的根链表"中的一员，
            //     然后进行"级联剪切"
            // 否则，则判断是否需要更新堆的最小节点
            FibNode parent = node.parent;
            if (parent != null) {
                cut(node, parent);
                cascadingCut(parent);
            } else if (min == node) {
                FibNode right = node.right;
                while (right != node) {
                    if (node.key > right.key)
                        min = right;
                    right = right.right;
                }
            }
        }

        /**
         * 更新斐波那契堆的节点node的键值为key
         */
        private void update(FibNode node, int key) {
            if (key < node.key)
                decrease(node, key);
            else if (key > node.key)
                increase(node, key);
            else
                System.out.printf("No need to update!!!\n");
        }

        public void update(int oldkey, int newkey) {
            FibNode node;

            node = search(oldkey);
            if (node != null)
                update(node, newkey);
        }

        /**
         * 在最小堆root中查找键值为key的节点
         */
        private FibNode search(FibNode root, int key) {
            FibNode t = root;    // 临时节点
            FibNode p = null;    // 要查找的节点

            if (root == null)
                return root;

            do {
                if (t.key == key) {
                    p = t;
                    break;
                } else {
                    if ((p = search(t.child, key)) != null)
                        break;
                }
                t = t.right;
            } while (t != root);

            return p;
        }

        /**
         * 在斐波那契堆中查找键值为key的节点
         */
        private FibNode search(int key) {
            if (min == null)
                return null;

            return search(min, key);
        }

        /**
         * 在斐波那契堆中是否存在键值为key的节点。
         * 存在返回true，否则返回false。
         */
        public boolean contains(int key) {
            return search(key) != null ? true : false;
        }

        /**
         * 删除结点node
         */
        private void remove(FibNode node) {
            int m = min.key;
            decrease(node, m - 1);
            removeMin();
        }

        public void remove(int key) {
            if (min == null)
                return;

            FibNode node = search(key);
            if (node == null)
                return;

            remove(node);
        }

        /**
         * 销毁斐波那契堆
         */
        private void destroyNode(FibNode node) {
            if (node == null)
                return;

            FibNode start = node;
            do {
                destroyNode(node.child);
                // 销毁node，并将node指向下一个
                node = node.right;
                node.left = null;
            } while (node != start);
        }

        public void destroy() {
            destroyNode(min);
        }

        /**
         * 打印"斐波那契堆"
         *
         * 参数说明：
         *     node       -- 当前节点
         *     prev       -- 当前节点的前一个节点(父节点or兄弟节点)
         *     direction  --  1，表示当前节点是一个左孩子;
         *                    2，表示当前节点是一个兄弟节点。
         */
        private void print(FibNode node, FibNode prev, int direction) {
            FibNode start = node;

            if (node == null)
                return;
            do {
                if (direction == 1)
                    System.out.printf("%8d(%d) is %2d's child\n", node.key, node.degree, prev.key);
                else
                    System.out.printf("%8d(%d) is %2d's next\n", node.key, node.degree, prev.key);

                if (node.child != null)
                    print(node.child, node, 1);

                // 兄弟节点
                prev = node;
                node = node.right;
                direction = 2;
            } while (node != start);
        }

        public void print() {
            if (min == null)
                return;

            int i = 0;
            FibNode p = min;
            System.out.printf("== 斐波那契堆的详细信息: ==\n");
            do {
                i++;
                System.out.printf("%2d. %4d(%d) is root\n", i, p.key, p.degree);

                print(p.child, p, 1);
                p = p.right;
            } while (p != min);
            System.out.printf("\n");
        }
    }

    /**
     * 斐波那契堆
     */
    public static class TestFibHeap {

        private static final boolean DEBUG = false;

        // 共8个
        private static int a[] = {12, 7, 25, 15, 28, 33, 41, 1};
        // 共14个
        private static int b[] = {18, 35, 20, 42, 9,
                31, 23, 6, 48, 11,
                24, 52, 13, 2};

        // 验证"基本信息(斐波那契堆的结构)"
        public static void testBasic() {
            FibHeap hb = new FibHeap();

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            hb.print(); // 打印斐波那契堆hb
        }

        // 验证"插入操作"
        public static void testInsert() {
            FibHeap ha = new FibHeap();

            // 斐波那契堆ha
            System.out.printf("== 斐波那契堆(ha)中依次添加: ");
            for (int i = 0; i < a.length; i++) {
                System.out.printf("%d ", a[i]);
                ha.insert(a[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(ha)删除最小节点\n");
            ha.removeMin();
            ha.print(); // 打印斐波那契堆ha

            System.out.printf("== 插入50\n");
            ha.insert(50);
            ha.print();
        }

        // 验证"合并操作"
        public static void testUnion() {
            FibHeap ha = new FibHeap();
            FibHeap hb = new FibHeap();

            // 斐波那契堆ha
            System.out.printf("== 斐波那契堆(ha)中依次添加: ");
            for (int i = 0; i < a.length; i++) {
                System.out.printf("%d ", a[i]);
                ha.insert(a[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(ha)删除最小节点\n");
            ha.removeMin();
            ha.print(); // 打印斐波那契堆ha

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            hb.print(); // 打印斐波那契堆hb

            // 将"斐波那契堆hb"合并到"斐波那契堆ha"中。
            System.out.printf("== 合并ha和hb\n");
            ha.union(hb);
            ha.print();
        }

        // 验证"删除最小节点"
        public static void testRemoveMin() {
            FibHeap ha = new FibHeap();
            FibHeap hb = new FibHeap();

            // 斐波那契堆ha
            System.out.printf("== 斐波那契堆(ha)中依次添加: ");
            for (int i = 0; i < a.length; i++) {
                System.out.printf("%d ", a[i]);
                ha.insert(a[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(ha)删除最小节点\n");
            ha.removeMin();
            //ha.print(); // 打印斐波那契堆ha

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            //hb.print(); // 打印斐波那契堆hb

            // 将"斐波那契堆hb"合并到"斐波那契堆ha"中。
            System.out.printf("== 合并ha和hb\n");
            ha.union(hb);
            ha.print();

            System.out.printf("== 删除最小节点\n");
            ha.removeMin();
            ha.print();
        }

        // 验证"减小节点"
        public static void testDecrease() {
            FibHeap hb = new FibHeap();

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            hb.print(); // 打印斐波那契堆hb

            System.out.printf("== 将20减小为2\n");
            hb.update(20, 2);
            hb.print();
        }

        // 验证"增大节点"
        public static void testIncrease() {
            FibHeap hb = new FibHeap();

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            hb.print(); // 打印斐波那契堆hb

            System.out.printf("== 将20增加为60\n");
            hb.update(20, 60);
            hb.print();
        }

        // 验证"删除节点"
        public static void testDelete() {
            FibHeap hb = new FibHeap();

            // 斐波那契堆hb
            System.out.printf("== 斐波那契堆(hb)中依次添加: ");
            for (int i = 0; i < b.length; i++) {
                System.out.printf("%d ", b[i]);
                hb.insert(b[i]);
            }
            System.out.printf("\n");
            System.out.printf("== 斐波那契堆(hb)删除最小节点\n");
            hb.removeMin();
            hb.print(); // 打印斐波那契堆hb

            System.out.printf("== 删除节点20\n");
            hb.remove(20);
            hb.print();
        }

        public static void test() {
            // 验证"基本信息(斐波那契堆的结构)"
            testBasic();
            // 验证"插入操作"
            //testInsert();
            // 验证"合并操作"
            //testUnion();
            // 验证"删除最小节点"
            //testRemoveMin();
            // 验证"减小节点"
            //testDecrease();
            // 验证"增大节点"
            //testIncrease();
            // 验证"删除节点"
            //testDelete();
        }
    }

    public static void main(String[] args) {
//        Heap h = new Heap(10);
//        h.insert(10);
//        h.insert(30);
//        h.insert(20);
//        h.insert(18);
//        h.insert(12);
//        h.displayHeap();
//        h.remove();
//        h.displayHeap();

        // heapSort();

        // testMaxHeap();

        // testMinHeap();

        // testLeftistHeap();

//        TestBinomialHeap.testInsert();
//        TestBinomialHeap.testDecrease();
//        TestBinomialHeap.testDelete();
//        TestBinomialHeap.testIncrease();
//        TestBinomialHeap.testUnion();

        TestFibHeap.test();
    }
}
