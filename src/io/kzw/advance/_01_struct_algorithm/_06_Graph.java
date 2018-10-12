package io.kzw.advance._01_struct_algorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * 图相关.
 *
 * @author kzw on 2018/09/17.
 */
public final class _06_Graph {

    /*
     * ###### 图的基本概念
     *
     * 1. 图的定义
     *
     * 定义：图(graph)是由一些点(vertex)和这些点之间的连线(edge)所组成的；
     * 其中，点通常被成为"顶点(vertex)"，而点与点之间的连线则被成为"边或弧"(edege)。通常记为，G=(V,E)。
     *
     * 2. 图的种类
     *
     * 根据边是否有方向，将图可以划分为：无向图和有向图。
     *
     * -----------------------------------------------------------------------------------------
     * 无向图：
     *
     *                 A
     *              /     \
     *             B ----- C ------- D
     *             |  \ /  |
     *             |  / \  |
     *             E ----- F
     *
     *                (G0)
     * 上面的图G0是无向图，无向图的所有的边都是不区分方向的。G0=(V1,{E1})。其中，
     *
     * (01) V1={A,B,C,D,E,F}。 V1表示由"A,B,C,D,E,F"几个顶点组成的集合。
     * (02) E1={(A,B),(A,C),(B,C),(B,E),(B,F),(C,F), (C,D),(E,F),(C,E)}。
     *      E1是由边(A,B),边(A,C)...等等组成的集合。其中，(A,C)表示由顶点A和顶点C连接成的边。
     *
     *
     *
     * 有向图：
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
     * 所有边都是有方向的。
     *
     * 上面的图G2是有向图。和无向图不同，有向图的所有的边都是有方向的！ G2=(V2,{A2})。其中，
     *
     * (01) V2={A,C,B,F,D,E,G}。 V2表示由"A,B,C,D,E,F,G"几个顶点组成的集合。
     * (02) A2={<A,B>,<B,C>,<B,F>,<B,E>,<C,E>,<E,D>,<D,C>,<E,B>,<F,G>}。
     *
     * E1是由矢量<A,B>,矢量<B,C>...等等组成的集合。其中，矢量<A,B)表示由"顶点A"指向"顶点C"的有向边。
     * -----------------------------------------------------------------------------------------
     *
     *
     * 3. 邻接点和度
     *
     * 3.1 邻接点
     * 一条边上的两个顶点叫做邻接点。
     * 例如，上面无向图G0中的顶点A和顶点C就是邻接点。
     *
     * 在有向图中，除了邻接点之外；还有"入边"和"出边"的概念。
     * 顶点的入边，是指以该顶点为终点的边。而顶点的出边，则是指以该顶点为起点的边。
     * 例如，上面有向图G2中的B和E是邻接点；<B,E>是B的出边，还是E的入边。
     *
     * 3.2 度
     *
     * 在无向图中，某个顶点的度是邻接到该顶点的边(或弧)的数目。
     * 例如，上面无向图G0中顶点A的度是2。
     *
     * 在有向图中，度还有"入度"和"出度"之分。
     * 某个顶点的入度，是指以该顶点为终点的边的数目。而顶点的出度，则是指以该顶点为起点的边的数目。
     * 顶点的度 = 入度 + 出度。
     * 例如，上面有向图G2中，顶点B的入度是2，出度是3；顶点B的度 = 2 + 3 = 5。
     *
     * 4. 路径和回路
     *
     * 路径：如果顶点(Vm)到顶点(Vn)之间存在一个顶点序列。则表示Vm到Vn是一条路径。
     * 路径长度：路径中"边的数量"。
     * 简单路径：若一条路径上顶点不重复出现，则是简单路径。
     * 回路：若路径的第一个顶点和最后一个顶点相同，则是回路。
     * 简单回路：第一个顶点和最后一个顶点相同，其它各顶点都不重复的回路则是简单回路。
     *
     * 5. 连通图和连通分量
     *
     * 连通图：对无向图而言，任意两个顶点之间都存在一条无向路径，则称该无向图为 连通图。
     * 对有向图而言，若图中任意两个顶点之间都存在一条有向路径，则称该有向图为 强连通图。
     * 连通分量：非连通图中的各个连通子图称为该图的 连通分量。
     *
     * 6. 权
     *
     * 在学习"哈夫曼树"的时候，了解过"权"的概念。图中权的概念与此类似。
     *
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/basic/03.jpg?raw=true
     *
     * 上面就是一个带权的图，即每个顶点都有值的图。
     */

