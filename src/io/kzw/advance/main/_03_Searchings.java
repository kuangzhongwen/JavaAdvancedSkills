package io.kzw.advance.main;

import java.util.Arrays;
import java.util.Random;

/**
 * java实现常见查找算法
 *
 * @author kzw on 2018/09/15.
 */
public final class _03_Searchings {

    /*
     * 查找按照操作方式分为两大种：静态查找和动态查找。
     *
     * 静态查找表：
     * 1. 查找某个特定的元素是否在查找表中。
     * 2. 检索某个特定的数据元索和各种属性。
     *
     * 按照我们大多数人的理解，查找，当然是在已经有的数据中找到我们需要的。
     * 静态查找就是在干这样的事情，不过，现实中还有存在这样的应用:查找的目的不仅仅只是查找,还可能边查找边作其它操作。
     *
     *
     * 动态查找表：
     * 在查找过程中同时插入查找表中不存在的数据元素，或者从查找表中删除已经存在的某个数据元素。
     * 显然动态查找表的操作就是两个:
     * 1. 查找时插入数据元素。
     * 2. 查找时删除数据元素。
     *
     * 为了提高查找的效率 ，我们需要专门为查找操作设置数据结构，这种面向查找操作的数据结构称为查找结构。
     * 从逻辑上来说，查找所基于的数据结构是集合，集合中的记录之间没有本质关系。
     * 可是要想获得较高的查找性能，我们就不得不改变数据元素之间的关系，在存储时可以将查找集合组织成表、树等结构。
     *
     * 例如，对于静态查找表来说，我们不妨应用线性表结构来组织数据，这样可以使用顺序查找算法，如果再对主关键字排序，
     * 则可以应用折半查找等技术进行高效的查找。
     *
     * 如果是需要动态查找，则会复杂一些，可以考虑二叉排序树的查找技术。
     * 另外，还可以用散列表结构来解决一些查找问题，这些技术都将在后面的讲解中说明。
     */

    /**
     * 1. 顺序表查找
     *
     * <p>
     * 顺序查找 (Sequential Search) 又叫线性查找，是最基本的查找技术，
     * 它的查找过程是: 从表中第一个(或最后一个)记录开始，逐个进行记录的关键字和给定值比较，若某个记录的关键字和给定值相等，
     * 则查找成功，找到所查的记录; 如果直到最后一个(或第一个)记录，其关键字和给定值比较都不等时，则表中没有所查的记录，查找不成功。
     *
     * @param array 待查找的数组
     * @param key   待查找的key
     */
    private static int linearSearch(int array[], int key) {
        long start = System.nanoTime();
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == key) {
                System.out.println("linear search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                        + " ,index = " + i);
                return i;
            }
        }
        System.out.println("linear search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + " ,index = -1");
        return -1;
    }

