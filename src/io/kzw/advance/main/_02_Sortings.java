package io.kzw.advance.main;

import java.util.Arrays;

/**
 * 常用经典排序算法总结Java实现
 *
 * @author kzw on 2018/09/15.
 */
public final class _02_Sortings {

    /*
     * 算法包括：冒泡排序、插入排序、希尔排序、快速排序、选择排序、归并排序、堆排序。
     *
     * 一般地，对数函数以幂（真数）为自变量，指数为因变量，底数为常量的函数。
     * 对数函数是6类基本初等函数之一。其中对数的定义：
     * 如果a的x方=N（a>0，且a≠1），那么数x叫做以a为底N的对数，记作x=logaN，读作以a为底N的对数，其中a叫做对数的底数，N叫做真数。
     * 一般地，函数y=loga的x方（a>0，且a≠1）叫做对数函数，也就是说以幂（真数）为自变量，指数为因变量，底数为常量的函数，叫对数函数。
     *
     * aX=N  X=logaN.
     *
     * O(NlogN)，底数为2.
     *
     * 在描述算法复杂度时,经常用到o(1), o(n), o(logn), o(nlogn)来表示对应算法的时间复杂度, 这里进行归纳一下它们代表的含义:
     * 这是算法的时空复杂度的表示。不仅仅用于表示时间复杂度，也用于表示空间复杂度。
     * O后面的括号中有一个函数，指明某个算法的耗时/耗空间与数据增长量之间的关系。其中的n代表输入数据的量。
     *
     * 比如时间复杂度为O(n)，就代表数据量增大几倍，耗时也增大几倍。比如常见的遍历算法。
     *
     * 再比如时间复杂度O(n^2)，就代表数据量增大n倍时，耗时增大n的平方倍，这是比线性更高的时间复杂度。比如冒泡排序，就是典型的O(n^2)的算法，
     * 对n个数排序，需要扫描n×n次。
     *
     * 再比如O(logn)，当数据增大n倍时，耗时增大logn倍（这里的log是以2为底的，比如，当数据增大256倍时，耗时只增大8倍，是比线性还要低的时间复杂度）。
     * 二分查找就是O(logn)的算法，每找一次排除一半的可能，256个数据中查找只要找8次就可以找到目标。2的8次方为256，所以log256 = 8.
     *
     * O(nlogn)同理，就是n乘以logn，当数据增大256倍时，耗时增大256*8=2048倍。这个复杂度高于线性低于平方。归并排序就是O(nlogn)的时间复杂度。
     *
     * O(1)就是最低的时空复杂度了，也就是耗时/耗空间与输入数据大小无关，无论输入数据增大多少倍，耗时/耗空间都不变。
     * 哈希算法就是典型的O(1)时间复杂度，无论数据规模多大，都可以在一次计算后找到目标（不考虑冲突的话）。
     *
     *
     * O(1) < O(logn) < O(n) < O(nlogn) < O(n²)< O(n³) < O(2ⁿ) < O(n!)
     *
     * ----------------------------------------------------------------------------------------
     * Algorithm    |    Average    |    Best    |    Worst   |    Extra Space    |     stable
     * ----------------------------------------------------------------------------------------
     * 冒泡排序      |    O(N²)      |    O(N)    |    O(N²)   |     O(1)          |      稳定
     * ----------------------------------------------------------------------------------------
     * 直接插入排序   |   O(N²)      |    O(N)    |    O(N²)    |    O(1)           |      稳定
     * ----------------------------------------------------------------------------------------
     * 折半插入排序   |   O(NlogN)   |    O(NlogN) |   O(N²)    |    O(1)           |      稳定
     * ----------------------------------------------------------------------------------------
     * 希尔排序      |   O(N^1.3)   |     O(N)    |   O(N²)    |    O(1)           |      不稳定
     * ----------------------------------------------------------------------------------------
     * 简单选择排序   |    O(N²)     |     O(N²)   |    O(N²)   |    O(1)           |      不稳定
     * ----------------------------------------------------------------------------------------
     * 快速排序      |   O(NlogN)   |    O(NlogN)  |   O(N²)   |    O(logN) - O(N²) |     不稳定
     * ----------------------------------------------------------------------------------------
     * 归并排序      |   O(NlogN)   |    O(NlogN)  |   O(NlogN) |   O(N)            |      稳定
     * ----------------------------------------------------------------------------------------
     * 堆排序        |   O(NlogN)   |    O(NlogN)  |   O(NlogN) |   O(1)            |     不稳定
     * ----------------------------------------------------------------------------------------
     */