    /*
     * ###### 图的存储结构
     *
     * 上面了解了"图的基本概念"，下面开始介绍图的存储结构。
     *
     * 图的存储结构，常用的是"邻接矩阵"和"邻接表"。
     *
     * 1. 邻接矩阵
     * 邻接矩阵是指用矩阵来表示图。它是采用矩阵来描述图中顶点之间的关系(及弧或边的权)。
     * 假设图中顶点数为n，则邻接矩阵定义为：
     *
     *            --  1 (若Vi与Vj之间有弧或边存在)
     *           |
     * A[i][j] = |
     *            --  0 (若Vi与Vj之间没有弧或边存在)
     *
     * 下面通过示意图来进行解释：
     *
     * ---------------------------------------------------------------------------
     *   A ------ F ------ G ------ E
     *   |   \
     *   |    \
     *   C ------ D
     *   |
     *   |
     *   B
     *
     *   注意：跨越顶点的不叫边，叫路径。
     *
     *   无向图G1
     *
     *
     *      A    B    C    D    E    F     G
     *    ————————————————————————————————————
     * A |  0    0    1    1    0    1     0
     *   |
     * B |  0    0    1    0    0    0     0
     *   |
     * C |  1    1    0    1    0    0     0
     *   |
     * D |  1    0    1    0    0    0     0
     *   |
     * E |  0    0    0    0    0    0     1
     *   |
     * F |  1    0    0    0    0    0     1
     *   |
     * G |  0    0    0    0    1    1     0
     *
     * G1的邻接矩阵
     *
     * 图中的G1是无向图和它对应的邻接矩阵。
     *
     * ---------------------------------------------------------------------------
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
     *  有向图G2
     *
     *
     *      A    B    C    D    E    F     G
     *    ————————————————————————————————————
     * A |  0    1    0    0    0    0     0
     *   |
     * B |  0    0    1    0    1    1     0
     *   |
     * C |  0    0    0    0    1    0     0
     *   |
     * D |  0    0    1    0    0    0     0
     *   |
     * E |  0    1    0    1    0    0     0
     *   |
     * F |  0    0    0    0    0    0     1
     *   |
     * G |  0    0    0    0    0    0     0
     *
     *  G2的邻接矩阵
     *
     * 图中的G2是有向图和它对应的邻接矩阵。
     *
     * 通常采用两个数组来实现邻接矩阵：一个一维数组用来保存顶点信息，一个二维数组来用保存边的信息。
     * 邻接矩阵的缺点就是比较耗费空间。
     * ---------------------------------------------------------------------------
     *
     *
     * 2. 邻接表
     * 邻接表是图的一种链式存储表示方法。它是改进后的"邻接矩阵"，
     * 它的缺点是不方便判断两个顶点之间是否有边，但是相对邻接矩阵来说更省空间。
     *
     * ---------------------------------------------------------------------------
     *   A ------ F ------ G ------ E
     *   |   \
     *   |    \
     *   C ------ D
     *   |
     *   |
     *   B
     *
     *  无向图G1
     *
     *  0 [A] ->  2 -> 3 -> 5
     *  1 [B] ->  2
     *  2 [C] ->  0 -> 1 -> 3
     *  3 [D] ->  0 -> 2
     *  4 [E] ->  6
     *  5 [F] ->  0 -> 6
     *  6 [G] ->  4 -> 5
     *
     *  G1的邻接表
     *
     * 图中的G1是无向图和它对应的邻接表。
     *
     * ---------------------------------------------------------------------------
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
     *  有向图G2
     *
     *  0 [A] ->  1
     *  1 [B] ->  2 -> 4 -> 5
     *  2 [C] ->  4
     *  3 [D] ->  2
     *  4 [E] ->  1 -> 3
     *  5 [F] ->  6
     *  6 [G]
     *
     *  G2的邻接表
     *
     * 图中的G2是有向图和它对应的邻接表。
     * ---------------------------------------------------------------------------
     */

    /**
     * 邻接矩阵无/有向图的实现.
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
            for (int i = 0; i < vlen; i++) {
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
            // 辅组队列
            int[] queue = new int[mVexs.length];
            // 顶点访问标记
            boolean[] visited = new boolean[mVexs.length];
            for (int i = 0; i < mVexs.length; i++)
                visited[i] = false;

            System.out.printf("BFS: ");
            for (int i = 0; i < mVexs.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    System.out.printf("%c ", mVexs[i]);
                    // 入队列
                    queue[rear++] = i;
                }

                while (head != rear) {
                    // 出队列
                    int j = queue[head++];
                    // k是为访问的邻接顶点
                    for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) {
                        if (!visited[k]) {
                            visited[k] = true;
                            System.out.printf("%c ", mVexs[k]);
                            queue[rear++] = k;
                        }
                    }
                }
            }
            System.out.printf("\n");
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

    /**
     * 邻接表无/有向图的实现.
     */
    public static class ListGraphic {

        // 邻接表中表的顶点
        private class VNode {

            // 顶点信息
            char data;
            // 指向第一条依附该顶点的弧
            ENode firstEdge;
        }

        // 邻接表中表对应的链表的顶点
        private class ENode {

            // 该边所指向的顶点的位置
            int ivex;
            // 指向下一条弧的指针
            ENode nextEdge;
        }

        // 顶点数组
        private VNode[] mVexs;

        /**
         * 创建图(自己输入数据)
         *
         * @param dg 是否有向
         */
        public ListGraphic(boolean dg) {
            // 输入"顶点数"和"边数"
            System.out.printf("input vertex number: ");
            int vlen = readInt();
            System.out.printf("input edge number: ");
            int elen = readInt();
            if (vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
                System.out.printf("input error: invalid parameters!\n");
                return;
            }
            // 初始化"顶点"
            mVexs = new VNode[vlen];
            for (int i = 0; i < vlen; i++) {
                System.out.printf("vertex(%d): ", i);
                mVexs[i] = new VNode();
                mVexs[i].data = readChar();
                mVexs[i].firstEdge = null;
            }

            // 初始化"边"
            //mMatrix = new int[vlen][vlen];
            for (int i = 0; i < elen; i++) {
                // 读取边的起始顶点和结束顶点
                System.out.printf("edge(%d):", i);
                char c1 = readChar();
                char c2 = readChar();
                int p1 = getPosition(c1);
                int p2 = getPosition(c2);
                // 初始化node1
                ENode node1 = new ENode();
                node1.ivex = p2;
                // 将node1链接到"p1所在链表的末尾"
                if (mVexs[p1].firstEdge == null)
                    mVexs[p1].firstEdge = node1;
                else
                    linkLast(mVexs[p1].firstEdge, node1);

                if (!dg) {
                    // 初始化node2
                    ENode node2 = new ENode();
                    node2.ivex = p1;
                    // 将node2链接到"p2所在链表的末尾"
                    if (mVexs[p2].firstEdge == null)
                        mVexs[p2].firstEdge = node2;
                    else
                        linkLast(mVexs[p2].firstEdge, node2);
                }
            }
        }

        /**
         * 创建图(用已提供的矩阵)
         *
         * @param vexs  顶点数组
         * @param edges 边数组
         * @param dg    是否有向
         */
        public ListGraphic(char[] vexs, char[][] edges, boolean dg) {
            // 初始化"顶点数"和"边数"
            int vlens = vexs.length;
            int elens = edges.length;
            mVexs = new VNode[vlens];
            for (int i = 0; i < vlens; i++) {
                mVexs[i] = new VNode();
                mVexs[i].data = vexs[i];
                mVexs[i].firstEdge = null;
            }
            // 初始化"边"
            for (int i = 0; i < elens; i++) {
                // 读取边的起始顶点和结束顶点
                char c1 = edges[i][0];
                char c2 = edges[i][1];
                // 读取边的起始顶点和结束顶点
                int p1 = getPosition(c1);
                int p2 = getPosition(c2);
                // 初始化node1
                ENode node1 = new ENode();
                node1.ivex = p2;
                if (mVexs[p1].firstEdge == null) {
                    mVexs[p1].firstEdge = node1;
                } else {
                    linkLast(mVexs[p1].firstEdge, node1);
                }
                if (!dg) {
                    // 初始化node2
                    ENode node2 = new ENode();
                    node2.ivex = p1;
                    if (mVexs[p2].firstEdge == null) {
                        mVexs[p2].firstEdge = node2;
                    } else {
                        linkLast(mVexs[p2].firstEdge, node2);
                    }
                }
            }
        }