    /**
     * 2. 优化的线性查找
     *
     * <p>
     * 到这里并非足够完美，因为每次循环时都需要对i是否越界，即是否小于等于n作判断。
     * 事实上，还可以有更好一点的办法，设置一个哨兵，可以解决不需要每次让i与n作比较。
     * 看下面的改进后的顺序查找算法代码。
     *
     * 这种在查找方向的尽头放置"哨兵"免去了在查找过程中每一次比较后都要判断查找位置是否越界的小技巧，看似与原先差别不大，
     * 但在总数据较多时，效率提高很大，是非常好的编码技巧。当然，"哨兵"也不一定就一定要在数组开始，也可以在末端。
     *
     * 对于这种顺序查找算法来说，查找成功最好的情况就是在第一个位置就找到了，算法时间复杂度为O(1)，最坏的情况是在最后一位置才找到，
     * 需要n次比较，时间复杂度为O(n)，当查找不成功时，需要n+1次比较，时间复杂度为O(n)。
     * 我们之前推导过，关键字在任何一位置的概率是相同的，所以平均查找次数为(n+1)/2 ，所以最终时间复杂度还是O(n)。
     *
     * 很显然，顺序查找技术是有很大缺点的，n很大时，查找效率极为低下，不过优点也是有的，这个算法非常简单，
     * 对静态查找表的记录没有任何要求，在一些小型数据的查找时，是可以适用的。
     *
     * 另外，也正由于查找概率的不同，我们完全可以将容易查找到的记录放在前面，而不常用的记录放置在后面，效率就可以有大幅提高。
     *
     * @param array 待查找的数组
     * @param key   待查找的key
     */
    private static int linearSearchBetter(int array[], int key) {
        long start = System.nanoTime();
        int index = array.length - 1;
        while (index >= 0 && array[index] != key) {
            index--;
        }
        System.out.println("linear search better cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + " ,index = " + index);
        return index;
    }

    /**
     * 3. 折半查找 - 有序表查找
     *
     * <p>
     * 折半查找(Binary Search)技术，又称为二分查找。它的前提是线性表中的记录必须是关键码有序(通常从小到大有序) ，
     * 线性表必须采用顺序存储。折半查找的基本思想是: 在有序表中，取中间记录作为比较对象，若给定值与中间记录的关键字相等，
     * 则查找成功;若给定值小于中间记录的关键字，则在中间记录的左半区继续查找;若给定值大于中间记录的关键字，
     * 则在中间记录的右半区继续查找。不断重复上 述过程，直到查找成功，或所有查找区域无记录，查找失败为止。
     *
     * 该算法还是比较容易理解的，同时我们也能感觉到它的效率非常高。但到底高多少?关键在于此算法的时间复杂度分析。
     *
     * 首先，我们将数组的查找过程绘制成一棵二叉树，如果查找的关键字不是中间记录的话，折半查找等于是把静态有序
     * 查找表分成了两棵子树，即查找结果只需要找其中的一半数据记录即可，等于工作量少了一半，然后继续折半查找，效率当然是非常高了。
     *
     * 根据二叉树的性质4，具有n个结点的完全二叉树的深度为[log2n]+1。在这里尽管折半查找判定二叉树并不是完全二
     * 叉树，但同样相同的推导可以得出，最坏情况是查找到关键字或查找失败的次数为[log2n]+1，最好的情况是1次。
     * 因此最终我们折半算法的时间复杂度为O(logn)，它显然远远好于顺序查找的O(n)时间复杂度了。
     * 不过由于折主查找的前提条件是需要有序表顺序存储，对于静态查找表，一次排序后不再变化，这样的算法已经比较好了。
     * 但对于需要频繁执行插入或删除操作的数据集来说，维护有序的排序会带来不小的工作量，那就不建议使用。
     *
     * @param array 待查找的数组
     * @param key   待查找的key
     */
    private static int binarySearch(int array[], int key) {
        long start = System.nanoTime();
        int low, mid, high;
        low = 0;
        high = array.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (key > array[mid]) {
                low = mid + 1;
            } else if (key < array[mid]) {
                high = mid - 1;
            } else {
                System.out.println("binary search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                        + " ,index = " + mid);
                return mid;
            }
        }
        System.out.println("binary search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + " ,index = -1");
        return -1;
    }

    /**
     * 4. 插值查找 - 有序表查找
     *
     * <p>
     * 现在我们的新问题是，为什么一定要折半，而不是折四分之一或者折更多呢?
     * 打个比方，在英文词典里查"apple"，你下意识里翻开词典是翻前面的书页还是后面的书页呢?
     * 如果再让你查"zoo"，你又怎么查?很显然，这里你绝对不会是从中间开始查起，而是有一定目的的往前或往后翻。
     *
     * 同样的，比如要在取值范围0 - 10000之间100个元素从小到大均匀分布的数组中查找5，我们自然会考虑从数组下标较小的开始查找。
     * 看来，我们的折半查找，还是有改进空间的。折半计算mid的公式，我们略微等式变换后得到:
     *
     * mid = (low + high) / 2 = low + (high-low) / 2
     *
     * 也就是mid等于最低下标low加上最高下标high与low的差的一半。
     * 算法科学家们考虑的就是将这个 1/2 进行改进，通过类比，改进为下面的计算方案:
     *
     * mid = low + ((key - a[low]) / (a[high] - a[low])) (high - low)
     *
     * 这样就可以大大提高查找的效率。
     *
     * 插值查找(Interpolation Search)是根据要查找的关键字 key 与查找表中最大最小记录的关键字比较后的查找方法，
     * 其核心就在于插值的计算公式(key - a[low])/(a[high] - a[low])。应该说，从时间复杂度来看，它也是O(logn)，
     * 但对于表长较大，而关键字分布又比较均匀的查找表来说，插值查找算法的平均性能比折半查找要好得多 。
     *
     * 反之， 数组中如果分布类似{0，1，2，2000，2001，.......,999998, 999999}这种极端不均匀的数据，用插值查找未必是很合适的选择。
     *
     * @param array 待查找的数组
     * @param key   待查找的key
     */
    private static int interpolationSearch(int array[], int key) {
        long start = System.nanoTime();
        int low, mid, high;
        low = 0;
        high = array.length - 1;
        while (low <= high) {
            // 插值插值主要在于mid的计算公式
            mid = low + (high - low) * (key - array[low]) / (array[high] - array[low]);
            if (key > array[mid]) {
                low = mid + 1;
            } else if (key < array[mid]) {
                high = mid - 1;
            } else {
                System.out.println("interpolation search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                        + " ,index = " + mid);
                return mid;
            }
        }
        System.out.println("interpolation search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + " ,index = -1");
        return -1;
    }

    /**
     * 5. 斐波那契查找 - 有序表查找
     *
     * <p>
     * 斐波那契查找(Fibonacci Search)时，它是利用了黄金分割原理来实现的。
     *
     * 斐波那契查找算法的核心在于 :
     * 1 ) 当 key=a[mid] 时，查找就成功。
     * 2 ) 当 key<a[mid]时，新范围是第low个到第mid-1个，此时范围个数为f[k-1]-1个;
     * 3 ) 当 key>a[mid]时，新范围是第mid+l个到第high个，此时范围个数为f[k-2]-1个。
     *
     * 也就是说，如果要查找的记录在右侧，则左侧的数据都不用再判断了，
     * 不断反复进行下去，对处于当中的大部分数据，其工作效率要高一些。
     *
     * 所以尽管斐波那契查找的时间复杂也为O(logn)，但就平均性能来说，斐波那契查找要优于折半查找。
     * 可惜如果是最坏情况，比如这里key=l，那么始终都处于左侧长半区在查找，则查找效率要低于折半查找。
     *
     * 还有比较关键的一点，折半查找是进行加法与除法运算mid=(low+ high)/2,
     * 插值查找进行复杂的四则运算mid = low + ((key - a[low])/(a[high] - a[low]))(high - low),
     * 而斐波那契查找只是最简单加减法运算mid=low+f[k-l]-1,在海量数据的查找过程中，这种细微的差别可能会影响最终的查找效率。
     * 应该说，三种有序表的查找本质上是分隔点的选择不同，各有优劣，实际开发时可根据数据的特点综合考虑再做出选择。
     *
     * @param array 待查找的数组
     * @param key   待查找的key
     */
    private static int fibonaciSearch(int array[], int key) {
        long start = System.nanoTime();
        /**
         * 构造斐波那契数列
         * <p>
         * 当前数等于前面两位数之和.
         */
        final int f[] = new int[array.length];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        int low, mid, high, k;
        low = 0;
        high = array.length - 1;
        // 斐波那契数列下标
        k = 0;

        // 获取斐波那契分割值下标，当要查找的数组足够大时，以后f将越界？
        while (high > f[k] - 1)
            k++;
        // 利用Java工具类Arrays构造长度为f[k]的新数组并指向引用array
        array = Arrays.copyOf(array, f[k]);
        // 对新数组后面多余的元素赋值最大的元素
        for (int i = high + 1; i < f[k]; i++) {
            // 当key是是最大值时候，防止角标越界异常
            array[i] = array[high];
        }
        while (low <= high) {
            // 前半部分有f[k-1]个元素，由于下标从0开始
            // 减去 1 获取 分割位置元素的下标
            mid = low + f[k - 1] - 1;
            if (key < array[mid]) {
                high = mid - 1;
                // (全部元素) = (前半部分)+(后半部分)
                // f[k] = f[k-1] + f[k-2]
                // 因为前半部分有f[k-1]个元素， 则继续拆分f[k-1] = f[k-2] + f[k-3]成立
                // 即在f[k-1]个元素的前半部分f[k-2]中继续查找，所以k = k - 1,
                // 则下次循环mid = low + f[k - 1 - 1] - 1;
                k = k - 1;
            } else if (key > array[mid]) {
                low = mid + 1;
                // (全部元素) = (前半部分)+(后半部分)
                // f[k] = f[k-1] + f[k-2]
                // 因为后半部分有f[k-2]个元素， 则继续拆分f[k-2] = f[k-3] + f[k-4]成立
                // 即在f[k-2]个元素的前半部分f[k-3]继续查找，所以k = k - 2,
                // 则下次循环mid = low + f[k - 2 - 1] - 1;
                k = k - 2;
            } else {
                // 当条件成立的时候，则找到元素
                if (mid <= high) {
                    System.out.println("interpolation search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                            + " ,index = " + mid);
                    return mid;
                } else {
                    // 出现这种情况是查找到补充的元素
                    // 而补充的元素与high位置的元素一样
                    System.out.println("interpolation search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                            + " ,index = " + high);
                    return high;
                }
            }
        }
        System.out.println("interpolation search cost = " + (System.nanoTime() - start) / 1000 + "ms "
                + " ,index = -1");
        return -1;
    }

    /**
     * 6. 线性索引查找
     *
     * <p>
     * 我们前面讲的几种比较高效的查找方法都是基于有序的基础之上的，但事实上，很多数据集可能增长非常快，
     * 如果要保证记录全部是按照当中的某个关键字有序，其时间代价是非常高昂的，所以这种数据通常都是按先后顺序存储。
     *
     * 那么对于这样的查找表，我们如何能够快速查找到需要的数据呢?办法就是--索引。
     * 数据结构的最终目的是提高数据的处理速度，索引是为了加快查找速度而设计的一种数据结构。
     *
     * 索引就是把一个关键字与它对应的记录相关联的过程，一个索引由若干个索引项构成，
     * 每个索引项至少应包含关键字和其对应的记录在存储器中的位置等信息。
     *
     * 索引技术是组织大型数据库以及磁盘文件的一种重要技术。
     *
     * 索引按照结构可以分为 <p>线性索引、树形索引和多级索引</p>。
     *
     * 我们这里就只介绍线性索引技术。
     * 所谓线性索引就是将索引项集合组织为线性结构，也称为索引表。
     *
     * 我们重点介绍三种线性索引: <p>稠密索引、分块索引和倒排索引</p>。
     *
     * 1. 稠密索引
     *
     * 稠密索引是指在线性索引中，将数据集中的每个记录对应一个索引项。
     * 对于稠密索引这个索引表来说，索引项一定是按照关键码有序的排列。
     *
     * 索引项有序也就意味着，我们要查找关键字时，可以用到折半、插值、斐波那契等有序查找算法，大大提高了效率，
     *
     * 比如上图中，我要查找关键字是18的记录，如果直接从右侧的数据表中查找，那只能顺序查找，需要查找6次才可以查到结果。
     * 而如果是从左侧的索引表中查找，只需两次折半查找就可以得到18对应的指针，最终查找到结果。
     *
     * 这显然是稠密索引优点，但是如果数据集非常大，比如上亿，那也就意味着索引也得同样的数据集长度规模，
     * 对于内存有限的计算机来说，可能就需要反复去访问磁盘，查找性能反而大大下降了。
     *
     * 2. 分块索引
     *
     * 稠密索引因为索引项与数据集的记录个数相同，所以空间代价很大。
     * 为了减少索引项的个数，我们可以对数据集进行分块，使其分块有序，然后再对每一块建立一个索引项，从而减少索引项的个数。
     *
     * 分块有序，是把数据集的记录分成了若干块，并且这些块需要满足两个条件:
     * • 块内无序，即每一块内的记录不要求有序。当然 ，你如果能够让块内有序对查找来说更理想，
     *   不过这就要付出大量时间和空间的代价，因此通常我们不要求块内有序 。
     * • 块间有序，例如，要求第二块所有记录的关键字均要大于第一块中所有记录的关键字，
     *   第三块的所有记录的关键字均要大于第二块的所有记录关键字……因为只有块间有序，才有可能在查找时带来放率。
     *
     * 对于分块有序的数据集，将每块对应一个索引项，这种索引方法叫做分块索引。
     * 我们定义的分块索引的索引项结构分三个数据项 :
     * • 最大关键码，它存储每一块中的最大关键字，这样的好处就是可以使得在它之后的下一块中的最小关键字也能比这一块最大的关键字要大;
     * • 存储了块中的记录个数，以便于循环时使用;
     * • 用于指向块首数据元素的指针，便于开始对这一块中记录进行遍历。
     *
     * 在分块索引表中查找，就是分两步进行:
     * 1. 在分块索引表中查找要查关键字所在的块。由于分块索引表是块间有序的，因此很容易利用折半、插值等算法得到结果。
     * 例如，在上图的数据集中查找62，我们可以很快可以从左上角的索引表中由57<62<96得到62在第三个块中。
     * 2. 根据块首指针找到相应的块，并在块中顺序查找关键码。因为块中可以是无序的，因此只能顺序查找。
     * 我们再来分析一下分块索引的平均查找长度。设 n 个记录的数据集被平均分成 m 块，每个块中有 t 条记录，显然 n=m×t，
     * 或者说 m=n/t。再假设 Lb 为查找索引表的平均查找长度，因最好与最差的等概率原则，所以Lb平均长度为(m+1)/2。
     * Lw为块中查找记录的平均查找长度，同理可知它的平均查找长度为(t+1)/2。
     *
     * 这样分块索引查找的平均查找长度为:
     * ASLw = Lb + Lw = (m+1)/2 + (t+1)/2 = (m+t)/2 + 1 = (n/t + t)/2 + 1
     *
     * 注意上面这个式子的推导是为了让整个分块索引查找长度依赖 n 和 t 两个变量。
     * 从这里了我们也就得到，平均长度不仅仅取决于数据集的总记录数 n ，还和每一个块的记录个数 t 相关。
     * 最佳的情况就是分的块数m与块中的记录数 t相同，此时意味着n = m × t = t²，即ASLw = (n/t + t)/2 + 1 = √n + 1
     *
     * 可见，分块索引的效率比顺序查找的O(n)是高了不少，不过显然它与折半查找的O(logn)相比还有不小的差距。
     * 因此在确定所在块的过程中，由于块间有序，所以可以应用折半、插值等手段来提高效率。
     * 总的来说，分块索引在兼顾了对细分块内不需要有序的情况下，大大增加了整体查找的速度，所以普遍被用于数据库表查找等技术的应用当中。
     *
     * 3. 倒排索引
     *
     * 搜索引擎通常检索的场景是：给定几个关键词，找出包含关键词的记录。
     *
     * 我们来看样例，现在有两篇极短的英文"文章"--其实只能算是句子，我们暂认为它是文章，编号分别是1和2。
     * 1.Books and friends should be few but good.(读书如交友，应求少而精。)
     * 2.A good book is a good friend.(好书如挚友。)
     * 假设我们忽略掉如"books" 、"friends" 中的复数"s"以及如"A"这样的大小写差异。我们可以整理出这样一张单词表，
     * 如下表所示，并将单词做了排序，也就是表格显示了每个不同的单词分别出现在哪篇文章中，
     * 比如"good"在两篇文章中都有出现，而"is"只是在文章2中才有。
     *
     * 有了这样一张单词表，我们要搜索文章，就非常方便了。如果你在搜索框中填写"book"关键字。
     * 系统就先在这张单词表中有序查找"book"，找到后将它对应的文章编号1和2的文章地址(通常在搜索引擎中就是网页的标题和链接)返回，并告诉你，
     * 查找到两条记录，用时0.0001秒。由于单词表是有序的，查找效率很高，返回的又只是文章的编号，所以整体速度都非常快。
     *
     * 如果没有这张单词表，为了能证实所有的文章中有还是没有关键字"book"，则需要对每一篇文章每一个单词顺序查找。
     * 在文章数是海量的情况下，这样的做法只存在理论上可行性，现实中是没有人愿意使用的。
     *
     * 在这里这张单词表就是索引表，索引项的通用结构是:
     * • 次关键码，例如上面的"英文单词";
     * • 记录号表，例如上面的"文章编号"。
     * 其中记录号表存储具有相同次关键字的所有记录的记录号(可以是指向记录的指针或者是该记录的主关键字)。
     * 这样的索引方法就是倒排索引(inverted index)。
     *
     * 倒排索引源于实际应用中需要根据属性(或字段、次关键码)的值来查找记录。
     * 这种索引表中的每一项都包括一个属性值和具有该属性值的各记录的地址。
     * 由于不是由记录来确定属性值，而是由属性值来确定记录的位置，因而称为倒排索引。
     */

    /**
     * 7. Hash表 - 哈希查找
     *
     * 为了解决一些不容易排序，或者查找的对象。 比如图像，视频等等。
     *
     * 哈希查找是通过计算数据元素的存储地址进行查找的一种方法。
     *
     * 哈希查找的操作步骤：
     * ⑴用给定的哈希函数构造哈希表；
     * ⑵根据选择的冲突处理方法解决地址冲突；
     * ⑶在哈希表的基础上执行哈希查找。
     *
     * step1 取数据元素的关键字key，计算其哈希函数值。若该地址对应的存储空间还没有被占用，则将该元素存入；
     * 否则执行step2解决冲突。step2 根据选择的冲突处理方法，计算关键字key的下一个存储地址。若下一个存储地
     * 址仍被占用，则继续执行step2，直到找到能用的存储地址为止。
     *
     * 哈希查找步骤为：
     * 设哈希表为HST[0~M-1]，哈希函数取H（key），解决冲突的方法为R（x）；
     * Step1 对给定k值，计算哈希地址 Di=H（k）；若HST为空，则查找失败；
     * 若HST=k，则查找成功；否则，执行step2（处理冲突）。
     * Step2 重复计算处理冲突的下一个存储地址 Dk=R（Dk-1），直到HST[Dk]为
     * 空，或HST[Dk]=k为止。若HST[Dk]=K，则查找成功，否则查找失败。
     *
     * 哈希查找的本质是先将数据映射成它的哈希值。哈希查找的核心是构造一个哈希函数，它将原来直观、整洁的数据映射为看上去似乎是随机的一些整数。
     *
     * 哈希查找的产生有这样一种背景——有些数据本身是无法排序的(如图像)，有些数据是很难比较的(如图像)。
     * 如果数据本身是无法排序的，就不能对它们进行比较查找。如果数据是很难比较的，即使采用折半查找，
     * 要比较的次数也是非常多的。因此，哈希查找并不查找数据本身，而是先将数据映射为一个整数(它的哈希值)，
     * 并将哈希值相同的数据存放在同一个位置一即以哈希值为索引构造一个数组。
     *
     * 在哈希查找的过程中，只需先将要查找的数据映射为它的哈希值，然后查找具有这个哈希值的数据，
     * 这就大大减少了查找次数。如果构造哈希函数的参数经过精心设计，内存空间也足以存放哈希表，
     * 查找一个数据元素所需的比较次数基本上就接近于一次。
     *
     *
     * <p>解决冲突</p>
     * 影响哈希查找效率的一个重要因素是哈希函数本身。
     * 当两个不同的数据元素的哈希值相同时，就会发生冲突。
     *
     * 为减少发生冲突的可能性，哈希函数应该将数据尽可能分散地映射到哈希表的每一个表项中。解决冲突的方法有以下两种：
     * (1) 开放地址法
     * 如果两个数据元素的哈希值相同，则在哈希表中为后插入的数据元素另外选择一个表项。
     * 当程序查找哈希表时，如果没有在第一个对应的哈希表项中找到符合查找要求的数据元素，程序就会继续往后查找，
     * 直到找到一个符合查找要求的数据元素，或者遇到一个空的表项。
     * (2) 链地址法
     * 将哈希值相同的数据元素存放在一个链表中，在查找哈希表的过程中，当查找到这个链表时，必须采用线性查找方法。
     */

    /**
     * 8. 树表查找
     *
     * 最简单的树表查找算法——二叉树查找算法。
     *
     * 基本思想：二叉查找树是先对待查找的数据进行生成树，确保树的左分支的值小于右分支的值，然后在就行和每个节点的父节点比较大小，
     * 查找最适合的范围。这个算法的查找效率很高，但是如果使用这种查找方法要首先创建树。
     *
     * (1). 二叉查找树（BinarySearch Tree，也叫二叉搜索树，或称二叉排序树Binary Sort Tree）或者是一棵空树，
     * 或者是具有下列性质的二叉树：
     *
     * 　　1）若任意节点的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     *
     * 　　2）若任意节点的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     *
     * 　　3）任意节点的左、右子树也分别为二叉查找树。
     *
     * 二叉查找树性质：对二叉查找树进行中序遍历，即可得到有序的数列。
     *
     * (a)
     *         45
     *     |        |
     *    24        53
     *   |   |       |
     *  12   37     93
     *
     * 有关二叉查找树的查找、插入、删除等操作的详细讲解，请移步浅谈算法和数据结构: 七 二叉查找树。
     *
     * 复杂度分析：它和二分查找一样，插入和查找的时间复杂度均为O(logn)，但是在最坏的情况下仍然会有O(n)的时间复杂度。
     * 原因在于插入和删除元素的时候，树没有保持平衡（比如，我们查找上图（b）中的“93”，我们需要进行n次查找操作）。
     * 我们追求的是在最坏的情况下仍然有较好的时间复杂度，这就是平衡查找树设计的初衷。
     *
     * 基于二叉查找树进行优化，进而可以得到其他的树表查找算法，如平衡树、红黑树等高效算法。
     *
     * (2) 平衡查找树之2-3查找树（2-3 Tree）
     * 2-3查找树定义：和二叉树不一样，2-3树运行每个节点保存1个或者2个的值。
     * 对于普通的2节点(2-node)，它保存1个key和左右两个自己点。对应3节点(3-node)，保存两个Key，2-3查找树的定义如下：
     *
     * 　　1）要么为空，要么：
     *
     * 　　2）对于2节点，该节点保存一个key及对应value，以及两个指向左右节点的节点，左节点也是一个2-3节点，
     * 所有的值都比key要小，右节点也是一个2-3节点，所有的值比key要大。
     *
     * 　　3）对于3节点，该节点保存两个key及对应value，以及三个指向左中右的节点。左节点也是一个2-3节点，
     * 所有的值均比两个key中的最小的key还要小；中间节点也是一个2-3节点，中间节点的key值在两个跟节点key值之间；
     * 右节点也是一个2-3节点，节点的所有key值比两个key中的最大的key还要大。
     *
     * 2-3查找树的性质：
     *
     * 　　1）如果中序遍历2-3查找树，就可以得到排好序的序列；
     *
     * 　　2）在一个完全平衡的2-3查找树中，根节点到每一个为空节点的距离都相同。
     * （这也是平衡树中“平衡”一词的概念，根节点到叶节点的最长距离对应于查找算法的最坏情况，
     * 而平衡树中根节点到叶节点的距离都一样，最坏情况也具有对数复杂度。）
     *
     * (3) 平衡查找树之红黑树（Red-Black Tree）
     *
     * 　　2-3查找树能保证在插入元素之后能保持树的平衡状态，最坏情况下即所有的子节点都是2-node，树的高度为lgn，
     * 从而保证了最坏情况下的时间复杂度。但是2-3树实现起来比较复杂，于是就有了一种简单实现2-3树的数据结构，即红黑树（Red-Black Tree）。
     *
     * 　　基本思想：红黑树的思想就是对2-3查找树进行编码，尤其是对2-3查找树中的3-nodes节点添加额外的信息。
     * 红黑树中将节点之间的链接分为两种不同类型，红色链接，他用来链接两个2-nodes节点来表示一个3-nodes节点。
     * 黑色链接用来链接普通的2-3节点。特别的，使用红色链接的两个2-nodes来表示一个3-nodes节点，并且向左倾斜，
     * 即一个2-node是另一个2-node的左子节点。这种做法的好处是查找的时候不用做任何修改，和普通的二叉查找树相同。
     *
     * 红黑树的定义：
     *
     * 　　红黑树是一种具有红色和黑色链接的平衡查找树，同时满足：
     *
     * 红色节点向左倾斜
     * 一个节点不可能有两个红色链接
     * 整个树完全黑色平衡，即从根节点到所以叶子结点的路径上，黑色链接的个数都相同。
     * 　　下图可以看到红黑树其实是2-3树的另外一种表现形式：如果我们将红色的连线水平绘制，
     * 那么他链接的两个2-node节点就是2-3树中的一个3-node节点了。
     *
     * 红黑树的性质：整个树完全黑色平衡，即从根节点到所以叶子结点的路径上，黑色链接的个数都相同（2-3树的第2）性质，从根节点到叶子节点的距离都相等）。
     *
     * 　　复杂度分析：最坏的情况就是，红黑树中除了最左侧路径全部是由3-node节点组成，即红黑相间的路径长度是全黑路径长度的2倍。
     *
     *
     * 树表查找总结：
     *
     * 　　二叉查找树平均查找性能不错，为O(logn)，但是最坏情况会退化为O(n)。在二叉查找树的基础上进行优化，我们可以使用平衡查找树。
     * 平衡查找树中的2-3查找树，这种数据结构在插入之后能够进行自平衡操作，从而保证了树的高度在一定的范围内进而能够保证最坏情况下的时间复杂度。
     * 但是2-3查找树实现起来比较困难，红黑树是2-3树的一种简单高效的实现，他巧妙地使用颜色标记来替代2-3树中比较难处理的3-node节点问题。
     * 红黑树是一种比较高效的平衡查找树，应用非常广泛，很多编程语言的内部实现都或多或少的采用了红黑树。
     *
     * 　　除此之外，2-3查找树的另一个扩展——B/B+平衡树，在文件系统和数据库系统中有着广泛的应用。
     */

    // 二叉树 数据结构
    private static class BinTree {
        // 数据
        int data;
        // 左子数
        BinTree leftChild;
        // 右子数
        BinTree rightChild;
    }

    // 全局变量 存放查找到的关键字所在的父节点
    private static BinTree parentNode = new BinTree();

    /**
     * 二叉排序树
     *
     * @param bt     待查询二叉排序树
     * @param key    查找关键字
     * @param parent 指向bt的双亲，其初始调用值为null
     * @return 查找关键字key成功 返回true，并把树结点赋值给全局变量result，查找失败，返回false
     */
    private static boolean searchBST(BinTree bt, int key, BinTree parent) {
        // 树结点不存在，返回
        if (bt == null || bt.data == 0) {
            parentNode = parent;
            return false;
        } else if (key == bt.data) {
            // 查找成功，父结点
            parentNode = bt;
            return true;
        } else if (key < bt.data) {
            // 关键字小于根节点则查找左子树
            return searchBST(bt.leftChild, key, bt);
        } else {
            // 关键字大于根节点则查找右子树
            return searchBST(bt.rightChild, key, bt);
        }
    }

    private static void testSearchBST() {
        /**
         * 主要是表达查询，所以手动构造一棵二叉排序树
         *
         *                  62
         *       |                      |
         *      58                      88
         *   |                      |       |
         *  47                      73     99
         * |   |                         |
         * 35  51                       93
         *   |
         *   37
         */
        BinTree bt1 = new BinTree();
        bt1.data = 62;
        BinTree bt2 = new BinTree();
        bt1.leftChild = bt2;
        bt2.data = 58;
        BinTree bt3 = new BinTree();
        bt2.leftChild = bt3;
        bt3.data = 47;
        BinTree bt4 = new BinTree();
        bt3.leftChild = bt4;
        bt4.data = 35;
        BinTree bt5 = new BinTree();
        bt4.rightChild = bt5;
        bt5.data = 37;
        BinTree bt6 = new BinTree();
        bt3.rightChild = bt6;
        bt6.data = 51;
        BinTree bt7 = new BinTree();
        bt1.rightChild = bt7;
        bt7.data = 88;
        BinTree bt8 = new BinTree();
        bt7.leftChild = bt8;
        bt8.data = 73;
        BinTree bt9 = new BinTree();
        bt7.rightChild = bt9;
        bt9.data = 99;
        BinTree bt10 = new BinTree();
        bt9.leftChild = bt10;
        bt10.data = 93;

        boolean search = searchBST(bt1, 93, null);
        System.out.println(search ? "查找成功：" + parentNode.data : "查找失败!");
    }

    /**
     * 在二叉排序树中插入关键字key(如果不存在) - 动态查找
     *
     * @param bt  二叉排序树
     * @param key sdddd
     * @return 插入成功返回true 错误返回false
     */
    private static boolean insertBST(BinTree bt, int key) {
        BinTree s;
        if (!searchBST(bt, key, null)) {
            s = new BinTree();
            s.data = key;
            s.leftChild = s.rightChild = null;
            if (parentNode == null) {
                // 不存在，则表明是父节点，将s指向bt成为新的根节点
                bt = s;
            } else if (key < parentNode.data) {
                // 当key小于子根结点，则插入为左孩子
                parentNode.leftChild = s;
            } else {
                // 当key大于子根结点，则插入为右孩子
                parentNode.rightChild = s;
            }
            preOrderTraverse(bt);
            return true;
        } else {
            System.out.println("该节点已存在!");
        }
        return false;
    }

    /**
     * 中序遍历打印线索二叉树
     * @param t 二叉树
     */
    private static void preOrderTraverse(BinTree t) {
        if (t == null || t.data == 0) {
            return;
        }
        if (t.leftChild != null) {
            // 接着中序遍历左子树
            preOrderTraverse(t.leftChild);
        }
        if (t.data != 0) {
            // 显示当前结点数据
            System.out.print("[" + t.data + "]");
        }
        if (t.rightChild != null) {
            // 最后遍历右子树
            preOrderTraverse(t.rightChild);
        }
    }

    private static BinTree newTree = new BinTree();

    /**
     * 生成二叉排序树
     */
    private static boolean generateBST(int key) {
        if (!searchBST(newTree, key, null)) {
            BinTree s = new BinTree();
            s.data = key;
            s.leftChild = s.rightChild = null;
            if (parentNode == null) {
                // 不存在，则表明是父节点，将s指向bt成为新的根节点
                newTree = s;
            } else if (key < parentNode.data) {
                // 当key小于子根结点，则插入为左孩子
                parentNode.leftChild = s;
            } else {
                // 当key大于子根结点，则插入为右孩子
                parentNode.rightChild = s;
            }
            preOrderTraverse(newTree);
            return true;
        } else {
            System.out.println("该节点已存在!");
        }
        return false;
    }

    /**
     * 从二叉排序树中删除结点p，并重接它的左或右子树
     */
    private static boolean delete(BinTree bt) {
        BinTree q, s;
        if (bt.rightChild == null) {
            // 右子树为空则只需重接左子树
            bt = bt.leftChild;
        } else if (bt.leftChild == null) {
            // 左子树为空则只需重接右子树
            bt = bt.rightChild;
        } else {
            q = bt;
            s = bt.leftChild;
            while (s.rightChild != null) {
                // 转左，然后向右到尽头(找到待删结点前驱)
                q = s;
                s = s.rightChild;
            }
            // s指向被删除结点的直接前驱
            bt.data = s.data;
            if (q != bt) {
                // 重接q的右子树
                q.rightChild = s.leftChild;
            } else {
                // 重接q的左子树
                q.leftChild = s.leftChild;
            }
        }
        return true;
    }

    private static int[] newArray() {
        return new int[]{
                11, 16, 10, 7, 9, 8, 14, 18, 6, 5, 4, 3, 2, 1, 13, 12, 15, 17, 19, 20, 25, 33, 30, 23,
                99, 34, 65, 66, 42, 43, 38, 36, 31, 77, 55, 49, 48, 47, 62, 63, 41, 40, 89, 72, 64, 75,
                69, 68, 74, 70
        };
    }

    private static int[] newRandomLargeArray() {
        final int len = 100000;
        int[] data = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            data[i] = random.nextInt(len);
        }
        return data;
    }

    private static int[] newLinearLargeArray() {
        final int len = 100000;
        int[] data = new int[len];
        for (int i = 0; i < len; i++) {
            data[i] = i;
        }
        return data;
    }

    public static void main(String[] args) {
        // 线性查找
//        int[] array = newLinearLargeArray();
//        linearSearch(array, 50000);
//        linearSearchBetter(array, 50000);

        int[] array = newArray();
        Arrays.sort(array);

        // 二分查找
        binarySearch(array, 75);

        // 插值查找
        interpolationSearch(array, 75);

        // 斐波那契查找
        fibonaciSearch(array, 75);

        // 二叉树查找
        testSearchBST();
    }
}
