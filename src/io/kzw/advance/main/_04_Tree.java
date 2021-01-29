package io.kzw.advance.main;

import java.util.*;

/**
 * 树相关.
 *
 * @author kzw on 2018/09/17.
 */
public final class _04_Tree {

    // ======== from Java数据结构和算法（十）——二叉树 https://www.cnblogs.com/ysocean/p/8032642.html ======== //

    /*
     * 接下来我们将会介绍另外一种数据结构——树。
     *
     * 二叉树是树这种数据结构的一员，后面我们还会介绍红黑树，2-3-4树等数据结构。那么为什么要使用树？它有什么优点？
     * 前面我们介绍数组的数据结构，我们知道对于有序数组，查找很快，并介绍可以通过二分法查找，但是想要在有序数组中插入一个数据项，
     * 就必须先找到插入数据项的位置，然后将所有插入位置后面的数据项全部向后移动一位，来给新数据腾出空间，平均来讲要移动N/2次，
     * 这是很费时的。同理，删除数据也是。
     *
     * 然后我们介绍了另外一种数据结构——链表，链表的插入和删除很快，我们只需要改变一些引用值就行了，
     * 但是查找数据却很慢了，因为不管我们查找什么数据，都需要从链表的第一个数据项开始，遍历到找到所需数据项为止，
     * 这个查找也是平均需要比较N/2次。
     *
     * 那么我们就希望一种数据结构能同时具备数组查找快的优点以及链表插入和删除快的优点，于是 树 诞生了。
     */

    /*
     * 1、树
     *
     * 树（tree）是一种抽象数据类型（ADT），用来模拟具有树状结构性质的数据集合。
     * 它是由n（n>0）个有限节点通过连接它们的边组成一个具有层次关系的集合。
     * 把它叫做“树”是因为它看起来像一棵倒挂的树，也就是说它是根朝上，而叶朝下的。
     *
     *
     * ①、节点：上图的圆圈，比如A,B,C等都是表示节点。节点一般代表一些实体，在java面向对象编程中，节点一般代表对象。
     *
     * ②、边：连接节点的线称为边，边表示节点的关联关系。一般从一个节点到另一个节点的唯一方法就是沿着一条顺着有边的道路前进。
     * 在Java当中通常表示引用。
     *
     * 树有很多种，向上面的一个节点有多余两个的子节点的树，称为多路树，后面会讲解2-3-4树和外部存储都是多路树的例子。
     * 而每个节点最多只能有两个子节点的一种形式称为二叉树，这也是本篇博客讲解的重点。
     *
     *
     * 树的常用术语
     *
     * ①、路径：顺着节点的边从一个节点走到另一个节点，所经过的节点的顺序排列就称为“路径”。
     *
     * ②、根：树顶端的节点称为根。一棵树只有一个根，如果要把一个节点和边的集合称为树，
     *    那么从根到其他任何一个节点都必须有且只有一条路径。A是根节点。
     *
     * ③、父节点：若一个节点含有子节点，则这个节点称为其子节点的父节点；B是D的父节点。
     *
     * ④、子节点：一个节点含有的子树的根节点称为该节点的子节点；D是B的子节点。
     *
     * ⑤、兄弟节点：具有相同父节点的节点互称为兄弟节点；比如上图的D和E就互称为兄弟节点。
     *
     * ⑥、叶节点：没有子节点的节点称为叶节点，也叫叶子节点，比如上图的H、E、F、G都是叶子节点。
     *
     * ⑦、子树：每个节点都可以作为子树的根，它和它所有的子节点、子节点的子节点等都包含在子树中。
     *
     * ⑧、节点的层次：从根开始定义，根为第一层，根的子节点为第二层，以此类推。
     *
     * ⑨、深度：对于任意节点n,n的深度为从根到n的唯一路径长，根的深度为0；
     *
     * ⑩、高度：对于任意节点n,n的高度为从n到一片树叶的最长路径长，所有树叶的高度为0；
     *
     * 备注：
     * 首先要介绍树的层数：顶点的层数是从根到该顶点唯一通路的长度。
     *
     * 比如：
     *          10
     *       6          14
     *   4           12     16
     *
     * 高度 = 层数
     *
     * 这棵树的深度为2，高度为3（也可以理解为3层）
     * 14这个结点的深度为1，高度为2
     */

    /*
     * 2、二叉树
     *
     * 二叉树：树的每个节点最多只能有两个子节点
     *
     * 上图的第一幅图B节点有DEF三个子节点，就不是二叉树，称为多路树；
     * 而第二幅图每个节点最多只有两个节点，是二叉树，并且二叉树的子节点称为“左子节点”和“右子节点”。
     * 上图的D,E分别是B的左子节点和右子节点。
     *
     * 如果我们给二叉树加一个额外的条件，就可以得到一种被称作二叉搜索树(binary search tree)的特殊二叉树。
     *
     * 二叉查找树要求：若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     * 它的左、右子树也分别为二叉查找树。
     *
     * 二叉查找树:
     *         45
     *     |        |
     *    24        53
     *   |   |       |
     *  12   37     93
     */

    /**
     * 二叉查找树作为一种数据结构，那么它是如何工作的呢？它查找一个节点，插入一个新节点，以及删除一个节点，
     * 遍历树等工作效率如何，下面我们来一一介绍。
     * <p>
     * 二叉树的结点类
     */
    private static class Node {
        // 数据
        int data;
        // 左子树，注意，每个结点都可以有左右子树
        Node leftChild;
        // 右子树
        Node rightChild;

        void display() {
            System.out.println(data);
        }
    }

    /**
     * 二叉树的具体方法
     */
    private interface Tree {
        // 查找结点
        Node find(int key);

        // 插入新结点
        boolean insert(int key);

        // 删除结点
        boolean delete(int key);
    }

    private static Node root = null;

    /**
     * 3、二叉查找树查找节点
     *
     * <p>
     * 查找某个节点，我们必须从根节点开始遍历。
     *
     * ①、查找值比当前节点值大，则搜索右子树；
     *
     * ②、查找值等于当前节点值，停止搜索（终止条件）；
     *
     * ③、查找值小于当前节点值，则搜索左子树；
     *
     * <p>
     * 用变量current来保存当前查找的节点，参数key是要查找的值，刚开始查找将根节点赋值到current。
     * 接在在while循环中，将要查找的值和current保存的节点进行对比。如果key小于当前节点，则搜索当前节点的左子节点，
     * 如果大于，则搜索右子节点，如果等于，则直接返回节点信息。当整个树遍历完全，即current == null，
     * 那么说明没找到查找值，返回null。
     * <p>
     * 树的效率：查找节点的时间取决于这个节点所在的层数，每一层最多有2n-1个节点，总共N层共有2n-1个节点，
     * 那么时间复杂度为O(logn),底数为2。
     */
    private static Node find(int key) {
        // 先从根开始
        Node current = root;
        while (current != null) {
            // 当前值比查找值大，搜索左子树
            if (current.data > key) {
                current = current.leftChild;
            } else if (current.data < key) {
                // 当前值比查找值小，搜索右子树
                current = current.rightChild;
            } else {
                // 相等
                return current;
            }
        }
        return null;
    }