    /**
     * 交换数组中两个数.
     *
     * @param array 数组
     * @param left  交换的左索引
     * @param right 交换的右索引
     */
    private static void swap(int array[], int left, int right) {
        int temp;
        temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    /**
     * 冒泡排序.
     * <p>
     * 平均O(N²) | 最好O(N) | 最坏O(N²) | 空间O(1) |  稳定
     *
     * @param array 待排序数组
     */
    private static void bubbleSort(int array[]) {
        /*
         * 6, 5, 4, 3, 2, 1
         *
         *    - i = 0, i < 5; j = 0, j < 5
         *    5, 6, 4, 3, 2, 1
         *    5, 4, 6, 3, 2, 1
         *    5, 4, 3, 6, 2, 1
         *    5, 4, 3, 2, 6, 1
         *    5, 4, 3, 2, 1, 6 - 6冒出来
         *
         *    - i = 1, i < 5; j = 0, j < 4
         *    4, 5, 3, 2, 1
         *    4, 3, 5, 2, 1
         *    4, 3, 2, 5, 1
         *    4, 3, 2, 1, 5 - 5冒出来
         *
         *    - i = 2, i < 5; j = 0, j < 3
         *    3, 4, 2, 1
         *    3, 2, 4, 1
         *    3, 2, 1, 4 - 4冒出来
         *
         *    - i = 3, i < 5; j = 0, j < 2
         *    2, 3, 1
         *    2, 1, 3 - 3冒出来
         *
         *    - i = 4, i < 5; j = 0, j < 1
         *    1, 2 - 2冒出来
         *
         */
        long start = System.nanoTime();
        int len = array.length;
        // 外层循环控制排序趟数
        for (int i = 0; i < len - 1; i++) {
            // 内层循环控制每一趟排序多少次
            for (int j = 0; j < len - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
        // n+(n-1)+(n-2)+(n-3)+……+1 = n²/2+n/2 = n²
        System.out.println("bubble sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 改进的冒泡排序.
     *
     * @param array 待排序数组
     */
    private static void bubbleSortBetter(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        // 外层循环控制排序趟数
        for (int i = 0; i < len; i++) {
            // 在一轮中是否发生了交换，没有则说明数组已经有序
            boolean isChanged = false;
            // 内层循环控制每一趟排序多少次
            for (int j = 0; j < len - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    isChanged = true;
                }
            }
            if (!isChanged) {
                // 已经有序，直接结束
                break;
            }
        }
        System.out.println("bubble sort better cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 选择排序.
     * <p>
     * 平均O(N²) | 最好O(N²) | 最坏O(N²) | 空间O(1) | 不稳定
     *
     * @param array 待排序数组
     */
    private static void selectSort(int array[]) {
        /*
         * 6, 5, 4, 3, 2, 1
         *
         *   - i = 0, i < 5; j = 1, j < 6
         *   1, 5, 4, 3, 2, 6 // 已排好 1
         *
         *   - i = 1, i < 5; j = 2, j < 6
         *   2, 4, 3, 5, 6 // 已排好 1， 2
         *
         *   - i = 2, i < 5; j = 3, j < 6
         *   3， 4， 5， 6 // 已排好 1，2，3
         *
         *   - i = 3, i < 5; j = 4, j < 6
         *   4, 5, 6 // 已排好 1, 2, 3, 4
         *
         *   - i = 4, i < 5; j = 5, j < 6
         *   5, 6 // 已排好 1, 2, 3, 4, 5, 6
         */
        long start = System.nanoTime();
        int len = array.length;
        // 外层循环控制排序趟数
        for (int i = 0; i < len - 1; i++) {
            // 这一趟中的最小索引，默认为i
            int minIndex = i;
            // 内层循环控制每一趟排序多少次
            for (int j = i + 1; j < len; j++) {
                // 找出后面最小的索引
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // 如果最小值位置改变了（不是i了），则交换，就是将最小值从后面换到前面
            if (minIndex != i) {
                swap(array, i, minIndex);
            }
        }
        System.out.println("select sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 插入排序.
     * <p>
     * 平均O(N²) | 最好O(N) | 最坏O(N²) | 空间O(1) | 稳定
     *
     * @param array 待排序数组
     */
    private static void insertSort(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        // 放于for循环外面是为了防止重复创建变量
        int temp;
        for (int i = 1; i < len; i++) {
            // 判断可以增加性能，如果这个索引处本来就比有序序列的最大值大，则不用插入
            if (array[i] < array[i - 1]) {
                // 赋给temp是为了防止索引i之前的元素向后移动覆盖了索引i的元素
                temp = array[i];
                int j = i - 1;
                // 向前遍历，将大于i位置元素的元素向后移
                for (; j >= 0 && array[j] > temp; j--) {
                    array[j + 1] = array[j];
                }
                // 找到i应该在的位置，将值放置此处，此时的j为比temp小的数的索引
                array[j + 1] = temp;
            }
        }
        System.out.println("insert sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 引入二分查找的插入排序.
     * <p>
     * 平均O(NlogN) | 最好O(NlogN) | 最坏O(N²) | 空间O(1) | 稳定
     *
     * @param array 待排序数组
     */
    private static void insertBinarySort(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        // 放于for循环外面是为了防止重复创建变量
        int temp;
        for (int i = 1; i < len; i++) {
            // 如果有逆序，则向前找到一个不大于该值的位置，插到它后面
            if (array[i] < array[i - 1]) {
                // 记录当前值
                temp = array[i];
                // 二分查找的low,high,mid位置标记
                int low = 0, mid, high = i - 1;
                while (low <= high) {
                    mid = (low + high) / 2;
                    // 如果当前值大于中间位置的值，则区间改为[mid+1, h]
                    if (temp > array[mid]) {
                        low = mid + 1;
                    } else {
                        // 如果当前值小于中间位置的值，则区间改为[low,mid-1]
                        high = mid - 1;
                    }
                }
                // 找到位置（就是low）后，从后向前移动数组，以腾出位置
                for (int j = i; j > low; j--) {
                    array[j] = array[j - 1];
                }
                array[low] = temp;
            }
        }
        System.out.println("insert binary sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 希尔排序，也是插入排序，但是调整了比较的步数.
     * <p>
     * 平均O(N^1.3) | 最好O(N) | 最坏O(N²) | 空间O(1) | 不稳定
     *
     * @param array 待排序数组
     */
    private static void shellSort(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        int temp;
        // gap每次减半，为什么为2，目前数学没有很好的解释，是大量测试出来的结果
        for (int gap = len / 2; gap > 0; gap /= 2) {
            // 比较的时候，每隔gap个数比较一次
            for (int i = 0; i < len; i += gap) {
                temp = array[i];
                int j = i;
                // 如果当前值小于前面对应的gap位置的数，则把前面的数赋值到当前位置
                for (; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                // 再把当前值赋值到前面的位置，本质就是交换了当前值和前面对应gap位置的数
                array[j] = temp;
            }
        }
        System.out.println("shell sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    /**
     * 快速排序，分治 + 递归.
     * <p>
     * 平均O(NlogN) | 最好O(NlogN) | 最坏O(N²) | 空间O(logN) - O(N²) | 不稳定
     *
     * @param array 待排序数组
     */
    private static void quickSort(int array[]) {
        long start = System.nanoTime();
        quickSort(array, 0, array.length - 1);
        System.out.println("quick sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    private static void quickSort(int array[], int left, int right) {
        // 切记，一定要左边小于右边时才分部处理
        if (left < right) {
            // 找到一个基准位置p
            int p = partation(array, left, right);
            // 对左半部分递归快排
            quickSort(array, left, p - 1);
            // 对右半部分递归快排
            quickSort(array, p + 1, right);
            // 这边只有分治，没有合并，归并 = 分治 + 合并
        }
    }

    /**
     * 分而治之，找到划分基准，并交换.
     *
     * @param array 待排序数组
     * @param left  起始位置
     * @param right 结束位置
     * @return 找到的基准位置
     */
    private static int partation(int array[], int left, int right) {
        // 以最左边的数为基准
        int pv = array[left];
        // 记录基准位置
        int p = left;
        // 往右遍历数组，如果小于基准，则与基准交换
        for (int i = p + 1; i <= right; i++) {
            if (array[i] < pv) {
                // 基准前移
                p++;
                // 如果是基准位置的下一个位置，则不用交换
                if (p != i) {
                    swap(array, p, i);
                }
            }
        }
        // 最后，将新的基准的值赋值给最左边
        array[left] = array[p];
        // 将基准赋值到中间，划分位置
        array[p] = pv;
        // 返回划分位置
        return p;
    }

    /**
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
    private static void mergeSort(int array[]) {
        long start = System.nanoTime();
        int len = array.length;
        // 空间复杂度为O(N)，需要构建一个同等大小的临时数组
        int temp[] = new int[len];
        mergeSort(array, temp, 0, len - 1);
        System.out.println("merge sort cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + Arrays.toString(array));
    }

    private static void mergeSort(int array[], int temp[], int left, int right) {
        // 当左边小于右边，分治
        if (left < right) {
            // 不断从中间分割，使得左边 !< 右边，即不能再分割了
            int mid = (left + right) / 2;
            // 递归分治，先分治到最小
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            // 再合并
            merge(array, temp, left, mid, right);
        }
    }

    /**
     * 合并数据.
     *
     * @param array 数组
     * @param temp  临时数组
     * @param left  left索引
     * @param mid   mid索引
     * @param right right索引
     */
    private static void merge(int array[], int temp[], int left, int mid, int right) {
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
                // 否则就拷贝pb的
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
            swap(array, 0, j);
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

    private static int[] newTestArray() {
        return new int[]{11, 16, 10, 7, 9, 8, 14, 18, 6, 5, 4, 3, 2, 1, 13, 12, 15, 17};
    }

    public static void main(String[] args) {
        bubbleSort(newTestArray());
        bubbleSortBetter(newTestArray());
        selectSort(newTestArray());
        insertSort(newTestArray());
        insertBinarySort(newTestArray());
        shellSort(newTestArray());
        quickSort(newTestArray());
        mergeSort(newTestArray());
        heapSort(newTestArray());
    }
}