        /**
         * 将node节点链接到list的最后
         */
        private void linkLast(ENode list, ENode node) {
            ENode p = list;

            while (p.nextEdge != null)
                p = p.nextEdge;
            p.nextEdge = node;
        }

        /**
         * 返回ch位置
         */
        private int getPosition(char ch) {
            for (int i = 0; i < mVexs.length; i++)
                if (mVexs[i].data == ch)
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
            System.out.printf("List Graph:\n");
            for (int i = 0; i < mVexs.length; i++) {
                System.out.printf("%d(%c): ", i, mVexs[i].data);
                ENode node = mVexs[i].firstEdge;
                while (node != null) {
                    System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                    node = node.nextEdge;
                }
                System.out.printf("\n");
            }
        }

        /**
         * 深度优先搜索遍历图的递归实现
         */
        private void DFS(int i, boolean[] visited) {
            ENode node;
            visited[i] = true;

            System.out.printf("%c ", mVexs[i].data);
            node = mVexs[i].firstEdge;
            while (node != null) {
                if (!visited[node.ivex])
                    DFS(node.ivex, visited);
                node = node.nextEdge;
            }
        }

        /**
         * 深度优先搜索遍历图
         */
        public void DFS() {
            // 顶点访问标记
            boolean[] visited = new boolean[mVexs.length];

            // 初始化所有顶点都没有被访问
            for (int i = 0; i < mVexs.length; i++)
                visited[i] = false;

            System.out.printf("DFS: ");
            for (int i = 0; i < mVexs.length; i++) {
                if (!visited[i])
                    DFS(i, visited);
            }
            System.out.printf("\n");
        }

        /**
         * 广度优先搜索（类似于树的层次遍历）
         */
        public void BFS() {
            int head = 0;
            int rear = 0;
            // 辅组队列
            int[] queue = new int[mVexs.length];
            // 顶点访问标记
            boolean[] visited = new boolean[mVexs.length];

            for (int i = 0; i < mVexs.length; i++)
                visited[i] = false;

            System.out.printf("BFS: ");
            for (int i = 0; i < mVexs.length; i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    System.out.printf("%c ", mVexs[i].data);
                    // 入队列
                    queue[rear++] = i;
                }

                while (head != rear) {
                    // 出队列
                    int j = queue[head++];
                    ENode node = mVexs[j].firstEdge;
                    while (node != null) {
                        int k = node.ivex;
                        if (!visited[k]) {
                            visited[k] = true;
                            System.out.printf("%c ", mVexs[k].data);
                            queue[rear++] = k;
                        }
                        node = node.nextEdge;
                    }
                }
            }
            System.out.printf("\n");
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
            ListGraphic pG;

            // 自定义"图"(输入矩阵队列)
            //pG = new ListUDG();
            // 采用已有的"图"
            pG = new ListGraphic(vexs, edges, dg);