    /**
     * 4、插入节点
     *
     * 要插入节点，必须先找到插入的位置。
     * 与查找操作相似，由于二叉搜索树的特殊性，待插入的节点也需要从根节点开始进行比较，
     * 小于根节点则与根节点左子树比较，反之则与右子树比较，直到左子树为空或右子树为空，
     * 则插入到相应为空的位置，在比较的过程中要注意保存父节点的信息 及 待插入的位置是父节点的左子树还是右子树，
     * 才能插入到正确的位置。
     */
    private static boolean insert(int key) {
        Node newNode = new Node();
        newNode.data = key;
        // 当前树为空树，没有任何节点
        if (root == null) {
            root = newNode;
            return false;
        } else {
            // 从root结点开始
            Node current = root;
            // 查找父结点
            Node parentNode;
            while (current != null) {
                parentNode = current;
                // 当前值比插入值大，搜索左子节点
                if (current.data > key) {
                    current = current.leftChild;
                    // 左子节点为空，直接将新值插入到该节点
                    if (current == null) {
                        parentNode.leftChild = newNode;
                        return true;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null) {
                        // 右子节点为空，直接将新值插入到该节点
                        parentNode.rightChild = newNode;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 5、遍历树
     *
     * 遍历树是根据一种特定的顺序访问树的每一个节点。
     * 比较常用的有前序遍历，中序遍历和后序遍历。
     *
     * 而二叉查找树最常用的是中序遍历。
     *
     *
     * ①、前序遍历:根节点——》左子树——》右子树  [根左右]
     *
     * ②、中序遍历:左子树——》根节点——》右子树  [左根右]
     *
     * ③、后序遍历:左子树——》右子树——》根节点  [左右根]
     *
     * 如：
     *                   A
     *       B                         E
     *          C                           F
     *       D                         G
     *                             H        K
     *
     * 前序遍历：ABCDEFGHK (根左右)
     * 中序遍历：BDCAEHGKF (左根右)
     * 后序遍历：DCBHKGFEA (左右根）
     *
     *
     * 以中序遍历为例：
     *
     * 中序遍历的规则是【左根右】，我们从root节点A看起；
     *
     * 此时A是根节点，遍历A的左子树；
     * A的左子树存在，找到B，此时B看做根节点，遍历B的左子树；
     * B的左子树不存在，返回B，根据【左根右】的遍历规则，记录B，遍历B的右子树；
     * B的右子树存在，找到C，此时C看做根节点，遍历C的左子树；
     * C的左子树存在，找到D，由于D是叶子节点，无左子树，记录D，无右子树，返回C，根据【左根右】的遍历规则，记录C，遍历C的右子树；
     * C的右子树不存在，返回B，B的右子树遍历完，返回A；
     * 至此，A的左子树遍历完毕，根据【左根右】的遍历规则，记录A，遍历A的右子树；
     * A的右子树存在，找到E，此时E看做根节点，遍历E的左子树；
     * E的左子树不存在，返回E，根据【左根右】的遍历规则，记录E，遍历E的右子树；
     * E的右子树存在，找到F，此时F看做根节点，遍历F的左子树；
     * F的左子树存在，找到G，此时G看做根节点，遍历G的左子树；
     * G的左子树存在，找到H，由于H是叶子节点，无左子树，记录H，无右子树，返回G，根据【左根右】的遍历规则，记录G，遍历G的右子树；
     * G的右子树存在，找到K，由于K是叶子节点，无左子树，记录K，无右子树，返回G，根据【左根右】的遍历规则，记录F，遍历F的右子树；
     * F的右子树不存在，返回F，E的右子树遍历完毕，返回A；
     * 至此，A的右子树也遍历完毕；
     *
     * 最终我们得到上图的中序遍历为BDCAEHGKF，无非是按照遍历规则来的；
     */

    // 前序遍历，使用递归实现
    private static void preOrder(Node current) {
        if (current != null) {
            // 根
            System.out.print(current.data + " ");
            // 左
            preOrder(current.leftChild);
            // 右
            preOrder(current.rightChild);
        }
    }

    // 中序遍历，使用递归实现
    private static void infixOrder(Node current) {
        if (current != null) {
            // 左
            preOrder(current.leftChild);
            // 根
            System.out.print(current.data + " ");
            // 右
            preOrder(current.rightChild);
        }
    }

    // 后序遍历，使用递归实现
    private static void postOrder(Node current) {
        if (current != null) {
            // 左
            preOrder(current.leftChild);
            // 右
            preOrder(current.rightChild);
            // 根
            System.out.print(current.data + " ");
        }
    }

    /**
     * 6、查找最大值和最小值
     *
     * 这没什么好说的，要找最小值，先找根的左节点，然后一直找这个左节点的左节点，直到找到没有左节点的节点，
     * 那么这个节点就是最小值。同理要找最大值，一直找根节点的右节点，直到没有右节点，则就是最大值。
     */

    // 找最大值
    private static Node findMax() {
        Node current = root;
        Node maxNode = current;
        while (current != null) {
            maxNode = current;
            current = current.rightChild;
        }
        return maxNode;
    }

    // 找最小值
    private static Node findMin() {
        Node current = root;
        Node minNode = current;
        while (current != null) {
            minNode = current;
            current = current.leftChild;
        }
        return minNode;
    }

    /**
     * 7、删除节点
     *
     * 删除节点是二叉查找树中最复杂的操作，删除的节点有三种情况，前两种比较简单，但是第三种却很复杂。
     *
     * 1、该节点是叶节点（没有子节点）
     *
     * 2、该节点有一个子节点
     *
     * 3、该节点有两个子节点
     *
     * 下面我们分别对这三种情况进行讲解。
     */

    /**
     * ①、删除没有子节点的节点
     *
     * 要删除叶节点，只需要改变该节点的父节点引用该节点的值，即将其引用改为 null 即可。
     * 要删除的节点依然存在，但是它已经不是树的一部分了，由于Java语言的垃圾回收机制，我们不需要非得把节点本身删掉，
     * 一旦Java意识到程序不在与该节点有关联，就会自动把它清理出存储器。
     *
     * 删除节点，我们要先找到该节点，并记录该节点的父节点。
     * 在检查该节点是否有子节点。如果没有子节点，接着检查其是否是根节点，如果是根节点，只需要将其设置为null即可。
     * 如果不是根节点，是叶节点，那么断开父节点和其的关系即可。
     */
    private static boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = false;
        // 查找删除值，找不到直接返回false
        while (current.data != key) {
            parent = current;
            if (current.data > key) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {
                return false;
            }
        }
        // 如果当前节点没有子节点
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.leftChild = null;
            } else {
                parent.rightChild = null;
            }
            return true;

            // 当前节点有一个子节点，右子节点
        } else if (current.leftChild == null && current.rightChild != null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
            return true;
            // 当前节点有一个子节点，左子节点
        } else if (current.leftChild != null && current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
            return true;
        } else {
            // 当前节点存在两个子节点
            Node successor = getSuccessor(current);
            if (current == root) {
                successor = root;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }
        return false;
    }

    private static Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        // 后继节点不是删除节点的右子节点，将后继节点替换删除节点
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    /**
     * 8、二叉树的效率
     *
     * 从前面的大部分对树的操作来看，都需要从根节点到下一层一层的查找。
     *
     * 一颗满树，每层节点数大概为2n-1，那么最底层的节点个数比树的其它节点数多1，
     * 因此，查找、插入或删除节点的操作大约有一半都需要找到底层的节点，另外四分之一的节点在倒数第二层，依次类推。
     *
     * 总共N层共有2n-1个节点，那么时间复杂度为O(logn),底数为2。
     *
     * 在有1000000 个数据项的无序数组和链表中，查找数据项平均会比较500000 次，但是在有1000000个节点的二叉树中，只需要20次或更少的比较即可。
     *
     * 有序数组可以很快的找到数据项，但是插入数据项的平均需要移动 500000 次数据项，在 1000000 个节点的二叉树中插入数据项需要20次或更少比较，
     * 在加上很短的时间来连接数据项。
     *
     * 同样，从 1000000 个数据项的数组中删除一个数据项平均需要移动 500000 个数据项，而在 1000000 个节点的二叉树中删除节点只需要20次
     * 或更少的次数来找到他，然后在花一点时间来找到它的后继节点，一点时间来断开节点以及连接后继节点。
     *
     * 所以，树对所有常用数据结构的操作都有很高的效率。
     *
     * 遍历可能不如其他操作快，但是在大型数据库中，遍历是很少使用的操作，它更常用于程序中的辅助算法来解析算术或其它表达式。
     */

    /**
     * 9、用数组表示树
     *
     * 用数组表示树，那么节点是存在数组中的，节点在数组中的位置对应于它在树中的位置。
     * 下标为 0 的节点是根，下标为 1 的节点是根的左子节点，以此类推，按照从左到右的顺序存储树的每一层。
     *
     * 树中的每个位置，无论是否存在节点，都对应于数组中的一个位置，树中没有节点的在数组中用0或者null表示。
     *
     * 假设节点的索引值为index，那么节点的左子节点是 2*index+1，节点的右子节点是 2*index+2，它的父节点是 （index-1）/2。
     *
     * 在大多数情况下，使用数组表示树效率是很低的，不满的节点和删除掉的节点都会在数组中留下洞，浪费存储空间。
     * 更坏的是，删除节点如果要移动子树的话，子树中的每个节点都要移到数组中新的位置，这是很费时的。
     *
     * 不过如果不允许删除操作，数组表示可能会很有用，尤其是因为某种原因要动态的为每个字节分配空间非常耗时。
     */

    /**
     * 10、哈夫曼(Huffman)编码
     *
     * 我们知道计算机里每个字符在没有压缩的文本文件中由一个字节（比如ASCII码）或两个字节（比如Unicode,这个编码在各种语言中通用）表示，
     * 在这些方案中，每个字符需要相同的位数。
     *
     * 有很多压缩数据的方法，就是减少表示最常用字符的位数量，比如英语中，E是最常用的字母，我们可以只用两位01来表示，
     * 2位有四种组合：00、01、10、11，那么我们可以用这四种组合表示四种常用的字符吗？
     *
     * 答案是不可以的，因为在编码序列中是没有空格或其他特殊字符存在的，全都是有0和1构成的序列，比如E用01来表示，
     * X用01011000表示，那么在解码的时候就弄不清楚01是表示E还是表示X的起始部分，所以在编码的时候就定下了一个规则：
     * 每个代码都不能是其它代码的前缀。
     *
     * ①、哈夫曼编码　
     *
     * 二叉树中有一种特别的树——哈夫曼树（最优二叉树），其通过某种规则（权值）来构造出一哈夫曼二叉树，在这个二叉树中，
     * 只有叶子节点才是有效的数据节点（很重要），其他的非叶子节点是为了构造出哈夫曼而引入的！
     *
     * 哈夫曼编码是一个通过哈夫曼树进行的一种编码，一般情况下，以字符：‘0’与‘1’表示。编码的实现过程很简单，只要实现哈夫曼树，
     * 通过遍历哈夫曼树，规定向左子树遍历一个节点编码为“0”，向右遍历一个节点编码为“1”，结束条件就是遍历到叶子节点！
     * 因为上面说过：哈夫曼树叶子节点才是有效数据节点！
     *
     * 我们用01表示S，用00表示空格后，就不能用01和11表示某个字符了，因为它们是其它字符的前缀。
     * 在看三位的组合，分别有000,001,010,100,101,110和111，A是010，I是110，为什么没有其它三位的组合了呢？
     * 因为已知是不能用01和11开始的组合了，那么就减少了四种选择，同时011用于U和换行符的开始，111用于E和Y的开始，
     * 这样就只剩下2个三位的组合了，同理可以理解为什么只有三个四位的代码可用。
     *
     * 所以对于消息：SUSIE SAYS IT IS EASY
     *
     * 哈夫曼编码为：100111110110111100100101110100011001100011010001111010101110
     *
     * ②、哈夫曼解码
     *
     * 如果收到上面的一串哈夫曼编码，怎么解码呢？消息中出现的字符在哈夫曼树中是叶节点，也就是没有子节点，
     * 如下图：它们在消息中出现的频率越高，在树中的位置就越高，每个圆圈外面的数字就是频率，非叶节点外面的数字是它子节点数字的和。
     *
     * 每个字符都从根开始，如果遇到0，就向左走到下一个节点，如果遇到1，就向右。
     * 比如字符A是010，那么先向左，再向右，再向左，就找到了A，其它的依次类推。
     */

    // ======== from 满二叉树和完全二叉树的区别 https://blog.csdn.net/wuxiaobingandbob/article/details/46500257 ======== //

    /**
     * 完全二叉树（Complete Binary Tree）：
     *
     * 在最后一层，并不是所有节点都有两个子节点，这类二叉树又称为完全二叉树（Complete Binary Tree），如下：
     *
     * ========================================================================================
     *
     *                                      1
     *                        2                             3
     *                    4      5                       6     7
     *                 8
     *
     * ========================================================================================
     *
     *
     *
     * 满二叉树（Full Binarry Tree)：
     *
     * 所有的节点都有两个子节点，这类二叉树称作满二叉树（Full Binarry Tree)，如下：
     *
     * ========================================================================================
     *
     *                                      1
     *                        2                             3
     *                    4      5                       6     7
     *                 8    9   10  11                 12 13  14 15
     *
     * ========================================================================================
     */

    // ======== from 数据结构与算法系列 目录 https://blog.csdn.net/l_215851356/article/details/77659462 ======== //

    /**
     * 1. AVL树（平衡二叉树）
     *
     * AVL树的介绍:
     *
     * AVL树是根据它的发明者G.M. Adelson-Velsky和E.M. Landis命名的。
     * 它是最先发明的自平衡二叉查找树，也被称为高度平衡树。相比于"二叉查找树"，
     * 它的特点是：AVL树中任何节点的两个子树的高度最大差别为1。
     *
     * 如：
     * ========================================================================================
     * AVL树：
     *                                         5
     *                                  2      |       8
     *                              1      4   |    7
     *                                   3
     *
     * 可以看出，任何结点的两个子树的高度最大差别为1
     *
     *
     * 非AVL树：
     *                                          7
     *                                   2      |      8
     *                                1     4   |
     *                                    3   5 |
     *
     * 可以看出这不是AVL树，因为7的两颗子树的高度相差为2(以2为根节点的树的高度是3，而以8为根节点的树的高度是1)。
     * ========================================================================================
     *
     * AVL树的查找、插入和删除在平均和最坏情况下都是O(logn)。
     * 如果在AVL树中插入或删除节点后，使得高度之差大于1。此时，AVL树的平衡状态就被破坏，它就不再是一棵二叉树；
     * 为了让它重新维持在一个平衡状态，就需要对其进行旋转处理。
     * 学AVL树，重点的地方也就是它的旋转算法；在后文的介绍中，再来对它进行详细介绍。
     */

    /**
     * 2. AVL树的Java实现
     */
    // 节点定义
    private static class AVLTree<T extends Comparable<T>> {
        // 根结点
        private AVLTreeNode<T> mRoot;

        // AVL树的节点(内部类)
        static class AVLTreeNode<T extends Comparable<T>> {
            // 关键字
            T key;
            // 高度
            int height;
            // 左孩子
            AVLTreeNode<T> left;
            // 右孩子
            AVLTreeNode<T> right;

            public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
                this.key = key;
                this.left = left;
                this.right = right;
                this.height = 0;
            }
        }

        /**
         * 获取树的高度
         */
        int height(AVLTreeNode<T> tree) {
            if (tree != null)
                return tree.height;

            return 0;
        }

        int height() {
            return height(mRoot);
        }

        /**
         * 比较两个值的大小
         */
        int max(int a, int b) {
            return a > b ? a : b;
        }

        /*
         * 旋转
         *
         * 如果在AVL树中进行插入或删除节点后，可能导致AVL树失去平衡。这种失去平衡的可以概括为4种姿态：
         * LL(左左)，LR(左右)，RR(右右)和RL(右左)。下面给出它们的示意图：
         * https://images0.cnblogs.com/i/497634/201403/281624280475098.jpg
         *
         * 上图中的4棵树都是"失去平衡的AVL树"，从左往右的情况依次是：LL、LR、RL、RR。除了上面的情况之外，
         * 还有其它的失去平衡的AVL树，如下图：
         * https://images0.cnblogs.com/i/497634/201403/281625317193879.jpg
         *
         * 上面的两张图都是为了便于理解，而列举的关于"失去平衡的AVL树"的例子。
         * 总的来说，AVL树失去平衡时的情况一定是LL、LR、RL、RR这4种之一，它们都由各自的定义：
         *
         * (1) LL：LeftLeft，也称为"左左"。插入或删除一个节点后，根节点的左子树的左子树还有非空子节点，
         *     导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。
         *     例如，在上面LL情况中，由于"根节点(8)的左子树(4)的左子树(2)还有非空子节点"，
         *     而"根节点(8)的右子树(12)没有子节点"；导致"根节点(8)的左子树(4)高度"比"根节点(8)的右子树(12)"高2。
         *
         * (2) LR：LeftRight，也称为"左右"。插入或删除一个节点后，根节点的左子树的右子树还有非空子节点，
         *     导致"根的左子树的高度"比"根的右子树的高度"大2，导致AVL树失去了平衡。
         *     例如，在上面LR情况中，由于"根节点(8)的左子树(4)的左子树(6)还有非空子节点"，
         *     而"根节点(8)的右子树(12)没有子节点"；导致"根节点(8)的左子树(4)高度"比"根节点(8)的右子树(12)"高2。
         *
         * (3) RL：RightLeft，称为"右左"。插入或删除一个节点后，根节点的右子树的左子树还有非空子节点，
         *     导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。
         *     例如，在上面RL情况中，由于"根节点(8)的右子树(12)的左子树(10)还有非空子节点"，
         *     而"根节点(8)的左子树(4)没有子节点"；导致"根节点(8)的右子树(12)高度"比"根节点(8)的左子树(4)"高2。
         *
         *
         * (4) RR：RightRight，称为"右右"。插入或删除一个节点后，根节点的右子树的右子树还有非空子节点，
         *     导致"根的右子树的高度"比"根的左子树的高度"大2，导致AVL树失去了平衡。
         *     例如，在上面RR情况中，由于"根节点(8)的右子树(12)的右子树(14)还有非空子节点"，
         *     而"根节点(8)的左子树(4)没有子节点"；导致"根节点(8)的右子树(12)高度"比"根节点(8)的左子树(4)"高2。
         *
         *  如果在AVL树中进行插入或删除节点后，可能导致AVL树失去平衡。AVL失去平衡之后，可以通过旋转使其恢复平衡，
         *  下面分别介绍"LL(左左)，LR(左右)，RR(右右)和RL(右左)"这4种情况对应的旋转方法。
         */

        /**
         * LL的旋转
         *
         * LL失去平衡的情况，可以通过一次旋转让AVL树恢复平衡。
         *
         * https://images0.cnblogs.com/i/497634/201403/281626153129361.jpg
         *
         * 图中左边是旋转之前的树，右边是旋转之后的树。从中可以发现，旋转之后的树又变成了AVL树，而且该旋转只需要一次即可完成。
         * 对于LL旋转，你可以这样理解为：LL旋转是围绕"失去平衡的AVL根节点"进行的，也就是节点k2；而且由于是LL情况，即左左情况，
         * 就用手抓着"左孩子，即k1"使劲摇。将k1变成根节点，k2变成k1的右子树，"k1的右子树"变成"k2的左子树"。
         *
         * LL：左左对应的情况(左单旋转)。
         *
         * 返回值：旋转后的根节点
         */
        AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
            AVLTreeNode<T> k1;
            k1 = k2.left;
            k2.left = k1.right;
            k1.right = k2;

            k2.height = max( height(k2.left), height(k2.right)) + 1;
            k1.height = max( height(k1.left), k2.height) + 1;

            return k1;
        }

        /**
         * RR的旋转
         *
         * https://images0.cnblogs.com/i/497634/201403/281626410316969.jpg
         *
         * 理解了LL之后，RR就相当容易理解了。RR是与LL对称的情况！RR恢复平衡的旋转方法如下：
         * 图中左边是旋转之前的树，右边是旋转之后的树。RR旋转也只需要一次即可完成。
         *
         * RR：右右对应的情况(右单旋转)。
         *
         * 返回值：旋转后的根节点
         */
        AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
            AVLTreeNode<T> k2;

            k2 = k1.right;
            k1.right = k2.left;
            k2.left = k1;

            k1.height = max( height(k1.left), height(k1.right)) + 1;
            k2.height = max( height(k2.right), k1.height) + 1;

            return k2;
        }

        /**
         * LR的旋转
         *
         * LR失去平衡的情况，需要经过两次旋转才能让AVL树恢复平衡。如下图：
         * https://images0.cnblogs.com/i/497634/201403/281627088127150.jpg
         *
         * 第一次旋转是围绕"k1"进行的"RR旋转"，第二次是围绕"k3"进行的"LL旋转"。
         *
         * LR的旋转代码
         *
         * LR：左右对应的情况(左双旋转)。
         *
         * 返回值：旋转后的根节点
         */
        AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
            k3.left = rightRightRotation(k3.left);

            return leftLeftRotation(k3);
        }

        /**
         * RL的旋转
         *
         * RL是与LR的对称情况！RL恢复平衡的旋转方法如下：
         * https://images0.cnblogs.com/i/497634/201403/281628118447060.jpg
         *
         * 第一次旋转是围绕"k3"进行的"LL旋转"，第二次是围绕"k1"进行的"RR旋转"。
         * RL的旋转代码
         *
         * RL：右左对应的情况(右双旋转)。
         *
         * 返回值：旋转后的根节点
         */
        AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
            k1.right = leftLeftRotation(k1.right);

            return rightRightRotation(k1);
        }

        /**
         * 将结点插入到AVL树中，并返回根节点
         *
         * 参数说明：
         *     tree AVL树的根结点
         *     key 插入的结点的键值
         * 返回值：
         *     根节点
         */
        private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
            if (tree == null) {
                // 新建节点
                tree = new AVLTreeNode<T>(key, null, null);
                if (tree == null) {
                    System.out.println("ERROR: create avltree node failed!");
                    return null;
                }
            } else {
                int cmp = key.compareTo(tree.key);
                if (cmp < 0) {
                    // 应该将key插入到"tree的左子树"的情况
                    tree.left = insert(tree.left, key);
                    // 插入节点后，若AVL树失去平衡，则进行相应的调节。
                    if (height(tree.left) - height(tree.right) == 2) {
                        if (key.compareTo(tree.left.key) < 0)
                            tree = leftLeftRotation(tree);
                        else
                            tree = leftRightRotation(tree);
                    }
                } else if (cmp > 0) {
                    // 应该将key插入到"tree的右子树"的情况
                    tree.right = insert(tree.right, key);
                    // 插入节点后，若AVL树失去平衡，则进行相应的调节。
                    if (height(tree.right) - height(tree.left) == 2) {
                        if (key.compareTo(tree.right.key) > 0)
                            tree = rightRightRotation(tree);
                        else
                            tree = rightLeftRotation(tree);
                    }
                } else {
                    // cmp==0
                    System.out.println("添加失败：不允许添加相同的节点！");
                }
            }
            tree.height = max(height(tree.left), height(tree.right)) + 1;
            return tree;
        }

        public void insert(T key) {
            mRoot = insert(mRoot, key);
        }

        /**
         * 删除结点(z)，返回根节点
         * <p>
         * 参数说明：
         * tree AVL树的根结点
         * z 待删除的结点
         * 返回值：
         * 根节点
         */
        private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> z) {
            // 根为空 或者 没有要删除的节点，直接返回null。
            if (tree == null || z == null)
                return null;
            int cmp = z.key.compareTo(tree.key);
            if (cmp < 0) {
                // 待删除的节点在"tree的左子树"中
                tree.left = remove(tree.left, z);
                // 删除节点后，若AVL树失去平衡，则进行相应的调节。
                if (height(tree.right) - height(tree.left) == 2) {
                    AVLTreeNode<T> r = tree.right;
                    if (height(r.left) > height(r.right))
                        tree = rightLeftRotation(tree);
                    else
                        tree = rightRightRotation(tree);
                }
            } else if (cmp > 0) {
                // 待删除的节点在"tree的右子树"中
                tree.right = remove(tree.right, z);
                // 删除节点后，若AVL树失去平衡，则进行相应的调节。
                if (height(tree.left) - height(tree.right) == 2) {
                    AVLTreeNode<T> l = tree.left;
                    if (height(l.right) > height(l.left))
                        tree = leftRightRotation(tree);
                    else
                        tree = leftLeftRotation(tree);
                }
            } else {
                // tree是对应要删除的节点。
                // tree的左右孩子都非空
                if ((tree.left != null) && (tree.right != null)) {
                    if (height(tree.left) > height(tree.right)) {
                        // 如果tree的左子树比右子树高；
                        // 则(01)找出tree的左子树中的最大节点
                        //   (02)将该最大节点的值赋值给tree。
                        //   (03)删除该最大节点。
                        // 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                        // 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。
                        AVLTreeNode<T> max = maximum(tree.left);
                        tree.key = max.key;
                        tree.left = remove(tree.left, max);
                    } else {
                        // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                        // 则(01)找出tree的右子树中的最小节点
                        //   (02)将该最小节点的值赋值给tree。
                        //   (03)删除该最小节点。
                        // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                        // 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                        AVLTreeNode<T> min = maximum(tree.right);
                        tree.key = min.key;
                        tree.right = remove(tree.right, min);
                    }
                } else {
                    AVLTreeNode<T> tmp = tree;
                    tree = (tree.left != null) ? tree.left : tree.right;
                    tmp = null;
                }
            }

            return tree;
        }

        public void remove(T key) {
            AVLTreeNode<T> z;

            if ((z = search(mRoot, key)) != null)
                mRoot = remove(mRoot, z);
        }

        /**
         * 前序遍历"AVL树"
         */
        private void preOrder(AVLTreeNode<T> tree) {
            if(tree != null) {
                System.out.print(tree.key+" ");
                preOrder(tree.left);
                preOrder(tree.right);
            }
        }

        public void preOrder() {
            preOrder(mRoot);
        }

        /**
         * 中序遍历"AVL树"
         */
        private void inOrder(AVLTreeNode<T> tree) {
            if(tree != null)
            {
                inOrder(tree.left);
                System.out.print(tree.key+" ");
                inOrder(tree.right);
            }
        }

        public void inOrder() {
            inOrder(mRoot);
        }

        /**
         * 后序遍历"AVL树"
         */
        private void postOrder(AVLTreeNode<T> tree) {
            if(tree != null) {
                postOrder(tree.left);
                postOrder(tree.right);
                System.out.print(tree.key+" ");
            }
        }

        public void postOrder() {
            postOrder(mRoot);
        }

        /**
         * (递归实现)查找"AVL树x"中键值为key的节点
         */
        private AVLTreeNode<T> search(AVLTreeNode<T> x, T key) {
            if (x==null)
                return x;

            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return search(x.left, key);
            else if (cmp > 0)
                return search(x.right, key);
            else
                return x;
        }

        public AVLTreeNode<T> search(T key) {
            return search(mRoot, key);
        }

        /**
         * (非递归实现)查找"AVL树x"中键值为key的节点
         */
        private AVLTreeNode<T> iterativeSearch(AVLTreeNode<T> x, T key) {
            while (x!=null) {
                int cmp = key.compareTo(x.key);

                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;
                else
                    return x;
            }

            return x;
        }

        public AVLTreeNode<T> iterativeSearch(T key) {
            return iterativeSearch(mRoot, key);
        }

        /**
         * 查找最小结点：返回tree为根结点的AVL树的最小结点。
         */
        private AVLTreeNode<T> minimum(AVLTreeNode<T> tree) {
            if (tree == null)
                return null;

            while(tree.left != null)
                tree = tree.left;
            return tree;
        }

        public T minimum() {
            AVLTreeNode<T> p = minimum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /**
         * 查找最大结点：返回tree为根结点的AVL树的最大结点。
         */
        private AVLTreeNode<T> maximum(AVLTreeNode<T> tree) {
            if (tree == null)
                return null;

            while(tree.right != null)
                tree = tree.right;
            return tree;
        }

        public T maximum() {
            AVLTreeNode<T> p = maximum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /**
         * 销毁AVL树
         */
        private void destroy(AVLTreeNode<T> tree) {
            if (tree==null)
                return ;

            if (tree.left != null)
                destroy(tree.left);
            if (tree.right != null)
                destroy(tree.right);

            tree = null;
        }

        public void destroy() {
            destroy(mRoot);
        }

        /**
         * 打印"二叉查找树"
         *
         * key        -- 节点的键值
         * direction  --  0，表示该节点是根节点;
         *               -1，表示该节点是它的父结点的左孩子;
         *                1，表示该节点是它的父结点的右孩子。
         */
        private void print(AVLTreeNode<T> tree, T key, int direction) {
            if(tree != null) {
                if(direction==0)    // tree是根节点
                    System.out.printf("%2d is root\n", tree.key, key);
                else                // tree是分支节点
                    System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction==1?"right" : "left");

                print(tree.left, tree.key, -1);
                print(tree.right,tree.key,  1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }
    }

    private static void testAVLTree() {
        // AVL树
        int arr[] = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};

        int i;
        AVLTree<Integer> tree = new AVLTree<>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < arr.length; i++) {
            System.out.printf("%d ", arr[i]);
            tree.insert(arr[i]);
        }

        System.out.printf("\n== 前序遍历: ");
        tree.preOrder();

        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();

        System.out.printf("\n== 后序遍历: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== 高度: %d\n", tree.height());
        System.out.printf("== 最小值: %d\n", tree.minimum());
        System.out.printf("== 最大值: %d\n", tree.maximum());
        System.out.printf("== 树的详细信息: \n");
        tree.print();

        i = 8;
        System.out.printf("\n== 删除根节点: %d", i);
        tree.remove(i);

        System.out.printf("\n== 高度: %d", tree.height());
        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();
        System.out.printf("\n== 树的详细信息: \n");
        tree.print();

        // 销毁二叉树
        tree.destroy();
    }

    /**
     * 3. 伸展树
     *
     * <p>
     *     伸展树(Splay Tree)是特殊的二叉查找树。
     *
     *     它的特殊是指，它除了本身是棵二叉查找树之外，它还具备一个特点: 当某个节点被访问时，伸展树会通过旋转使该节点成为树根。
     *
     *     这样做的好处是，下次要访问该节点时，能够迅速的访问到该节点。
     * </p>
     *
     * 伸展树(Splay Tree)是一种二叉查找树，它能在O(log n)内完成插入、查找和删除操作。
     * 它由Daniel Sleator和Robert Tarjan创造。
     *
     * (01) 伸展树属于二叉查找树，即它具有和二叉查找树一样的性质：
     *      假设x为树中的任意一个结点，x节点包含关键字key，节点x的key值记为key[x]。
     *      如果y是x的左子树中的一个结点，则key[y] <= key[x]；
     *      如果y是x的右子树的一个结点，则key[y] >= key[x]。
     *
     * (02) 除了拥有二叉查找树的性质之外，伸展树还具有的一个特点是：
     *      当某个节点被访问时，伸展树会通过旋转使该节点成为树根。
     *      这样做的好处是，下次要访问该节点时，能够迅速的访问到该节点。
     *
     * 假设想要对一个二叉查找树执行一系列的查找操作。为了使整个查找时间更小，被查频率高的那些条目就应当经常处于靠近树根的位置。
     * 于是想到设计一个简单方法，在每次查找之后对树进行重构，把被查找的条目搬移到离树根近一些的地方。
     * 伸展树应运而生，它是一种自调整形式的二叉查找树，它会沿着从某个节点到树根之间的路径，通过一系列的旋转把这个节点搬移到树根去。
     *
     * 相比于"二叉查找树"和"AVL树"，学习伸展树时需要重点关注是"伸展树的旋转算法"。
     */
    private static class SplayTree<T extends Comparable<T>> {

        // 根结点
        private SplayTreeNode<T> mRoot;

        public class SplayTreeNode<T extends Comparable<T>> {
            // 关键字(键值)
            T key;
            // 左孩子
            SplayTreeNode<T> left;
            // 右孩子
            SplayTreeNode<T> right;

            public SplayTreeNode() {
                this.left = null;
                this.right = null;
            }

            public SplayTreeNode(T key, SplayTreeNode<T> left, SplayTreeNode<T> right) {
                this.key = key;
                this.left = left;
                this.right = right;
            }
        }

        public SplayTree() {
            mRoot = null;
        }

        /**
         * 前序遍历"伸展树"，根左右
         */
        private void preOrder(SplayTreeNode<T> tree) {
            if (tree != null) {
                System.out.print(tree.key + " ");
                preOrder(tree.left);
                preOrder(tree.right);
            }
        }

        public void preOrder() {
            preOrder(mRoot);
        }

        /**
         * 中序遍历"伸展树"，左根右
         */
        private void inOrder(SplayTreeNode<T> tree) {
            if (tree != null) {
                inOrder(tree.left);
                System.out.print(tree.key + " ");
                inOrder(tree.right);
            }
        }

        public void inOrder() {
            inOrder(mRoot);
        }

        /**
         * 后序遍历"伸展树"，左右根
         */
        private void postOrder(SplayTreeNode<T> tree) {
            if (tree != null) {
                postOrder(tree.left);
                postOrder(tree.right);
                System.out.print(tree.key + " ");
            }
        }

        public void postOrder() {
            postOrder(mRoot);
        }

        /**
         * (递归实现)查找"伸展树x"中键值为key的节点
         */
        private SplayTreeNode<T> search(SplayTreeNode<T> x, T key) {
            if (x == null)
                return x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return search(x.left, key);
            else if (cmp > 0)
                return search(x.right, key);
            else
                return x;
        }

        public SplayTreeNode<T> search(T key) {
            return search(mRoot, key);
        }

        /**
         * (非递归实现)查找"伸展树x"中键值为key的节点
         */
        private SplayTreeNode<T> iterativeSearch(SplayTreeNode<T> x, T key) {
            while (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;
                else
                    return x;
            }
            return x;
        }

        public SplayTreeNode<T> iterativeSearch(T key) {
            return iterativeSearch(mRoot, key);
        }

        /**
         * 查找最小结点：返回tree为根结点的伸展树的最小结点。
         */
        private SplayTreeNode<T> minimum(SplayTreeNode<T> tree) {
            if (tree == null)
                return null;

            while (tree.left != null)
                tree = tree.left;
            return tree;
        }

        public T minimum() {
            SplayTreeNode<T> p = minimum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /**
         * 查找最大结点：返回tree为根结点的伸展树的最大结点。
         */
        private SplayTreeNode<T> maximum(SplayTreeNode<T> tree) {
            if (tree == null)
                return null;

            while (tree.right != null)
                tree = tree.right;
            return tree;
        }

        public T maximum() {
            SplayTreeNode<T> p = maximum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /**
         * 旋转key对应的节点为根节点，并返回根节点。
         *
         * 注意：
         *   (a)：伸展树中存在"键值为key的节点"。
         *          将"键值为key的节点"旋转为根节点。
         *   (b)：伸展树中不存在"键值为key的节点"，并且key < tree.key。
         *      b-1 "键值为key的节点"的前驱节点存在的话，将"键值为key的节点"的前驱节点旋转为根节点。
         *      b-2 "键值为key的节点"的前驱节点存在的话，则意味着，key比树中任何键值都小，那么此时，将最小节点旋转为根节点。
         *   (c)：伸展树中不存在"键值为key的节点"，并且key > tree.key。
         *      c-1 "键值为key的节点"的后继节点存在的话，将"键值为key的节点"的后继节点旋转为根节点。
         *      c-2 "键值为key的节点"的后继节点不存在的话，则意味着，key比树中任何键值都大，那么此时，将最大节点旋转为根节点。
         */
        private SplayTreeNode<T> splay(SplayTreeNode<T> tree, T key) {
            if (tree == null)
                return tree;

            SplayTreeNode<T> N = new SplayTreeNode<>();
            SplayTreeNode<T> l = N;
            SplayTreeNode<T> r = N;
            SplayTreeNode<T> c;

            for (; ; ) {
                int cmp = key.compareTo(tree.key);
                if (cmp < 0) {
                    if (tree.left == null)
                        break;

                    if (key.compareTo(tree.left.key) < 0) {
                        c = tree.left;                           /* rotate right */
                        tree.left = c.right;
                        c.right = tree;
                        tree = c;
                        if (tree.left == null)
                            break;
                    }
                    r.left = tree;                               /* link right */
                    r = tree;
                    tree = tree.left;
                } else if (cmp > 0) {
                    if (tree.right == null)
                        break;

                    if (key.compareTo(tree.right.key) > 0) {
                        c = tree.right;                          /* rotate left */
                        tree.right = c.left;
                        c.left = tree;
                        tree = c;
                        if (tree.right == null)
                            break;
                    }

                    l.right = tree;                              /* link left */
                    l = tree;
                    tree = tree.right;
                } else {
                    break;
                }
            }

            l.right = tree.left;                                /* assemble */
            r.left = tree.right;
            tree.left = N.right;
            tree.right = N.left;

            return tree;
        }

        public void splay(T key) {
            mRoot = splay(mRoot, key);
        }

        /**
         * 将结点插入到伸展树中，并返回根节点
         *
         * 参数说明：
         *     tree 伸展树的
         *     z 插入的结点
         */
        private SplayTreeNode<T> insert(SplayTreeNode<T> tree, SplayTreeNode<T> z) {
            int cmp;
            SplayTreeNode<T> y = null;
            SplayTreeNode<T> x = tree;

            // 查找z的插入位置
            while (x != null) {
                y = x;
                cmp = z.key.compareTo(x.key);
                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;
                else {
                    System.out.printf("不允许插入相同节点(%d)!\n", z.key);
                    z = null;
                    return tree;
                }
            }

            if (y == null)
                tree = z;
            else {
                cmp = z.key.compareTo(y.key);
                if (cmp < 0)
                    y.left = z;
                else
                    y.right = z;
            }

            return tree;
        }

        public void insert(T key) {
            SplayTreeNode<T> z = new SplayTreeNode<T>(key, null, null);

            // 如果新建结点失败，则返回。
            if ((z = new SplayTreeNode<T>(key, null, null)) == null)
                return;

            // 插入节点
            mRoot = insert(mRoot, z);
            // 将节点(key)旋转为根节点
            mRoot = splay(mRoot, key);
        }

        /**
         * 删除结点(z)，并返回被删除的结点
         *
         * 参数说明：
         *     bst 伸展树
         *     z 删除的结点
         */
        private SplayTreeNode<T> remove(SplayTreeNode<T> tree, T key) {
            SplayTreeNode<T> x;

            if (tree == null)
                return null;

            // 查找键值为key的节点，找不到的话直接返回。
            if (search(tree, key) == null)
                return tree;

            // 将key对应的节点旋转为根节点。
            tree = splay(tree, key);

            if (tree.left != null) {
                // 将"tree的前驱节点"旋转为根节点
                x = splay(tree.left, key);
                // 移除tree节点
                x.right = tree.right;
            } else
                x = tree.right;

            tree = null;
            return x;
        }

        public void remove(T key) {
            mRoot = remove(mRoot, key);
        }

        /**
         * 销毁伸展树
         */
        private void destroy(SplayTreeNode<T> tree) {
            if (tree == null)
                return;

            if (tree.left != null)
                destroy(tree.left);
            if (tree.right != null)
                destroy(tree.right);

            tree = null;
        }

        public void clear() {
            destroy(mRoot);
            mRoot = null;
        }

        /**
         * 打印"伸展树"
         *
         * key        -- 节点的键值
         * direction  --  0，表示该节点是根节点;
         *               -1，表示该节点是它的父结点的左孩子;
         *                1，表示该节点是它的父结点的右孩子。
         */
        private void print(SplayTreeNode<T> tree, T key, int direction) {
            if (tree != null) {

                if (direction == 0)    // tree是根节点
                    System.out.printf("%2d is root\n", tree.key);
                else                // tree是分支节点
                    System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction == 1 ? "right" : "left");

                print(tree.left, tree.key, -1);
                print(tree.right, tree.key, 1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }
    }

    private static void testSplayTree() {
        int arr[] = {10, 50, 40, 30, 20, 60};
        int i, ilen;
        SplayTree<Integer> tree = new SplayTree<>();

        System.out.print("== 依次添加: ");
        ilen = arr.length;
        for (i = 0; i < ilen; i++) {
            System.out.print(arr[i] + " ");
            tree.insert(arr[i]);
        }

        System.out.print("\n== 前序遍历: ");
        tree.preOrder();

        System.out.print("\n== 中序遍历: ");
        tree.inOrder();

        System.out.print("\n== 后序遍历: ");
        tree.postOrder();
        System.out.println();

        System.out.println("== 最小值: " + tree.minimum());
        System.out.println("== 最大值: " + tree.maximum());
        System.out.println("== 树的详细信息: ");
        tree.print();

        i = 30;
        System.out.printf("\n== 旋转节点(%d)为根节点\n", i);
        tree.splay(i);
        System.out.printf("== 树的详细信息: \n");
        tree.print();

        // 销毁二叉树
        tree.clear();
    }

    /*
     * 4. 红黑树
     *
     * (4.1) R-B Tree，全称是Red-Black Tree，又称为“红黑树”，它一种特殊的二叉查找树。
     * 红黑树的每个节点上都有存储位表示节点的颜色，可以是红(Red)或黑(Black)。
     *
     * 红黑树的特性:
     * （1）每个节点或者是黑色，或者是红色。
     * （2）根节点是黑色。
     * （3）每个叶子节点（NIL）是黑色。 [注意：这里叶子节点，是指为空(NIL或NULL)的叶子节点！]
     * （4）如果一个节点是红色的，则它的子节点必须是黑色的。
     * （5）从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
     *
     * 注意：
     * (01) 特性(3)中的叶子节点，是只为空(NIL或null)的节点。
     * (02) 特性(5)，确保没有一条路径会比其他路径长出俩倍。因而，红黑树是相对是接近平衡的二叉树（AVL树）。
     *
     * 红黑树示意图：
     * https://images0.cnblogs.com/i/497634/201403/251730074203156.jpg
     *
     *
     * (4.2) 红黑树的应用：
     *
     * 红黑树的应用比较广泛，主要是用它来存储有序的数据，它的时间复杂度是O(lgn)，效率非常之高。
     * 例如，Java集合中的TreeSet和TreeMap，C++ STL中的set、map，以及Linux虚拟内存的管理，都是通过红黑树去实现的。
     *
     * (4.3) 红黑树的时间复杂度和相关证明
     *
     * 红黑树的时间复杂度为: O(lgn)
     * 下面通过“数学归纳法”对红黑树的时间复杂度进行证明。
     *
     * ********** 定理：一棵含有n个节点的红黑树的高度至多为2log(n+1) **********
     *
     * 证明：
     *     "一棵含有n个节点的红黑树的高度至多为2log(n+1)" 的逆否命题是 "高度为h的红黑树，它的包含的内节点个数至少为 2h/2-1个"。
     *     我们只需要证明逆否命题，即可证明原命题为真；即只需证明 "高度为h的红黑树，它的包含的内节点个数至少为 2h/2-1个"。
     *
     *     从某个节点x出发（不包括该节点）到达一个叶节点的任意一条路径上，黑色节点的个数称为该节点的黑高度(x's black height)，记为bh(x)。
     *     关于bh(x)有两点需要说明：
     *     第1点：根据红黑树的"特性(5) ，即从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点"可知，
     *     从节点x出发到达的所有的叶节点具有相同数目的黑节点。这也就意味着，bh(x)的值是唯一的！
     *     第2点：根据红黑色的"特性(4)，即如果一个节点是红色的，则它的子节点必须是黑色的"可知，
     *     从节点x出发达到叶节点"所经历的黑节点数目">= "所经历的红节点的数目"。假设x是根节点，
     *     则可以得出结论"bh(x) >= h/2"。进而，我们只需证明 "高度为h的红黑树，它的包含的黑节点个数至少为 2bh(x)-1个"即可。
     *
     *     到这里，我们将需要证明的定理已经由
     * "一棵含有n个节点的红黑树的高度至多为2log(n+1)"
     *     转变成只需要证明
     * "高度为h的红黑树，它的包含的内节点个数至少为 2bh(x)-1个"。
     *
     *
     * 下面通过"数学归纳法"开始论证高度为h的红黑树，它的包含的内节点个数至少为 2bh(x)-1个"。
     *
     * (01) 当树的高度h=0时，
     *     内节点个数是0，bh(x) 为0，2bh(x)-1 也为 0。显然，原命题成立。
     *
     * (02) 当h>0，且树的高度为 h-1 时，它包含的节点个数至少为 2bh(x)-1-1。这个是根据(01)推断出来的！
     *
     *     下面，由树的高度为 h-1 的已知条件推出“树的高度为 h 时，它所包含的节点树为 2bh(x)-1”。
     *
     *     当树的高度为 h 时，
     *     对于节点x(x为根节点)，其黑高度为bh(x)。
     *     对于节点x的左右子树，它们黑高度为 bh(x) 或者 bh(x)-1。
     *     根据(02)的已知条件，我们已知 "x的左右子树，即高度为 h-1 的节点，它包含的节点至少为 2bh(x)-1-1 个"；
     *
     *     所以，节点x所包含的节点至少为 ( 2bh(x)-1-1 ) + ( 2bh(x)-1-1 ) + 1 = 2^bh(x)-1。即节点x所包含的节点至少为 2bh(x)-1。
     *     因此，原命题成立。
     *
     *     由(01)、(02)得出，"高度为h的红黑树，它的包含的内节点个数至少为 2^bh(x)-1个"。
     *     因此，********** “一棵含有n个节点的红黑树的高度至多为2log(n+1)” **********。
     */

    /**
     * 5. 红黑树的Java实现(代码说明)
     *
     * 红黑树的基本操作是添加、删除和旋转。
     *
     * 在对红黑树进行添加或删除后，会用到旋转方法。
     * 为什么呢？道理很简单，添加或删除红黑树中的节点之后，红黑树就发生了变化，可能不满足红黑树的5条性质，
     * 也就不再是一颗红黑树了，而是一颗普通的树。而通过旋转，可以使这颗树重新成为红黑树。
     * 简单点说，旋转的目的是让树保持红黑树的特性。
     *
     * 旋转包括两种：左旋 和 右旋。下面分别对红黑树的基本操作进行介绍。
     */
    private static class RBTree<T extends Comparable<T>> {

        // 根结点
        private RBTNode<T> mRoot;

        private static final boolean RED = false;
        private static final boolean BLACK = true;

        static class RBTNode<T extends Comparable<T>> {

            // 颜色
            boolean color;
            // 关键字
            T key;
            // 左孩子
            RBTNode<T> left;
            // 右孩子
            RBTNode<T> right;
            // 父结点
            RBTNode<T> parent;

            public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
                this.key = key;
                this.color = color;
                this.parent = parent;
                this.left = left;
                this.right = right;
            }
        }

        public RBTree() {
            mRoot = null;
        }

        private RBTNode<T> parentOf(RBTNode<T> node) {
            return node != null ? node.parent : null;
        }

        private boolean colorOf(RBTNode<T> node) {
            return node != null ? node.color : BLACK;
        }

        private boolean isRed(RBTNode<T> node) {
            return ((node != null) && (node.color == RED)) ? true : false;
        }

        private boolean isBlack(RBTNode<T> node) {
            return !isRed(node);
        }

        private void setBlack(RBTNode<T> node) {
            if (node != null)
                node.color = BLACK;
        }

        private void setRed(RBTNode<T> node) {
            if (node != null)
                node.color = RED;
        }

        private void setParent(RBTNode<T> node, RBTNode<T> parent) {
            if (node != null)
                node.parent = parent;
        }

        private void setColor(RBTNode<T> node, boolean color) {
            if (node != null)
                node.color = color;
        }

        /*
         * 前序遍历"红黑树"
         */
        private void preOrder(RBTNode<T> tree) {
            if (tree != null) {
                System.out.print(tree.key + " ");
                preOrder(tree.left);
                preOrder(tree.right);
            }
        }

        public void preOrder() {
            preOrder(mRoot);
        }

        /*
         * 中序遍历"红黑树"
         */
        private void inOrder(RBTNode<T> tree) {
            if (tree != null) {
                inOrder(tree.left);
                System.out.print(tree.key + " ");
                inOrder(tree.right);
            }
        }

        public void inOrder() {
            inOrder(mRoot);
        }


        /*
         * 后序遍历"红黑树"
         */
        private void postOrder(RBTNode<T> tree) {
            if (tree != null) {
                postOrder(tree.left);
                postOrder(tree.right);
                System.out.print(tree.key + " ");
            }
        }

        public void postOrder() {
            postOrder(mRoot);
        }

        /*
         * (递归实现)查找"红黑树x"中键值为key的节点
         */
        private RBTNode<T> search(RBTNode<T> x, T key) {
            if (x == null)
                return x;

            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return search(x.left, key);
            else if (cmp > 0)
                return search(x.right, key);
            else
                return x;
        }

        public RBTNode<T> search(T key) {
            return search(mRoot, key);
        }

        /*
         * (非递归实现)查找"红黑树x"中键值为key的节点
         */
        private RBTNode<T> iterativeSearch(RBTNode<T> x, T key) {
            while (x != null) {
                int cmp = key.compareTo(x.key);

                if (cmp < 0)
                    x = x.left;
                else if (cmp > 0)
                    x = x.right;
                else
                    return x;
            }

            return x;
        }

        public RBTNode<T> iterativeSearch(T key) {
            return iterativeSearch(mRoot, key);
        }

        /*
         * 查找最小结点：返回tree为根结点的红黑树的最小结点。
         */
        private RBTNode<T> minimum(RBTNode<T> tree) {
            if (tree == null)
                return null;

            while (tree.left != null)
                tree = tree.left;
            return tree;
        }

        public T minimum() {
            RBTNode<T> p = minimum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /*
         * 查找最大结点：返回tree为根结点的红黑树的最大结点。
         */
        private RBTNode<T> maximum(RBTNode<T> tree) {
            if (tree == null)
                return null;

            while (tree.right != null)
                tree = tree.right;
            return tree;
        }

        public T maximum() {
            RBTNode<T> p = maximum(mRoot);
            if (p != null)
                return p.key;

            return null;
        }

        /*
         * 找结点(x)的后继结点。即，查找"红黑树中数据值大于该结点"的"最小结点"。
         */
        public RBTNode<T> successor(RBTNode<T> x) {
            // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
            if (x.right != null)
                return minimum(x.right);

            // 如果x没有右孩子。则x有以下两种可能：
            // (01) x是"一个左孩子"，则"x的后继结点"为 "它的父结点"。
            // (02) x是"一个右孩子"，则查找"x的最低的父结点，并且该父结点要具有左孩子"，找到的这个"最低的父结点"就是"x的后继结点"。
            RBTNode<T> y = x.parent;
            while ((y != null) && (x == y.right)) {
                x = y;
                y = y.parent;
            }

            return y;
        }

        /*
         * 找结点(x)的前驱结点。即，查找"红黑树中数据值小于该结点"的"最大结点"。
         */
        public RBTNode<T> predecessor(RBTNode<T> x) {
            // 如果x存在左孩子，则"x的前驱结点"为 "以其左孩子为根的子树的最大结点"。
            if (x.left != null)
                return maximum(x.left);

            // 如果x没有左孩子。则x有以下两种可能：
            // (01) x是"一个右孩子"，则"x的前驱结点"为 "它的父结点"。
            // (01) x是"一个左孩子"，则查找"x的最低的父结点，并且该父结点要具有右孩子"，找到的这个"最低的父结点"就是"x的前驱结点"。
            RBTNode<T> y = x.parent;
            while ((y != null) && (x == y.left)) {
                x = y;
                y = y.parent;
            }

            return y;
        }

        /*
         * 对x进行左旋，意味着"将x变成一个左节点"。
         *
         * 对红黑树的节点(x)进行左旋转
         *
         * 左旋示意图(对节点x进行左旋)：
         *      px                              px
         *     /                               /
         *    x                               y
         *   /  \      --(左旋)-.           / \                #
         *  lx   y                          x  ry
         *     /   \                       /  \
         *    ly   ry                     lx  ly
         *
         *
         */
        private void leftRotate(RBTNode<T> x) {
            // x的右孩子为y
            RBTNode<T> y = x.right;
            // 将 “y的左孩子” 设为 “x的右孩子”；
            // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            // 将 “x的父亲” 设为 “y的父亲”
            y.parent = x.parent;
            if (x.parent == null) {
                // 如果 “x的父亲” 是空节点，则将y设为根节点
                this.mRoot = y;
            } else {
                if (x.parent.left == x) {
                    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                    x.parent.left = y;
                } else {
                    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
                    x.parent.right = y;
                }
            }
            // 将 “x” 设为 “y的左孩子”
            y.left = x;
            // 将 “x的父节点” 设为 “y”
            x.parent = y;
        }

        /*
         * 对y进行右旋，意味着"将y变成一个右节点"。
         *
         * 对红黑树的节点(y)进行右旋转
         *
         * 右旋示意图(对节点y进行左旋)：
         *            py                               py
         *           /                                /
         *          y                                x
         *         /  \      --(右旋)-.            /  \                     #
         *        x   ry                           lx   y
         *       / \                                   / \                   #
         *      lx  rx                                rx  ry
         *
         */
        private void rightRotate(RBTNode<T> y) {
            // 设置x是当前节点的左孩子。
            RBTNode<T> x = y.left;

            // 将 “x的右孩子” 设为 “y的左孩子”；
            // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
            y.left = x.right;
            if (x.right != null) {
                x.right.parent = y;
            }

            // 将 “y的父亲” 设为 “x的父亲”
            x.parent = y.parent;

            if (y.parent == null) {
                // 如果 “y的父亲” 是空节点，则将x设为根节点
                this.mRoot = x;
            } else {
                if (y == y.parent.right) {
                    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
                    y.parent.right = x;
                } else {
                    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
                    y.parent.left = x;
                }
            }
            // 将 “y” 设为 “x的右孩子”
            x.right = y;

            // 将 “y的父节点” 设为 “x”
            y.parent = x;
        }

        /**
         * 添加
         *
         * 将一个节点插入到红黑树中，需要执行哪些步骤呢？
         * 首先，将红黑树当作一颗二叉查找树，将节点插入；
         * 然后，将节点着色为红色；
         * 最后，通过"旋转和重新着色"等一系列操作来修正该树，使之重新成为一颗红黑树。
         *
         * 详细描述如下：
         *
         * 第一步: 将红黑树当作一颗二叉查找树，将节点插入。
         *        红黑树本身就是一颗二叉查找树，将节点插入后，该树仍然是一颗二叉查找树。
         *        也就意味着，树的键值仍然是有序的。
         *        此外，无论是左旋还是右旋，若旋转之前这棵树是二叉查找树，旋转之后它一定还是二叉查找树。
         *        这也就意味着，任何的旋转和重新着色操作，都不会改变它仍然是一颗二叉查找树的事实。
         *
         * 好吧？那接下来，我们就来想方设法的旋转以及重新着色，使这颗树重新成为红黑树！
         *
         * 第二步：将插入的节点着色为"红色"。
         *        为什么着色成红色，而不是黑色呢？为什么呢？在回答之前，我们需要重新温习一下红黑树的特性：
         * (1) 每个节点或者是黑色，或者是红色。
         * (2) 根节点是黑色。
         * (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
         * (4) 如果一个节点是红色的，则它的子节点必须是黑色的。
         * (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
         *
         * 将插入的节点着色为红色，不会违背"特性(5)"！少违背一条特性，就意味着我们需要处理的情况越少。
         * 接下来，就要努力的让这棵树满足其它性质即可；满足了的话，它就又是一颗红黑树了。o(∩∩)o...哈哈
         *
         * 第三步: 通过一系列的旋转或着色等操作，使之重新成为一颗红黑树。
         *        第二步中，将插入节点着色为"红色"之后，不会违背"特性(5)"。那它到底会违背哪些特性呢？
         *        对于"特性(1)"，显然不会违背了。因为我们已经将它涂成红色了。
         *        对于"特性(2)"，显然也不会违背。在第一步中，我们是将红黑树当作二叉查找树，然后执行的插入操作。
         *        而根据二叉查找数的特点，插入操作不会改变根节点。所以，根节点仍然是黑色。
         *        对于"特性(3)"，显然不会违背了。这里的叶子节点是指的空叶子节点，插入非空节点并不会对它们造成影响。
         *        对于"特性(4)"，是有可能违背的！
         *        那接下来，想办法使之"满足特性(4)"，就可以将树重新构造成红黑树了。
         */

        /*
         * 将结点插入到红黑树中
         *
         * 参数说明：
         *     node 插入的结点        // 对应《算法导论》中的node
         */
        private void insert(RBTNode<T> node) {
            int cmp;
            RBTNode<T> y = null;
            RBTNode<T> x = this.mRoot;

            // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
            while (x != null) {
                y = x;
                cmp = node.key.compareTo(x.key);
                if (cmp < 0)
                    x = x.left;
                else
                    x = x.right;
            }

            node.parent = y;
            if (y != null) {
                cmp = node.key.compareTo(y.key);
                if (cmp < 0)
                    y.left = node;
                else
                    y.right = node;
            } else {
                this.mRoot = node;
            }

            // 2. 设置节点的颜色为红色
            node.color = RED;

            // 3. 将它重新修正为一颗二叉查找树
            insertFixUp(node);
        }

        /*
         * 红黑树插入修正函数
         *
         * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
         * 目的是将它重新塑造成一颗红黑树。
         *
         * 参数说明：
         *     node 插入的结点        // 对应《算法导论》中的z
         */
        private void insertFixUp(RBTNode<T> node) {
            RBTNode<T> parent, gparent;

            // 若“父节点存在，并且父节点的颜色是红色”
            while (((parent = parentOf(node)) != null) && isRed(parent)) {
                gparent = parentOf(parent);

                // 若“父节点”是“祖父节点的左孩子”
                if (parent == gparent.left) {
                    // Case 1条件：叔叔节点是红色
                    RBTNode<T> uncle = gparent.right;
                    if ((uncle != null) && isRed(uncle)) {
                        setBlack(uncle);
                        setBlack(parent);
                        setRed(gparent);
                        node = gparent;
                        continue;
                    }

                    // Case 2条件：叔叔是黑色，且当前节点是右孩子
                    if (parent.right == node) {
                        RBTNode<T> tmp;
                        leftRotate(parent);
                        tmp = parent;
                        parent = node;
                        node = tmp;
                    }

                    // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                    setBlack(parent);
                    setRed(gparent);
                    rightRotate(gparent);
                } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                    // Case 1条件：叔叔节点是红色
                    RBTNode<T> uncle = gparent.left;
                    if ((uncle != null) && isRed(uncle)) {
                        setBlack(uncle);
                        setBlack(parent);
                        setRed(gparent);
                        node = gparent;
                        continue;
                    }

                    // Case 2条件：叔叔是黑色，且当前节点是左孩子
                    if (parent.left == node) {
                        RBTNode<T> tmp;
                        rightRotate(parent);
                        tmp = parent;
                        parent = node;
                        node = tmp;
                    }

                    // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                    setBlack(parent);
                    setRed(gparent);
                    leftRotate(gparent);
                }
            }

            // 将根节点设为黑色
            setBlack(this.mRoot);
        }

        /*
         * 新建结点(key)，并将其插入到红黑树中
         *
         * 参数说明：
         *     key 插入结点的键值
         */
        public void insert(T key) {
            RBTNode<T> node=new RBTNode<T>(key,BLACK,null,null,null);

            // 如果新建结点失败，则返回。
            if (node != null)
                insert(node);
        }

        /**
         * 删除操作
         *
         * 将红黑树内的某一个节点删除。
         * 需要执行的操作依次是：首先，将红黑树当作一颗二叉查找树，将该节点从二叉查找树中删除；
         * 然后，通过"旋转和重新着色"等一系列来修正该树，使之重新成为一棵红黑树。
         *
         * 详细描述如下：
         * 第一步：将红黑树当作一颗二叉查找树，将节点删除。
         *        这和"删除常规二叉查找树中删除节点的方法是一样的"。分3种情况：
         * ① 被删除节点没有儿子，即为叶节点。那么，直接将该节点删除就OK了。
         * ② 被删除节点只有一个儿子。那么，直接删除该节点，并用该节点的唯一子节点顶替它的位置。
         * ③ 被删除节点有两个儿子。那么，先找出它的后继节点；然后把“它的后继节点的内容”复制给“该节点的内容”；
         * 之后，删除“它的后继节点”。在这里，后继节点相当于替身，在将后继节点的内容复制给"被删除节点"之后，
         * 再将后继节点删除。这样就巧妙的将问题转换为"删除后继节点"的情况了，下面就考虑后继节点。
         * 在"被删除节点"有两个非空子节点的情况下，它的后继节点不可能是双子非空。
         * 既然"的后继节点"不可能双子都非空，就意味着"该节点的后继节点"要么没有儿子，要么只有一个儿子。
         * 若没有儿子，则按"情况① "进行处理；若只有一个儿子，则按"情况② "进行处理。
         *
         * 第二步：通过"旋转和重新着色"等一系列来修正该树，使之重新成为一棵红黑树。
         *        因为"第一步"中删除节点之后，可能会违背红黑树的特性。所以需要通过"旋转和重新着色"来修正该树，
         *        使之重新成为一棵红黑树。
         */

        /*
         * 删除结点(node)，并返回被删除的结点
         *
         * 参数说明：
         *     node 删除的结点
         */
        private void remove(RBTNode<T> node) {
            RBTNode<T> child, parent;
            boolean color;

            // 被删除节点的"左右孩子都不为空"的情况。
            if ((node.left != null) && (node.right != null)) {
                // 被删节点的后继节点。(称为"取代节点")
                // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
                RBTNode<T> replace = node;

                // 获取后继节点
                replace = replace.right;
                while (replace.left != null)
                    replace = replace.left;

                // "node节点"不是根节点(只有根节点不存在父节点)
                if (parentOf(node) != null) {
                    if (parentOf(node).left == node)
                        parentOf(node).left = replace;
                    else
                        parentOf(node).right = replace;
                } else {
                    // "node节点"是根节点，更新根节点。
                    this.mRoot = replace;
                }

                // child是"取代节点"的右孩子，也是需要"调整的节点"。
                // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
                child = replace.right;
                parent = parentOf(replace);
                // 保存"取代节点"的颜色
                color = colorOf(replace);

                // "被删除节点"是"它的后继节点的父节点"
                if (parent == node) {
                    parent = replace;
                } else {
                    // child不为空
                    if (child != null)
                        setParent(child, parent);
                    parent.left = child;

                    replace.right = node.right;
                    setParent(node.right, replace);
                }

                replace.parent = node.parent;
                replace.color = node.color;
                replace.left = node.left;
                node.left.parent = replace;

                if (color == BLACK)
                    removeFixUp(child, parent);

                node = null;
                return;
            }

            if (node.left != null) {
                child = node.left;
            } else {
                child = node.right;
            }

            parent = node.parent;
            // 保存"取代节点"的颜色
            color = node.color;

            if (child != null)
                child.parent = parent;

            // "node节点"不是根节点
            if (parent != null) {
                if (parent.left == node)
                    parent.left = child;
                else
                    parent.right = child;
            } else {
                this.mRoot = child;
            }

            if (color == BLACK)
                removeFixUp(child, parent);
            node = null;
        }

        /*
         * 删除结点(z)，并返回被删除的结点
         *
         * 参数说明：
         *     tree 红黑树的根结点
         *     z 删除的结点
         */
        public void remove(T key) {
            RBTNode<T> node;

            if ((node = search(mRoot, key)) != null)
                remove(node);
        }

        /*
         * 红黑树删除修正函数
         *
         * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
         * 目的是将它重新塑造成一颗红黑树。
         *
         * 参数说明：
         *     node 待修正的节点
         */
        private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
            RBTNode<T> other;

            while ((node == null || isBlack(node)) && (node != this.mRoot)) {
                if (parent.left == node) {
                    other = parent.right;
                    if (isRed(other)) {
                        // Case 1: x的兄弟w是红色的
                        setBlack(other);
                        setRed(parent);
                        leftRotate(parent);
                        other = parent.right;
                    }

                    if ((other.left == null || isBlack(other.left)) &&
                            (other.right == null || isBlack(other.right))) {
                        // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                        setRed(other);
                        node = parent;
                        parent = parentOf(node);
                    } else {

                        if (other.right == null || isBlack(other.right)) {
                            // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                            setBlack(other.left);
                            setRed(other);
                            rightRotate(other);
                            other = parent.right;
                        }
                        // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                        setColor(other, colorOf(parent));
                        setBlack(parent);
                        setBlack(other.right);
                        leftRotate(parent);
                        node = this.mRoot;
                        break;
                    }
                } else {

                    other = parent.left;
                    if (isRed(other)) {
                        // Case 1: x的兄弟w是红色的
                        setBlack(other);
                        setRed(parent);
                        rightRotate(parent);
                        other = parent.left;
                    }

                    if ((other.left == null || isBlack(other.left)) &&
                            (other.right == null || isBlack(other.right))) {
                        // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                        setRed(other);
                        node = parent;
                        parent = parentOf(node);
                    } else {

                        if (other.left == null || isBlack(other.left)) {
                            // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                            setBlack(other.right);
                            setRed(other);
                            leftRotate(other);
                            other = parent.left;
                        }

                        // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                        setColor(other, colorOf(parent));
                        setBlack(parent);
                        setBlack(other.left);
                        rightRotate(parent);
                        node = this.mRoot;
                        break;
                    }
                }
            }

            if (node != null)
                setBlack(node);
        }

        /*
         * 销毁红黑树
         */
        private void destroy(RBTNode<T> tree) {
            if (tree == null)
                return;

            if (tree.left != null)
                destroy(tree.left);
            if (tree.right != null)
                destroy(tree.right);

            tree = null;
        }

        public void clear() {
            destroy(mRoot);
            mRoot = null;
        }

        /*
         * 打印"红黑树"
         *
         * key        -- 节点的键值
         * direction  --  0，表示该节点是根节点;
         *               -1，表示该节点是它的父结点的左孩子;
         *                1，表示该节点是它的父结点的右孩子。
         */
        private void print(RBTNode<T> tree, T key, int direction) {

            if (tree != null) {

                if (direction == 0)    // tree是根节点
                    System.out.printf("%2d(B) is root\n", tree.key);
                else                // tree是分支节点
                    System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree) ? "R" : "B", key, direction == 1 ? "right" : "left");

                print(tree.left, tree.key, -1);
                print(tree.right, tree.key, 1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }
    }

    private static void testRBTree() {
        final int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        final boolean mDebugInsert = false;    // "插入"动作的检测开关(false，关闭；true，打开)
        final boolean mDebugDelete = false;    // "删除"动作的检测开关(false，关闭；true，打开)
        int i, ilen = a.length;
        RBTree<Integer> tree = new RBTree<>();

        System.out.printf("== 原始数据: ");
        for (i = 0; i < ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for (i = 0; i < ilen; i++) {
            tree.insert(a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (mDebugInsert) {
                System.out.printf("== 添加节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.printf("== 前序遍历: ");
        tree.preOrder();

        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();

        System.out.printf("\n== 后序遍历: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== 最小值: %s\n", tree.minimum());
        System.out.printf("== 最大值: %s\n", tree.maximum());
        System.out.printf("== 树的详细信息: \n");
        tree.print();
        System.out.printf("\n");

        // 设置mDebugDelete=true,测试"删除函数"
        if (mDebugDelete) {
            for (i = 0; i < ilen; i++) {
                tree.remove(a[i]);

                System.out.printf("== 删除节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        // 销毁二叉树
        tree.clear();
    }

    /**
     * 6. 哈夫曼树（最优二叉树）
     *
     * Huffman Tree，中文名是哈夫曼树或霍夫曼树，它是最优二叉树。
     *
     * 定义：给定n个权值作为n个叶子结点，构造一棵二叉树，若树的带权路径长度达到最小，则这棵树被称为哈夫曼树。
     *
     *
     * 这个定义里面涉及到了几个陌生的概念，下面就是一颗哈夫曼树，我们来看图解答。
     *
     * (01) 路径和路径长度
     *
     * 定义：在一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径。
     * 通路中分支的数目称为路径长度。若规定根结点的层数为1，则从根结点到第L层结点的路径长度为L-1。
     *
     * 例子：100和80的路径长度是1，50和30的路径长度是2，20和10的路径长度是3。
     *
     *
     * (02) 结点的权及带权路径长度
     *
     * 定义：若将树中结点赋给一个有着某种含义的数值，则这个数值称为该结点的权。
     * 结点的带权路径长度为：从根结点到该结点之间的路径长度与该结点的权的乘积。
     *
     * 例子：节点20的路径长度是3，它的 带权路径长度 = 路径长度 * 权 = 3 * 20 = 60。
     *
     *
     * (03) 树的带权路径长度
     *
     * 定义：树的带权路径长度规定为所有 叶子结点 的带权路径长度之和，记为WPL。
     *
     * 例子：示例中，树的WPL= 1*100 + 2*50 + 3*20 + 3*10 = 100 + 160 + 60 + 30 = 350。
     *
     * 比较下面两棵树:
     * ===============================================================================
     *
     * 下面的两棵树都是以{10, 20, 50, 100}为叶子节点的树:
     *
     *                     180
     *          30          |          150
     *       20    10       |       50    100
     *
     *
     *
     *                     180
     *            100       |        80
     *                      |     50     30
     *                      |          20  10
     *
     *
     * 第一棵树的WPL = 2*10 + 2*20 + 2*50 + 2*100 = 360
     * 第二棵树的WPL = 350
     *
     * 第一棵树WPL > 第二棵树的WPL。
     * 你也可以计算除上面两种示例之外的情况，但实际上第二棵树树就是{10,20,50,100}对应的哈夫曼树。
     * 至此，应该堆哈夫曼树的概念有了一定的了解了，下面看看如何去构造一棵哈夫曼树。
     * =================================================================================
     *
     *
     *
     * 哈夫曼树的图文解析：
     *
     * 假设有n个权值，则构造出的哈夫曼树有n个叶子结点。 n个权值分别设为 w1、w2、…、wn，哈夫曼树的构造规则为：
     *
     * 1. 将w1、w2、…，wn看成是有n 棵树的森林(每棵树仅有一个结点)；
     * 2. 在森林中选出根结点的权值最小的两棵树进行合并，作为一棵新树的左、右子树，且新树的根结点权值为其左、右子树根结点权值之和；
     * 3. 从森林中删除选取的两棵树，并将新树加入森林；
     * 4. 重复(02)、(03)步，直到森林中只剩一棵树为止，该树即为所求得的哈夫曼树。
     *
     * 第1步：创建森林，森林包括5棵树，这5棵树的权值分别是5,6,7,8,15。
     *
     * 第2步：在森林中，选择根节点权值最小的两棵树(5和6)来进行合并，将它们作为一颗新树的左右孩子
     *       (谁左谁右无关紧要，这里，我们选择较小的作为左孩子)，并且新树的权值是左右孩子的权值之和。即，新树的权值是11。
     *       然后，将"树5"和"树6"从森林中删除，并将新的树(树11)添加到森林中。
     *
     * 第3步：在森林中，选择根节点权值最小的两棵树(7和8)来进行合并。得到的新树的权值是15。
     *       然后，将"树7"和"树8"从森林中删除，并将新的树(树15)添加到森林中。
     *
     * 第4步：在森林中，选择根节点权值最小的两棵树(11和15)来进行合并。得到的新树的权值是26。
     *       然后，将"树11"和"树15"从森林中删除，并将新的树(树26)添加到森林中。
     *
     * 第5步：在森林中，选择根节点权值最小的两棵树(15和26)来进行合并。得到的新树的权值是41。
     *        然后，将"树15"和"树26"从森林中删除，并将新的树(树41)添加到森林中。
     *
     * 此时，森林中只有一棵树(树41)。这棵树就是我们需要的哈夫曼树！
     *
     * 以{5,6,7,8,15}为例，来构造一棵哈夫曼树：
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/tree/huffman/03.jpg?raw=true
     */

    private static class HuffmanNode implements Comparable, Cloneable {
        protected int key;                // 权值
        protected HuffmanNode left;        // 左孩子
        protected HuffmanNode right;    // 右孩子
        protected HuffmanNode parent;    // 父结点

        protected HuffmanNode(int key, HuffmanNode left, HuffmanNode right, HuffmanNode parent) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public Object clone() {
            Object obj = null;

            try {
                obj = (HuffmanNode) super.clone();//Object 中的clone()识别出你要复制的是哪一个对象。
            } catch (CloneNotSupportedException e) {
                System.out.println(e.toString());
            }

            return obj;
        }

        @Override
        public int compareTo(Object obj) {
            return this.key - ((HuffmanNode) obj).key;
        }
    }

    /**
     * 最小堆(Huffman.java的辅助类)
     */
    private static class MinHeap {

        private List<HuffmanNode> mHeap;        // 存放堆的数组

        /*
         * 创建最小堆
         *
         * 参数说明：
         *     a -- 数据所在的数组
         */
        protected MinHeap(int a[]) {
            mHeap = new ArrayList<>();
            // 初始化数组
            for (int i = 0; i < a.length; i++) {
                HuffmanNode node = new HuffmanNode(a[i], null, null, null);
                mHeap.add(node);
            }

            // 从(size/2-1) --> 0逐次遍历。遍历之后，得到的数组实际上是一个最小堆。
            for (int i = a.length / 2 - 1; i >= 0; i--)
                filterdown(i, a.length - 1);
        }

        /*
         * 最小堆的向下调整算法
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
         *     end   -- 截至范围(一般为数组中最后一个元素的索引)
         */
        protected void filterdown(int start, int end) {
            int c = start;        // 当前(current)节点的位置
            int l = 2 * c + 1;    // 左(left)孩子的位置
            HuffmanNode tmp = mHeap.get(c);    // 当前(current)节点

            while (l <= end) {
                // "l"是左孩子，"l+1"是右孩子
                if (l < end && (mHeap.get(l).compareTo(mHeap.get(l + 1)) > 0))
                    l++;        // 左右两孩子中选择较小者，即mHeap[l+1]

                int cmp = tmp.compareTo(mHeap.get(l));
                if (cmp <= 0)
                    break;        //调整结束
                else {
                    mHeap.set(c, mHeap.get(l));
                    c = l;
                    l = 2 * l + 1;
                }
            }
            mHeap.set(c, tmp);
        }

        /*
         * 最小堆的向上调整算法(从start开始向上直到0，调整堆)
         *
         * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
         *
         * 参数说明：
         *     start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
         */
        protected void filterup(int start) {
            int c = start;            // 当前节点(current)的位置
            int p = (c - 1) / 2;        // 父(parent)结点的位置
            HuffmanNode tmp = mHeap.get(c);    // 当前(current)节点

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

        /*
         * 将node插入到二叉堆中
         */
        protected void insert(HuffmanNode node) {
            int size = mHeap.size();

            mHeap.add(node);    // 将"数组"插在表尾
            filterup(size);        // 向上调整堆
        }

        /*
         * 交换两个HuffmanNode节点的全部数据
         */
        private void swapNode(int i, int j) {
            HuffmanNode tmp = mHeap.get(i);
            mHeap.set(i, mHeap.get(j));
            mHeap.set(j, tmp);
        }

        /*
         * 新建一个节点，并将最小堆中最小节点的数据复制给该节点。
         * 然后除最小节点之外的数据重新构造成最小堆。
         *
         * 返回值：
         *     失败返回null。
         */
        protected HuffmanNode dumpFromMinimum() {
            int size = mHeap.size();

            // 如果"堆"已空，则返回
            if (size == 0)
                return null;

            // 将"最小节点"克隆一份，将克隆得到的对象赋值给node
            HuffmanNode node = (HuffmanNode) mHeap.get(0).clone();

            // 交换"最小节点"和"最后一个节点"
            mHeap.set(0, mHeap.get(size - 1));
            // 删除最后的元素
            mHeap.remove(size - 1);

            if (mHeap.size() > 1)
                filterdown(0, mHeap.size() - 1);

            return node;
        }

        // 销毁最小堆
        protected void destroy() {
            mHeap.clear();
            mHeap = null;
        }
    }

    public static class Huffman {

        private HuffmanNode mRoot;    // 根结点

        /*
         * 创建Huffman树
         *
         * @param 权值数组
         */
        public Huffman(int a[]) {
            HuffmanNode parent = null;
            MinHeap heap;

            // 建立数组a对应的最小堆
            heap = new MinHeap(a);

            for (int i = 0; i < a.length - 1; i++) {
                HuffmanNode left = heap.dumpFromMinimum();  // 最小节点是左孩子
                HuffmanNode right = heap.dumpFromMinimum(); // 其次才是右孩子

                // 新建parent节点，左右孩子分别是left/right；
                // parent的大小是左右孩子之和
                parent = new HuffmanNode(left.key + right.key, left, right, null);
                left.parent = parent;
                right.parent = parent;

                // 将parent节点数据拷贝到"最小堆"中
                heap.insert(parent);
            }

            mRoot = parent;

            // 销毁最小堆
            heap.destroy();
        }

        /*
         * 前序遍历"Huffman树"
         */
        private void preOrder(HuffmanNode tree) {
            if (tree != null) {
                System.out.print(tree.key + " ");
                preOrder(tree.left);
                preOrder(tree.right);
            }
        }

        public void preOrder() {
            preOrder(mRoot);
        }

        /*
         * 中序遍历"Huffman树"
         */
        private void inOrder(HuffmanNode tree) {
            if (tree != null) {
                inOrder(tree.left);
                System.out.print(tree.key + " ");
                inOrder(tree.right);
            }
        }

        public void inOrder() {
            inOrder(mRoot);
        }


        /*
         * 后序遍历"Huffman树"
         */
        private void postOrder(HuffmanNode tree) {
            if (tree != null) {
                postOrder(tree.left);
                postOrder(tree.right);
                System.out.print(tree.key + " ");
            }
        }

        public void postOrder() {
            postOrder(mRoot);
        }

        /*
         * 销毁Huffman树
         */
        private void destroy(HuffmanNode tree) {
            if (tree == null)
                return;

            if (tree.left != null)
                destroy(tree.left);
            if (tree.right != null)
                destroy(tree.right);

            tree = null;
        }

        public void destroy() {
            destroy(mRoot);
            mRoot = null;
        }

        /*
         * 打印"Huffman树"
         *
         * key        -- 节点的键值
         * direction  --  0，表示该节点是根节点;
         *               -1，表示该节点是它的父结点的左孩子;
         *                1，表示该节点是它的父结点的右孩子。
         */
        private void print(HuffmanNode tree, int key, int direction) {
            if (tree != null) {
                if (direction == 0)    // tree是根节点
                    System.out.printf("%2d is root\n", tree.key);
                else                // tree是分支节点
                    System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction == 1 ? "right" : "left");

                print(tree.left, tree.key, -1);
                print(tree.right, tree.key, 1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }
    }

    private static void testHuffmanTree() {
        final int a[] = {5, 6, 8, 7, 15};
        int i;
        Huffman tree;

        System.out.print("== 添加数组: ");
        for (i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");

        // 创建数组a对应的Huffman树
        tree = new Huffman(a);

        System.out.print("\n== 前序遍历: ");
        tree.preOrder();

        System.out.print("\n== 中序遍历: ");
        tree.inOrder();

        System.out.print("\n== 后序遍历: ");
        tree.postOrder();
        System.out.println();

        System.out.println("== 树的详细信息: ");
        tree.print();

        // 销毁二叉树
        tree.destroy();
    }

    /**
     * 7. 二叉树的遍历方式：
     *
     * -- 1、深度优先：递归，非递归实现方式
     *
     * 　　1)先序遍历：先访问根节点，再依次访问左子树和右子树
     *
     * 　　2)中序遍历：先访问左子树，再访问根节点吗，最后访问右子树
     *
     * 　　3)后序遍历：先访问左子树，再访问右子树，最后访问根节点
     *
     * -- 2、广度优先
     *
     *     按照树的深度，一层一层的访问树的节点
     */

    public static class BinaryTree {

        // 二叉树节点
        public static class BinaryTreeNode {
            int value;
            BinaryTreeNode left;
            BinaryTreeNode right;

            public BinaryTreeNode(int value) {
                this.value = value;
            }

            public BinaryTreeNode(int value, BinaryTreeNode left,
                                  BinaryTreeNode right) {
                super();
                this.value = value;
                this.left = left;
                this.right = right;
            }

        }

        // 访问树的节点
        public static void visit(BinaryTreeNode node) {
            System.out.println(node.value);
        }

        /**
         * 递归实现二叉树的先序遍历
         */
        public static void preOrder(BinaryTreeNode node) {
            if (node != null) {
                visit(node);
                preOrder(node.left);
                preOrder(node.right);
            }
        }

        /**
         * 递归实现二叉树的中序遍历
         */
        public static void inOrder(BinaryTreeNode node) {
            if (node != null) {
                inOrder(node.left);
                visit(node);
                inOrder(node.right);
            }
        }

        /**
         * 递归实现二叉树的后序遍历
         */
        public static void postOrder(BinaryTreeNode node) {
            if (node != null) {
                postOrder(node.left);
                postOrder(node.right);
                visit(node);
            }
        }

        /**
         * 非递归实现二叉树的先序遍历
         */
        public static void iterativePreorder(BinaryTreeNode node) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            if (node != null) {
                stack.push(node);
                while (!stack.empty()) {
                    node = stack.pop();
                    // 先访问节点
                    visit(node);
                    // 把右子结点压入栈
                    if (node.right != null) {
                        stack.push(node.right);
                    }
                    // 把左子结点压入栈
                    if (node.left != null) {
                        stack.push(node.left);
                    }
                }
            }
        }

        /**
         * 非递归实现二叉树的中序遍历
         */
        public static void iterativeInOrder(BinaryTreeNode root) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            BinaryTreeNode node = root;
            while (node != null || stack.size() > 0) {
                // 把当前节点的所有左侧子结点压入栈
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                // 访问节点，处理该节点的右子树
                if (stack.size() > 0) {
                    node = stack.pop();
                    visit(node);
                    node = node.right;
                }
            }
        }

        /**
         * 非递归使用单栈实现二叉树后序遍历
         */
        public static void iterativePostOrder(BinaryTreeNode root) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            BinaryTreeNode node = root;
            // 访问根节点时判断其右子树是够被访问过
            BinaryTreeNode preNode = null;
            while (node != null || stack.size() > 0) {
                // 把当前节点的左侧节点全部入栈
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                if (stack.size() > 0) {
                    BinaryTreeNode temp = stack.peek().right;
                    // 一个根节点被访问的前提是：无右子树或右子树已被访问过
                    if (temp == null || temp == preNode) {
                        node = stack.pop();
                        visit(node);
                        preNode = node;// 记录刚被访问过的节点
                        node = null;
                    } else {
                        // 处理右子树
                        node = temp;
                    }
                }
            }
        }

        /**
         * 非递归使用双栈实现二叉树后序遍历
         */
        public static void iterativePostOrderByTwoStacks(BinaryTreeNode root) {
            Stack<BinaryTreeNode> stack = new Stack<>();
            Stack<BinaryTreeNode> temp = new Stack<>();
            BinaryTreeNode node = root;
            while (node != null || stack.size() > 0) {
                // 把当前节点和其右侧子结点推入栈
                while (node != null) {
                    stack.push(node);
                    temp.push(node);
                    node = node.right;
                }
                // 处理栈顶节点的左子树
                if (stack.size() > 0) {
                    node = stack.pop();
                    node = node.left;
                }
            }
            while (temp.size() > 0) {
                node = temp.pop();
                visit(node);
            }
        }

        /**
         * 二叉树广度优先遍历——层序遍历 （广度优先）
         */
        public static void layerTraversal(BinaryTreeNode root) {
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
                while (!queue.isEmpty()) {
                    BinaryTreeNode currentNode = queue.poll();
                    visit(currentNode);
                    if (currentNode.left != null) {
                        queue.add(currentNode.left);
                    }
                    if (currentNode.right != null) {
                        queue.add(currentNode.right);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // 平衡二叉树
        // testAVLTree();
        // 伸展树(二叉查找树)
        // testSplayTree();
        // 红黑树(二叉查找树)
        // testRBTree();
        // 哈夫曼树
        // testHuffmanTree();
    }
}