            // 打印图
            pG.print();
            // 深度优先遍历
            pG.DFS();
            // 广度优先遍历
            pG.BFS();
        }
    }

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
     * 第1步：。
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

    /**
     * 代码详见 {@link MatrixGraphic#DFS()}, {@link MatrixGraphic#BFS()},
     * {@link ListGraphic#DFS()}, {@link ListGraphic#BFS()} 的深度优先搜索和广度优先搜索方法。
     */

    /*
     * ###### 拓扑排序
     *
     * 拓扑排序介绍
     *
     * 拓扑排序(Topological Order)是指，将一个有向无环图(Directed Acyclic Graph简称DAG)进行排序进而得到一个有序的线性序列。
     * 这样说，可能理解起来比较抽象。下面通过简单的例子进行说明！
     *
     * 例如，一个项目包括A、B、C、D四个子部分来完成，并且A依赖于B和D，C依赖于D。现在要制定一个计划，写出A、B、C、D的执行顺序。
     * 这时，就可以利用到拓扑排序，它就是用来确定事物发生的顺序的。
     * 在拓扑排序中，如果存在一条从顶点A到顶点B的路径，那么在排序结果中B出现在A的后面。
     *
     *
     * 拓扑排序解决的是一系列相互依赖的事件的排序问题，比如Ant中有很多的Task，而某些Task依赖于另外的Task，
     * 编译之前需要清理空间，打包之前要先编译，但其它一些Task处理顺序可以调换(是无所谓前后，不是并行),
     * 如何安排Task的执行顺序就可以用拓扑排序解决。
     *
     * 熟悉Java的朋友应该都知道Spring，一个非常优秀的解决组件(Bean)依赖的框架，组件之间可能有依赖关系，
     * 也可能没有关系，如何按顺序创建组件也是相同的问题。
     *
     * 本文使用的是图搜索算法里面的深度优先排序算法解决问题。
     *
     * 需要特别指出的是拓扑排序算法的结果很可能有多个(不依赖的可以调换位置)，而算法也可以有多种，
     * 深度优先排序算法只是其中一种而已。拓扑排序为线性排序，效率为O(|V|+|E|)，其中|V|表示顶点数，|E|表示边的数量。
     *
     * ------------------------------------------------------------------------------------------------
     * 拓扑排序算法的基本步骤：
     *
     * 1. 构造一个队列Q(queue) 和 拓扑排序的结果队列T(topological)；
     * 2. 把所有没有依赖顶点的节点放入Q；
     * 3. 当Q还有顶点的时候，执行下面步骤：
     * 3.1 从Q中取出一个顶点n(将n从Q中删掉)，并放入T(将n加入到结果集中)；
     * 3.2 对n每一个邻接点m(n是起点，m是终点)；
     * 3.2.1 去掉边<n,m>;
     * 3.2.2 如果m没有依赖顶点，则把m放入Q;
     *
     * 注：顶点A没有依赖顶点，是指不存在以A为终点的边。
     *
     * B -------> A --------> G
     * |                      ^
     * |                      |
     * |                      |
     * ↓                      |
     * D -------> F <-------- C
     * |
     * |
     * |
     * ↓
     * E
     *
     *           (G3)
     *
     * 以上图为例，来对拓扑排序进行演示。
     *
     * 第1步：       B(*)        A ---------> G
     *
     *
     *
     *
     *               D --------> F           C(*)
     *               |
     *               |
     *               |
     *               ↓
     *               E
     *
     * 结果T: B -> C
     *
     * ++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * 第2步：      B(*)       A(*)         G
     *
     *
     *
     *             D(*)       F            C(*)
     *
     *
     *
     *
     *             E
     *
     *  结果T: B -> C -> A -> D
     *
     * ++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * 第3步：     B(*)        A(*)         G(*)
     *
     *
     *            D(*)        F(*)         C(*)
     *
     *
     *            E(*)
     *
     *  结果T: B -> C -> A -> D -> C -> A -> D
     *
     *
     * 第1步：将B和C加入到排序结果中。
     *       顶点B和顶点C都是没有依赖顶点，因此将C和C加入到结果集T中。
     *       假设ABCDEFG按顺序存储，因此先访问B，再访问C。访问B之后，去掉边<B,A>和<B,D>，并将A和D加入到队列Q中。
     *       同样的，去掉边<C,F>和<C,G>，并将F和G加入到Q中。
     *       (01) 将B加入到排序结果中，然后去掉边<B,A>和<B,D>；此时，由于A和D没有依赖顶点，因此并将A和D加入到队列Q中。
     *       (02) 将C加入到排序结果中，然后去掉边<C,F>和<C,G>；此时，由于F有依赖顶点D，G有依赖顶点A，因此不对F和G进行处理。
     *
     * 第2步：将A,D依次加入到排序结果中。
     *       第1步访问之后，A,D都是没有依赖顶点的，根据存储顺序，先访问A，然后访问D。访问之后，删除顶点A和顶点D的出边。
     *
     * 第3步：将E,F,G依次加入到排序结果中。
     *
     * 因此访问顺序是：B -> C -> A -> D -> E -> F -> G
     * ------------------------------------------------------------------------------------------------
     */

    /**
     * 方法1：基于减治法：寻找图中入度为0的顶点作为即将遍历的顶点，遍历完后，将此顶点从图中删除
     */
    public static class TopologicalSorting1 {

        /**
         * 参数adjMatrix:给出图的邻接矩阵值
         * 参数source:给出图的每个顶点的入度值
         * 该函数功能：返回给出图的拓扑排序序列
         */
        public char[] getSourceSort(int[][] adjMatrix, int[] source) {
            // 给出图的顶点个数
            int len = source.length;
            // 定义最终返回路径字符数组
            char[] result = new char[len];
            // 用于计算当前遍历的顶点个数
            int count = 0;
            boolean judge = true;
            while (judge) {
                for (int i = 0; i < source.length; i++) {
                    // 当第i个顶点入度为0时，遍历该顶点
                    if (source[i] == 0) {
                        result[count++] = (char) ('a' + i);
                        // 代表第i个顶点已被遍历
                        source[i] = -1;
                        // 寻找第i个顶点的出度顶点
                        for (int j = 0; j < adjMatrix[0].length; j++) {
                            if (adjMatrix[i][j] == 1)
                                // 第j个顶点的入度减1
                                source[j] -= 1;
                        }
                    }
                }
                if (count == len)
                    judge = false;
            }
            return result;
        }

        /**
         * 参数adjMatrix:给出图的邻接矩阵值
         * 函数功能：返回给出图每个顶点的入度值
         */
        public int[] getSource(int[][] adjMatrix) {
            int len = adjMatrix[0].length;
            int[] source = new int[len];
            for (int i = 0; i < len; i++) {
                // 若邻接矩阵中第i列含有m个1，则在该列的节点就包含m个入度，即source[i] = m
                int count = 0;
                for (int j = 0; j < len; j++) {
                    if (adjMatrix[j][i] == 1)
                        count++;
                }
                source[i] = count;
            }
            return source;
        }

        public static void test() {
            TopologicalSorting1 test = new TopologicalSorting1();
            int[][] adjMatrix = {{0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}};
            int[] source = test.getSource(adjMatrix);
            System.out.println("给出图的所有节点（按照字母顺序排列）的入度值：");
            for (int i = 0; i < source.length; i++)
                System.out.print(source[i] + "\t");
            System.out.println();
            char[] result = test.getSourceSort(adjMatrix, source);

            System.out.println("给出图的拓扑排序结果：");
            for (int i = 0; i < result.length; i++)
                System.out.print(result[i] + "\t");
        }
    }

    /**
     * 方法2：基于深度优先查找法（DFS）获取拓扑排序
     */
    public static class TopologicalSorting2 {

        public int count1 = 0;
        public Stack<Character> result1;

        /**
         * adjMatrix是待遍历图的邻接矩阵
         * value是待遍历图顶点用于是否被遍历的判断依据，0代表未遍历，非0代表已被遍历
         */
        public void dfs(int[][] adjMatrix, int[] value) {
            result1 = new Stack<>();
            for (int i = 0; i < value.length; i++) {
                if (value[i] == 0)
                    dfsVisit(adjMatrix, value, i);
            }
        }

        /**
         * adjMatrix是待遍历图的邻接矩阵
         * value是待遍历图顶点用于是否被遍历的判断依据，0代表未遍历，非0代表已被遍历
         * number是当前正在遍历的顶点在邻接矩阵中的数组下标编号
         */
        public void dfsVisit(int[][] adjMatrix, int[] value, int number) {
            // 把++count1赋值给当前正在遍历顶点判断值数组元素，变为非0，代表已被遍历
            value[number] = ++count1;
            for (int i = 0; i < value.length; i++) {
                // 当，当前顶点的相邻有相邻顶点可行走且其为被遍历
                if (adjMatrix[number][i] == 1 && value[i] == 0)
                    // 执行递归，行走第i个顶点
                    dfsVisit(adjMatrix, value, i);
            }
            char temp = (char) ('a' + number);
            result1.push(temp);
        }

        public static void test() {
            TopologicalSorting2 test = new TopologicalSorting2();
            int[][] adjMatrix = {{0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}};

            int[] value = new int[5];
            test.dfs(adjMatrix, value);
            System.out.println();
            System.out.println("使用ＤＦＳ方法得到拓扑排序序列的逆序：");
            System.out.println(test.result1);
            System.out.println("使用ＤＦＳ方法得到拓扑排序序列：");
            while (!test.result1.empty())
                System.out.print(test.result1.pop() + "\t");
        }
    }

    /*
     * ###### 克鲁斯卡尔算法(Kruskal)
     *
     * -------------------------------------------------------------------------------------------------------------
     *
     * 1. 最小生成树:
     *
     * 在含有n个顶点的连通图中选择n-1条边，构成一棵极小连通子图，并使该连通子图中n-1条边上权值之和达到最小，则称其为连通网的最小生成树。
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/kruskal/01.jpg?raw=true
     *
     * 例如，对于如上图G4所示的连通网可以有多棵权值总和不相同的生成树。
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/kruskal/02.jpg?raw=true
     *
     * -------------------------------------------------------------------------------------------------------------
     *
     * 2. 克鲁斯卡尔算法介绍:
     * 克鲁斯卡尔(Kruskal)算法，是用来求加权连通图的最小生成树的算法。
     *
     * 基本思想：按照权值从小到大的顺序选择n-1条边，并保证这n-1条边不构成回路。
     * 具体做法：首先构造一个只含n个顶点的森林，然后依权值从小到大从连通网中选择边加入到森林中，并使森林中不产生回路，直至森林变成一棵树为止。
     *
     * 克鲁斯卡尔算法图解:
     *
     * 以上图G4为例，来对克鲁斯卡尔进行演示(假设，用数组R保存最小生成树结果)。
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/kruskal/03.jpg?raw=true
     *
     * 第1步：将边<E,F>加入R中。
     *       边<E,F>的权值最小，因此将它加入到最小生成树结果R中。
     *
     * 第2步：将边<C,D>加入R中。
     *       上一步操作之后，边<C,D>的权值最小，因此将它加入到最小生成树结果R中。
     *
     * 第3步：将边<D,E>加入R中。
     *       上一步操作之后，边<D,E>的权值最小，因此将它加入到最小生成树结果R中。
     *
     * 第4步：将边<B,F>加入R中。
     *       上一步操作之后，边<C,E>的权值最小，但<C,E>会和已有的边构成回路；
     *       因此，跳过边<C,E>。同理，跳过边<C,F>。将边<B,F>加入到最小生成树结果R中。
     *
     * 第5步：将边<E,G>加入R中。
     *       上一步操作之后，边<E,G>的权值最小，因此将它加入到最小生成树结果R中。
     *
     * 第6步：将边<A,B>加入R中。
     *       上一步操作之后，边<F,G>的权值最小，但<F,G>会和已有的边构成回路；
     *       因此，跳过边<F,G>。同理，跳过边<B,C>。将边<A,B>加入到最小生成树结果R中。
     *
     * 此时，最小生成树构造完成！它包括的边依次是：<E,F> <C,D> <D,E> <B,F> <E,G> <A,B>。
     *
     * -------------------------------------------------------------------------------------------------------------
     *
     * 3. 克鲁斯卡尔算法分析
     * 根据前面介绍的克鲁斯卡尔算法的基本思想和做法，我们能够了解到，克鲁斯卡尔算法重点需要解决的以下两个问题：
     *
     * 问题一 对图的所有边按照权值大小进行排序。
     * 问题二 将边添加到最小生成树中时，怎么样判断是否形成了回路。
     *
     * 问题一很好解决，采用排序算法进行排序即可。
     *
     * 问题二，处理方式是：记录顶点在"最小生成树"中的终点，顶点的终点是"在最小生成树中与它连通的最大顶点"
     * (关于这一点，后面会通过图片给出说明)。然后每次需要将一条边添加到最小生存树时，判断该边的两个顶点的终点是否重合，
     * 重合的话则会构成回路。 以下图来进行说明：
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/kruskal/04.jpg?raw=true
     *
     * 在将<E,F> <C,D> <D,E>加入到最小生成树R中之后，这几条边的顶点就都有了终点：
     * (01) C的终点是F。
     * (02) D的终点是F。
     * (03) E的终点是F。
     * (04) F的终点是F。
     *
     * 关于终点，就是将所有顶点按照从小到大的顺序排列好之后；某个顶点的终点就是"与它连通的最大顶点"。
     * 因此，接下来，虽然<C,E>是权值最小的边。但是C和E的重点都是F，即它们的终点相同，
     * 因此，将<C,E>加入最小生成树的话，会形成回路。这就是判断回路的方式。
     * -------------------------------------------------------------------------------------------------------------
     */

    public static class KruskalMinTree {

        static class Graph {
            Edge[] edges;

            int[][] arr;
        }

        static class Edge implements Comparable<Edge> {
            int begin;
            int end;
            // 边的权重
            int weight;

            @Override
            public int compareTo(Edge o) {
                return this.weight - o.weight;
            }
        }

        public static void kruskal(Graph graph) {
            // 从小到大按权值排好序的edges
            Edge[] edges = graph.edges;
            int[] parent = new int[7];
            // 顶点的边号初始化为 0-6
            for (int i = 0; i < 7; i++) {
                parent[i] = 0;
            }
            for (int i = 0; i < edges.length; i++) {
                // 拿到边
                Edge edge = edges[i];
                int rootOfBegin = findParentRoot(edge.begin, parent);
                int rootOfend = findParentRoot(edge.end, parent);
                if (rootOfBegin != rootOfend) {
                    System.out.println(String.format("(%d,%d)->%d", rootOfBegin, rootOfend, edge.weight));
                    parent[rootOfBegin] = rootOfend;
                }
            }
        }

        /**
         * parent数组用于构造MST判断是否存在环路判断的思想:
         * <p>
         * 1.初始化的时候，数组为[0,0,...,0]
         * <p>
         * 2.第一次循环进来的时候比如(begin=0,end=1,weight=5),由于数组全为0,故返回0和1,
         * 若begin和end的返回值不相等则设置parent[begin]=end,即设置了0的双亲节点是1,即把0节点和1节点加入MST
         * 当数组数据为[1,5,8,7,7,8,0,0,6]时
         * 表示[节点0连着1,1连着5,5连着8,8连着6,2连着8]以及[3连着7,4连着7]
         * 若在加入<5,6时>，通过数组找到相同的顶点的话就说明构成了一个环
         * <p>
         * 3.begin和end的返回值相等，则表示构成了环
         */
        private static int findParentRoot(int target, int[] parent) {
            while (parent[target] > 0) {
                target = parent[target];
            }
            return target;
        }

        public static void test() {
            // 初始化
            Graph graph = new Graph();
            int[][] arr = new int[7][7];
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i == j)
                        arr[i][j] = 0;
                    else {
                        arr[j][i] = Integer.MAX_VALUE;
                    }
                }
            }
            arr[0][1] = 7;
            arr[0][3] = 5;
            arr[1][2] = 8;
            arr[1][3] = 9;
            arr[1][4] = 7;
            arr[2][4] = 5;
            arr[3][4] = 15;
            arr[3][5] = 6;
            arr[4][5] = 8;
            arr[4][6] = 9;
            arr[5][6] = 11;
            for (int i = 0; i < 7; i++) {
                for (int j = i; j < 7; j++) {
                    arr[j][i] = arr[i][j];
                }
            }
            graph.arr = arr;
            int k = 0;
            Edge[] edges = new Edge[11];
            for (int i = 0; i < edges.length; i++) {
                Edge edge = new Edge();
                edges[i] = edge;
            }
            for (int i = 0; i < 6; i++) {
                for (int j = i + 1; j < 7; j++) {
                    if (arr[i][j] < Integer.MAX_VALUE) {
                        edges[k].begin = i; // 编号较小的结点为首
                        edges[k].end = j; // 编号较大的结点为尾
                        edges[k].weight = arr[i][j];
                        k++;
                    }
                }
            }
            graph.edges = edges;
            Arrays.sort(edges);
            //初始化结束
            kruskal(graph);
        }
    }

    /*
     * ###### 普里姆(Prim)算法
     *
     * 普里姆(Prim)算法，和克鲁斯卡尔算法一样，是用来求加权连通图的最小生成树的算法。
     *
     * 1. 基本思想
     * 对于图G而言，V是所有顶点的集合；现在，设置两个新的集合U和T，其中U用于存放G的最小生成树中的顶点，T存放G的最小生成树中的边。
     * 从所有uЄU，vЄ(V-U) (V-U表示出去U的所有顶点)的边中选取权值最小的边(u, v)，将顶点v加入集合U中，将边(u, v)加入集合T中，
     * 如此不断重复，直到U=V为止，最小生成树构造完毕，这时集合T中包含了最小生成树中的所有边。
     *
     * 2. 普里姆算法图解
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/prim/01.jpg?raw=true
     *
     * 以上图G4为例，来对普里姆进行演示(从第一个顶点A开始通过普里姆算法生成最小生成树)。
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/prim/02.jpg?raw=true
     *
     * 初始状态：V是所有顶点的集合，即V={A,B,C,D,E,F,G}；U和T都是空！
     * 第1步：将顶点A加入到U中。
     * 此时，U={A}。
     *
     * 第2步：将顶点B加入到U中。
     * 上一步操作之后，U={A}, V-U={B,C,D,E,F,G}；因此，边(A,B)的权值最小。将顶点B添加到U中；此时，U={A,B}。
     *
     * 第3步：将顶点F加入到U中。
     * 上一步操作之后，U={A,B}, V-U={C,D,E,F,G}；因此，边(B,F)的权值最小。将顶点F添加到U中；此时，U={A,B,F}。
     *
     * 第4步：将顶点E加入到U中。
     * 上一步操作之后，U={A,B,F}, V-U={C,D,E,G}；因此，边(F,E)的权值最小。将顶点E添加到U中；此时，U={A,B,F,E}。
     *
     * 第5步：将顶点D加入到U中。
     * 上一步操作之后，U={A,B,F,E}, V-U={C,D,G}；因此，边(E,D)的权值最小。将顶点D添加到U中；此时，U={A,B,F,E,D}。
     *
     * 第6步：将顶点C加入到U中。
     * 上一步操作之后，U={A,B,F,E,D}, V-U={C,G}；因此，边(D,C)的权值最小。将顶点C添加到U中；此时，U={A,B,F,E,D,C}。
     *
     * 第7步：将顶点G加入到U中。
     * 上一步操作之后，U={A,B,F,E,D,C}, V-U={G}；因此，边(F,G)的权值最小。将顶点G添加到U中；此时，U=V。
     *
     * 此时，最小生成树构造完成！它包括的顶点依次是：A B F E D C G。
     */

    /**
     * 图的邻接矩阵表示
     */
    public static class MGraph {
        // 图中结点数目
        int vexs;
        // 存放结点数据
        char data[];
        // 存放边
        int[][] weight;

        public MGraph(int ve) {
            vexs = ve;
            data = new char[ve];
            weight = new int[ve][ve];
        }
    }

    public static class PrimMinTree {

        /**
         * 创建图的邻接矩阵
         */
        public void CreateGraph(MGraph graph, int vexs, char data[], int[][] weight) {
            int i, j;
            for (i = 0; i < vexs; i++) {
                graph.data[i] = data[i];
                for (j = 0; j < vexs; j++) {
                    graph.weight[i][j] = weight[i][j];
                }
            }
        }

        public void Prim(MGraph graph, int v) {
            // graph为图的邻接矩阵表示，v为起始顶点
            int visited[] = new int[graph.vexs];  // visited[]标记结点是否被访问过
            for (int i = 0; i < graph.vexs; i++) {   // 初始化visited[]
                visited[i] = 0;
            }

            visited[v] = 1;
            int h1 = -1, h2 = -1;   // 记录边的弧尾和弧头
            int minweight = 10000;// minweight记录最小权重
            for (int k = 1; k < graph.vexs; k++) {  // vexs个顶点，最小生成树中有vexs-1条边
                for (int i = 0; i < graph.vexs; i++) {  // i顶点表示被访问过的顶点
                    for (int j = 0; j < graph.vexs; j++) {  // j顶点表示未被访问过的顶点
                        if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minweight) {
                            // 寻找已访问的顶点与未访问的定点间的权值最小的边
                            minweight = graph.weight[i][j];
                            h1 = i;
                            h2 = j;
                        }
                    }
                }

                System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值：" + minweight);
                visited[h2] = 1;   // 标记h2被访问过
                minweight = 10000;
            }

        }

        public static void test() {
            char[] data = new char[]{'A', 'B', 'C', 'D', 'E'};
            int vexs = data.length;
            int[][] weight = new int[][]{
                    {10000, 4, 8, 5, 10000},
                    {4, 10000, 3, 10000, 7},
                    {8, 3, 10000, 6, 6},
                    {5, 10000, 6, 10000, 9},
                    {10000, 7, 6, 9, 10000}
            };

            MGraph graph = new MGraph(vexs);
            PrimMinTree mt = new PrimMinTree();
            mt.CreateGraph(graph, vexs, data, weight);
            mt.Prim(graph, 0);
        }
    }

    /*
     * ###### 迪杰斯特拉(Dijkstra)算法
     *
     * 迪杰斯特拉(Dijkstra)算法是典型最短路径算法，用于计算一个节点到其他节点的最短路径。
     * 它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)，直到扩展到终点为止。
     *
     * 1. 基本思想
     *
     * 通过Dijkstra计算图G中的最短路径时，需要指定起点s(即从顶点s开始计算)。
     * 此外，引进两个集合S和U。S的作用是记录已求出最短路径的顶点(以及相应的最短路径长度)，
     * 而U则是记录还未求出最短路径的顶点(以及该顶点到起点s的距离)。
     *
     * 初始时，S中只有起点s；U中是除s之外的顶点，并且U中顶点的路径是"起点s到该顶点的路径"。
     * 然后，从U中找出路径最短的顶点，并将其加入到S中；接着，更新U中的顶点和顶点对应的路径。
     * 然后，再从U中找出路径最短的顶点，并将其加入到S中；接着，更新U中的顶点和顶点对应的路径。 ... 重复该操作，直到遍历完所有顶点。
     *
     * 2. 操作步骤
     *
     * (1) 初始时，S只包含起点s；U包含除s外的其他顶点，且U中顶点的距离为"起点s到该顶点的距离"
     * [例如，U中顶点v的距离为(s,v)的长度，然后s和v不相邻，则v的距离为∞]。
     *
     * (2) 从U中选出"距离最短的顶点k"，并将顶点k加入到S中；同时，从U中移除顶点k。
     *
     * (3) 更新U中各个顶点到起点s的距离。之所以更新U中顶点的距离，是由于上一步中确定了k是求出最短路径的顶点，
     * 从而可以利用k来更新其它顶点的距离；例如，(s,v)的距离可能大于(s,k)+(k,v)的距离。
     *
     * (4) 重复步骤(2)和(3)，直到遍历完所有顶点。
     * 单纯的看上面的理论可能比较难以理解，下面通过实例来对该算法进行说明。
     *
     * 3. 迪杰斯特拉算法图解
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/dijkstra/01.jpg?raw=true
     *
     * 以上图G4为例，来对迪杰斯特拉进行算法演示(以第4个顶点D为起点)。
     * https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/pictures/graph/dijkstra/02.jpg?raw=true
     *
     * 初始状态：S是已计算出最短路径的顶点集合，U是未计算除最短路径的顶点的集合！
     *
     * 第1步：将顶点D加入到S中。
     * 此时，S={D(0)}, U={A(∞),B(∞),C(3),E(4),F(∞),G(∞)}。     注:C(3)表示C到起点D的距离是3。
     *
     * 第2步：将顶点C加入到S中。
     * 上一步操作之后，U中顶点C到起点D的距离最短；因此，将C加入到S中，同时更新U中顶点的距离。
     * 以顶点F为例，之前F到D的距离为∞；但是将C加入到S之后，F到D的距离为9=(F,C)+(C,D)。
     * 此时，S={D(0),C(3)}, U={A(∞),B(23),E(4),F(9),G(∞)}。
     *
     * 第3步：将顶点E加入到S中。
     * 上一步操作之后，U中顶点E到起点D的距离最短；因此，将E加入到S中，同时更新U中顶点的距离。还是以顶点F为例，
     * 之前F到D的距离为9；但是将E加入到S之后，F到D的距离为6=(F,E)+(E,D)。
     * 此时，S={D(0),C(3),E(4)}, U={A(∞),B(23),F(6),G(12)}。
     *
     * 第4步：将顶点F加入到S中。
     * 此时，S={D(0),C(3),E(4),F(6)}, U={A(22),B(13),G(12)}。
     *
     * 第5步：将顶点G加入到S中。
     * 此时，S={D(0),C(3),E(4),F(6),G(12)}, U={A(22),B(13)}。
     *
     * 第6步：将顶点B加入到S中。
     * 此时，S={D(0),C(3),E(4),F(6),G(12),B(13)}, U={A(22)}。
     *
     * 第7步：将顶点A加入到S中。
     * 此时，S={D(0),C(3),E(4),F(6),G(12),B(13),A(22)}。
     *
     * 此时，起点D到各个顶点的最短距离就计算出来了：A(22) B(13) C(3) D(0) E(4) F(6) G(12)。
     */

    public static class Dijkstra {

        // 此路不通
        static int M = 10000;

        public static void test() {
            // 邻接矩阵
            int[][] weight1 = {
                    {0, 3, 2000, 7, M},
                    {3, 0, 4, 2, M},
                    {M, 4, 0, 5, 4},
                    {7, 2, 5, 0, 6},
                    {M, M, 4, 6, 0}
            };


            int[][] weight2 = {
                    {0, 10, M, 30, 100},
                    {M, 0, 50, M, M},
                    {M, M, 0, M, 10},
                    {M, M, 20, 0, 60},
                    {M, M, M, M, 0}
            };
            int start = 0;
            int[] shortPath = Dijsktra(weight2, start);

            for (int i = 0; i < shortPath.length; i++)
                System.out.println("从" + start + "出发到" + i + "的最短距离为：" + shortPath[i]);
        }

        public static int[] Dijsktra(int[][] weight, int start) {
            // 接受一个有向图的权重矩阵，和一个起点编号start（从0编号，顶点存在数组中）
            // 返回一个int[] 数组，表示从start到它的最短路径长度
            int n = weight.length;        // 顶点个数
            int[] shortPath = new int[n];    // 存放从start到其他各点的最短路径
            String[] path = new String[n]; // 存放从start到其他各点的最短路径的字符串表示
            for (int i = 0; i < n; i++)
                path[i] = new String(start + "-->" + i);
            int[] visited = new int[n];   // 标记当前该顶点的最短路径是否已经求出,1表示已求出

            // 初始化，第一个顶点求出
            shortPath[start] = 0;
            visited[start] = 1;

            // 要加入n-1个顶点
            for (int count = 1; count <= n - 1; count++) {
                int k = -1;    // 选出一个距离初始顶点start最近的未标记顶点
                int dmin = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    if (visited[i] == 0 && weight[start][i] < dmin) {
                        dmin = weight[start][i];
                        k = i;
                    }
                }
                System.out.println("k=" + k);

                // 将新选出的顶点标记为已求出最短路径，且到start的最短路径就是dmin
                shortPath[k] = dmin;
                visited[k] = 1;

                // 以k为中间点，修正从start到未访问各点的距离
                for (int i = 0; i < n; i++) {
                    // System.out.println("k="+k);
                    if (visited[i] == 0 && weight[start][k] + weight[k][i] < weight[start][i]) {
                        weight[start][i] = weight[start][k] + weight[k][i];
                        path[i] = path[k] + "-->" + i;
                    }
                }
            }
            for (int i = 0; i < n; i++)
                System.out.println("从" + start + "出发到" + i + "的最短路径为：" + path[i]);
            System.out.println("=====================================");
            return shortPath;
        }
    }

    public static void main(String[] args) {
        /*
         * 邻接矩阵无向图的测试
         *
         * 0 0 1 1 0 1 0
         * 0 0 1 0 0 0 0
         * 1 1 0 1 0 0 0
         * 1 0 1 0 0 0 0
         * 0 0 0 0 0 0 1
         * 1 0 0 0 0 0 1
         * 0 0 0 0 1 1 0
         *
         * DFS: A C B D F G E
         * BFS: A C D F B G E
         */
        // MatrixGraphic.test(false);

        /*
         * 邻接矩阵有向图的测试
         *
         * 0 0 1 1 0 1 0
         * 0 0 1 0 0 0 0
         * 0 0 0 1 0 0 0
         * 0 0 0 0 0 0 0
         * 0 0 0 0 0 0 1
         * 0 0 0 0 0 0 1
         * 0 0 0 0 0 0 0
         *
         * DFS: A C D F G B E
         * BFS: A C D F G B E
         */
        // MatrixGraphic.test(true);

        /*
         * 邻接表无向图的测试
         *
         * 0(A): 2(C) 3(D) 5(F)
         * 1(B): 2(C)
         * 2(C): 0(A) 1(B) 3(D)
         * 3(D): 0(A) 2(C)
         * 4(E): 6(G)
         * 5(F): 0(A) 6(G)
         * 6(G): 4(E) 5(F)
         *
         * DFS: A C B D F G E
         * BFS: A C D F B G E
         */
        // ListGraphic.test(false);

        /*
         * 邻接表有向图的测试
         *
         * 0(A): 2(C) 3(D) 5(F)
         * 1(B): 2(C)
         * 2(C): 3(D)
         * 3(D):
         * 4(E): 6(G)
         * 5(F): 6(G)
         * 6(G):
         *
         * DFS: A C D F G B E
         * BFS: A C D F G B E
         */
        // ListGraphic.test(true);

        /*
         * 拓扑排序 - 基于减治法:
         *
         * 给出图的所有节点（按照字母顺序排列）的入度值：
         * 0	0	2	1	2
         * 给出图的拓扑排序结果：
         * a	b	c	d	e
         */
        // TopologicalSorting1.test();

        /*
         * 拓扑排序 - 深度优先查找法
         *
         * 使用ＤＦＳ方法得到拓扑排序序列的逆序：
         * [e, d, c, a, b]
         * 使用ＤＦＳ方法得到拓扑排序序列：
         * b	a	c	d	e
         */
        // TopologicalSorting2.test();

        /*
         * 克鲁斯卡尔算法 - 最小生成树
         *
         * (0,3)->5
         * (2,4)->5
         * (3,5)->6
         * (5,1)->7
         * (1,4)->7
         * (4,6)->9
         */
        // KruskalMinTree.test();

        /*
         * 普里姆(Prim)算法 - 最小生成树
         *
         * 边<A,B> 权值：4
         * 边<B,C> 权值：3
         * 边<A,D> 权值：5
         * 边<C,E> 权值：6
         */
        // PrimMinTree.test();

        /*
         * 迪杰斯特拉(Dijkstra)算法 - 最短路径
         *
         * k=1
         * k=3
         * k=2
         * k=4
         * 从0出发到0的最短路径为：0-->0
         * 从0出发到1的最短路径为：0-->1
         * 从0出发到2的最短路径为：0-->3-->2
         * 从0出发到3的最短路径为：0-->3
         * 从0出发到4的最短路径为：0-->3-->2-->4
         * =====================================
         * 从0出发到0的最短距离为：0
         * 从0出发到1的最短距离为：10
         * 从0出发到2的最短距离为：50
         * 从0出发到3的最短距离为：30
         * 从0出发到4的最短距离为：60
         */
        // Dijkstra.test();
    }
}
