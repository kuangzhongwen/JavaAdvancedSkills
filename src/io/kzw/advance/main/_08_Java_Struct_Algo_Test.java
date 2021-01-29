package io.kzw.advance.main;

import java.util.Stack;

/**
 * Java数据结构与算法练习.
 *
 * @author kzw on 2018/09/15.
 */
public final class _08_Java_Struct_Algo_Test {

    /**
     * 1. 二进制中1的个数
     *
     * ---------------------
     * 题目描述：
     *
     * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
     *
     * 思路解析：https://www.nowcoder.com/questionTerminal/8ee967e43c2c4ec193b040ea7fbb10b8
     *
     * 如果一个整数不为0，那么这个整数至少有一位是1。如果我们把这个整数减1，那么原来处在整数最右边的1就会变为0，
     * 原来在1后面的所有的0都会变成1(如果最右边的1后面还有0的话)。其余所有位将不会受到影响。
     *
     * 举个例子：一个二进制数1100，从右边数起第三位是处于最右边的一个1。减去1后，第三位变成0，它后面的两位0变成了1，
     * 而前面的1保持不变，因此得到的结果是1011.我们发现减1的结果是把最右边的一个1开始的所有位都取反了。
     * 这个时候如果我们再把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0。
     * 如1100&1011=1000.也就是说，把一个整数减去1，再和原整数做与运算，会把该整数最右边一个1变成0.
     * 那么一个整数的二进制有多少个1，就可以进行多少次这样的操作。
     * ---------------------
     */
    public static int numberOf1(int n) {
        /*
         * int n = 235;
         * // 11101011
         * System.out.println("n binary:" + Integer.toBinaryString(n));
         * // 11101010 - > 右边第一个1会变成0，第一个1后面的位取反
         * System.out.println(("n - 1 binary:" + Integer.toBinaryString(n - 1)));
         * // 如果我们再把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0
         * System.out.println("(n - 1) & n binary:" + Integer.toBinaryString((n - 1) & n));
         * // 直到变成0，那么所有的1都为0了
         */
        int count = 0;
        while (n != 0) {
            count++;
            n = (n - 1) & n;
        }
        return count;
    }

    /**
     * 2. 用栈实现队列
     *
     * ---------------------
     * 题目描述：
     *
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     *
     * 思路解析：
     *
     * 入栈：栈1是用来入栈的，栈2是辅助；
     *           如果栈2是空的，直接入栈到栈1中，如果栈2不为空，那么就先把栈2中的push回栈1中，再入栈。
     * 出栈：栈2是用来出栈的，栈1是辅助；
     *           如果栈1是非空的，要把栈1的都push到栈2中，如果栈1是空的，那么直接push到栈2中。
     * ---------------------
     */
    public static class SolutionStackImplQueue {

        private Stack<Integer> stack1 = new Stack<>();
        private Stack<Integer> stack2 = new Stack<>();

        private void push(int node) {
            /*
             * 先把旧的元素都取出来，栈是后进先出，pop出来之后，放到栈1中
             */
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            stack1.push(node);
        }

        private int pop() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            return stack2.pop();
        }

        public static void test() {
            // 队列，先进先出
            SolutionStackImplQueue queue = new SolutionStackImplQueue();
            queue.push(1);
            queue.push(2);
            queue.push(3);
            // 1
            System.out.println("SolutionStackImplQueue pop:" + queue.pop());
        }
    }

    /**
     * 3. 前序遍历-后序遍历（递归与非递归实现）深度优先遍历
     */
    public static class SolutionTreeErgodic {

        private static class Node<E extends Comparable<E>> {
            E value;
            Node left;
            Node right;

            Node(E value) {
                this.value = value;
                this.left = null;
                this.right = null;
            }
        }

        /*
         * 前序遍历递归实现 - 根左右
         */
        private void preorderTraversalRecursion(Node root) {
            System.out.println(root.value);
            if (root.left != null)
                preorderTraversalRecursion(root.left);
            if (root.right != null)
                preorderTraversalRecursion(root.right);
        }

        /*
         * 前序遍历非递归实现 - 根左右
         *
         * 主要采用数据结构栈来辅助实现。
         * 每pop一个节点（当前根节点），前提是栈不为空，先将节点右孩子入栈，再将左孩子入栈（前提是孩子存在），依次pop及push。
         */
        private void preorderTraversal(Node root) {
            Stack<Node> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                System.out.println(node.value);
                if (node.right != null)
                    stack.push(node.right);
                if (node.left != null)
                    stack.push(node.left);
            }
        }

        /*
         * 后序遍历递归实现 - 左右根
         */
        private void postorderTraversalRecursion(Node root) {
            if (root.left != null)
                postorderTraversalRecursion(root.left);
            if (root.right != null)
                postorderTraversalRecursion(root.right);
            System.out.println(root.value);
        }

        /*
         * 后序遍历非递归实现 - 左右根
         *
         * 首先将节点入栈，栈不空的时候，判断栈顶节点是否有左右孩子，没有左右孩子这可以直接访问
         *（值添加进list中，pop，改变当前访问的节点）或者是在此步之前访问的节点是该节点的孩子（孩子均被访问过了），
         * 也可以直接访问该节点，除此之外，需将节点的孩子按照右左的顺序入栈。
         */
        private void postorderTraversal(Node root) {
            Stack<Node> stack = new Stack<>();
            Node pre = null;
            stack.push(root);
            while (!stack.isEmpty()) {
                Node current = stack.peek();
                // 当前左右子树都为空或者pre不为空与pre等于当前的左右子树
                if ((current.left == null && current.right == null) ||
                        (pre != null && (pre == current.right || pre == current.left))) {
                    System.out.println(current.value);
                    pre = current;
                    stack.pop();
                } else {
                    if(current.right != null)
                        stack.push(current.right);
                    if(current.left != null)
                        stack.push(current.left);
                }
            }
        }

        public static void test() {
            SolutionTreeErgodic solutionTreeErgodic = new SolutionTreeErgodic();
            // 伪代码
            solutionTreeErgodic.preorderTraversalRecursion(null);
            solutionTreeErgodic.preorderTraversal(null);
            solutionTreeErgodic.postorderTraversalRecursion(null);
            solutionTreeErgodic.postorderTraversal(null);
        }
    }

    /**
     * 4. 二维数组的查找
     */
    public static boolean findInPartiallySortedMatrix(int[][] array, int number) {
        boolean find = false;
        int rows = array.length;
        int columns = array[0].length;
        int row = 0;
        int column = columns - 1;
        while (row < rows && column >= 0) {
            if (array[row][column] == number) {
                find = true;
                break;
            } else if (array[row][column] > number) {
                column--;
            } else if (array[row][column] < number) {
                row++;
            }
        }
        return find;
    }

    /**
     * 5. 将一串字符串的空格替换为%20
     */
    public static String replaceBank(char c[]) {
        if (c == null || c.length <= 0)
            return null;
        int i = 0;
        int numberOfBank = 0;
        while (c[i] != '\0') {
            if (c[i] == ' ')
                ++numberOfBank;
            ++i;
        }
        int newLength = i + 2 * numberOfBank;
        int indexOfOriginal = i;
        int indexOfNew = newLength;

        while (indexOfOriginal >= 0 && indexOfNew > indexOfOriginal) {
            if (c[indexOfOriginal] == ' ') {
                c[indexOfNew--] = '0';
                c[indexOfNew--] = '2';
                c[indexOfNew--] = '%';
            } else {
                c[indexOfNew--] = c[indexOfOriginal];
            }
            --indexOfOriginal;
        }
        return new String(c);
    }

    /**
     * 6. 从尾到头打印链表
     */
    public static class SolutionPrintNodes {

        private static class Node {

            int value;
            Node next;

            Node(int value) {
                this.value = value;
            }
        }

        /*
         * 递归实现
         */
        private static void printListReversingly1(Node head) {
            if (head != null) {
                if (head.next != null) {
                    printListReversingly1(head.next);
                }
                System.out.println(head.value + "、");
            }
        }

        /*
         * 栈实现
         */
        private static void printListReversingly2(Node head) {
            Stack<Integer> stack = new Stack<>();
            Node p = head;
            while (p != null) {
                stack.push(p.value);
                p = p.next;
            }
            // 后进先出
            while (!stack.isEmpty()) {
                System.out.println(stack.pop() + "、");
            }
        }

        public static void test() {
            Node head = new Node(1);
            Node node1 = new Node(2);
            Node node2 = new Node(3);
            Node node3 = new Node(4);

            head.next = node1;
            node1.next = node2;
            node2.next = node3;
            node3.next = null;

            printListReversingly1(head);
            printListReversingly2(head);
        }
    }

    /**
     * 7. 一个递增排序的数组的一个旋转(把一个数组最开始的若干元素搬到数组的末尾，称之为数组的旋转)，输出旋转数组的最小元素。
     *
     * ---------------------
     * 思路：
     * 其实旋转过后的数组是部分有序的，这样的数组依然可以使用折半查找的思路求解。
     * ---------------------
     */
    public static class SolutionRevolveArray {

        private static int findMin(int array[]) throws Exception {
            if (array == null || array.length <= 0) {
                throw new Exception("输入参数有误");
            }
            int start = 0;
            int end = array.length - 1;
            int mid = start;
            while (array[start] >= array[end]) {
                if (end - start == 1) {
                    mid = end;
                    break;
                }
                mid = (start + end) / 2;
                if (array[start] == array[end] && array[end] == array[mid]) {
                    return minInOrder(array, start, end);
                }
                if (array[mid] >= array[start]) {
                    start = mid;
                } else if (array[mid] <= array[end]) {
                    end = mid;
                }
            }
            return array[mid];
        }

        private static int minInOrder(int array[], int start, int end) {
            int result = array[start];
            for (int i = start + 1; i <= end; ++i) {
                if (result > array[i]) {
                    result = array[i];
                }
            }
            return result;
        }

        public static void test() {
            try {
                int[] A = {4, 5, 6, 7, 8, 0, 1, 2, 3};
                int[] A1 = {1, 1, 0, 0, 1, 1, 1};
                System.out.println(findMin(A));
                System.out.println(findMin(A1));
                int[] B = {1};
                System.out.println(findMin(B));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 8. 给定单向链表的头指针和一个结点指针，定义一个函数在O(1)时间删除该结点。
     *
     * ---------------------
     * 思路：
     * 想要在O(1)时间内删除链表的指定结点，要遍历的话得O(n)，则肯定不能遍历。
     * 若是要删除的结点不是尾结点，那么可以将后面的那个值复制到该指针处，并将后面指针所指空间删除，
     * 用复制+删除后面的实现删除，时间复杂度为O(1)。对于尾结点，需要遍历，那么时间复杂度是O(n)，
     * 但是总的时间复杂度为[(n-1)*O(1)+O(n)]/n，结果是O(1)。
     * ---------------------
     */
    public static class SolutionDeleteNodeList {

        private static class Node {

            int value;
            Node next;

            Node(int value) {
                this.value = value;
            }

            void setNext(Node next) {
                this.next = next;
            }
        }

        private static void deleteNode(Node head, Node toBeDeleted) {
            if (head == null || toBeDeleted == null) {
                return;
            }
            // 找删除节点的下一节点, 要删除的节点不是尾节点
            if (toBeDeleted.next != null) {
                Node node = toBeDeleted.next;
                toBeDeleted.value = node.value;
                // 删除了结点
                toBeDeleted.next = node.next;
            } else if (toBeDeleted == head) {
                head = null;
            } else {
                // 删除尾结点
                Node current = head;
                // 必须遍历到尾结点的前一个结点，再将它的下一个引用指向为null，就删除了尾结点，这边是 O(n)
                while (current.next != toBeDeleted) {
                    current = current.next;
                }
                current.next = null;
            }
        }

        private static void printNodes(Node head) {
            Node current = head;
            while (current.next != null) {
                System.out.println(current.value + " - ");
                current = current.next;
            }
        }

        public static void test() {
            Node head = new Node(1);
            Node node2 = new Node(2);
            Node node3 = new Node(3);
            Node node4 = new Node(4);
            Node node5 = new Node(5);
            Node node6 = new Node(6);
            Node node7 = new Node(7);
            head.setNext(node2);
            node2.setNext(node3);
            node3.setNext(node4);
            node4.setNext(node5);
            node5.setNext(node6);
            node6.setNext(node7);
            node7.setNext(null);

            // 输出原始链表
            System.out.println("原始链表：");
            printNodes(head);
            System.out.println("----------------");
            // 删除结点node_3
            deleteNode(head, node3);
            System.out.println("删除node3后链表：");
            printNodes(head);
            System.out.println("----------------");
            // 删除结点head
            deleteNode(head, head);
            System.out.println("删除head后链表：");
            printNodes(head);
            System.out.println("----------------");
            // 删除尾节点
            deleteNode(head, node7);
            System.out.println("删除尾节点后链表：");
            printNodes(head);
            System.out.println("----------------");
        }
    }

    /**
     * 9. 对于一个数组，实现一个函数使得所有奇数位于数组的前半部分，偶数位于数组的后半部分。
     *
     * ---------------------
     * 思路：可以使用双指针的方式，一个指针指向数组的开始，一个指针指向数组的尾部，
     * 如果头部的数为偶数且尾部的数是奇数则交换，否则头部指针向后移动，
     * 尾部指针向前移动，直到两个指针相遇。
     * ---------------------
     */
    public static void reorderOddEven(int array[]) {
        if (array == null || array.length == 0) {
            return;
        }
        int start = 0;
        int end = array.length - 1;
        // 直到相等才停止
        while (start < end) {
            // 如果start为偶数，end为奇数，则交换
            if (array[start] % 2 == 0 && array[end] % 2 == 1) {
                int tmp = array[start];
                array[start] = array[end];
                array[end] = tmp;
                start++;
                end--;
            } else if (array[start] % 2 == 1) {
                start++;
            } else {
                end--;
            }
        }
    }

    /**
     * 10. 对于一个链表，反转该链表并返回头结点。
     *
     * ---------------------
     * 思路：
     * 主要是指针的操作，但是要注意不能断链。这里可以使用非递归的方式求解。
     * ---------------------
     */
    public static class SolutionReverseList {

        private static class Node {

            int value;
            Node next;

            Node(int value) {
                this.value = value;
            }

            void setNext(Node next) {
                this.next = next;
            }
        }

        private static Node reverseList(Node head) {
            // 翻转后的头结点
            Node reverseHead = null;
            // 当前结点
            Node pNode = head;
            // 当前结点的前一个结点
            Node pPre = null;
            while (pNode != null) {
                Node next = pNode.next;
                pNode.setNext(pPre);
                pPre = pNode;
                pNode = next;
                if (next == null) {
                    reverseHead = pNode;
                }
            }
            return reverseHead;
        }

        public static void test() {
            Node head = new Node(0);
            Node node1 = new Node(1);
            Node node2 = new Node(2);
            Node node3 = new Node(3);
            Node node4 = new Node(4);
            head.setNext(node1);
            node1.setNext(node2);
            node2.setNext(node3);
            node3.setNext(node4);
            node4.setNext(null);
            Node tmp = reverseList(head);
            while (tmp != null) {
                System.out.println(tmp.value + "、");
                tmp = tmp.next;
            }
        }
    }

    /**
     * 11. 输入两个递增排序的链表，合并这两个链表并使得新链表中的结点仍然按照递增排序的。
     *
     * ---------------------
     * 思路：
     * 主要是链表中值的比较，取较小的结点插入到新的链表中。
     * ---------------------
     */
    public static class SolutionMergeSortedLists {

        private static class Node {

            int value;
            Node next;

            Node(int value) {
                this.value = value;
            }

            void setNext(Node next) {
                this.next = next;
            }
        }

        private static void printNodes(Node head) {
            Node current = head;
            while (current.next != null) {
                System.out.println(current.value + " - ");
                current = current.next;
            }
        }

        private static Node mergeSortedLists(Node head1, Node head2) {
            if (head1 == null) {
                return head2;
            }
            if (head2 == null) {
                return head1;
            }
            Node mergedHead;
            // 递归
            if (head1.value < head2.value) {
                mergedHead = head1;
                mergedHead.setNext(mergeSortedLists(head1.next, head2));
            } else {
                mergedHead = head2;
                mergedHead.setNext(mergeSortedLists(head1, head2.next));
            }
            return mergedHead;
        }

        public static void test() {
            // 构建链表1
            Node head1 = new Node(1);
            Node node1_2 = new Node(3);
            Node node1_3 = new Node(5);
            Node node1_4 = new Node(7);
            head1.setNext(node1_2);
            node1_2.setNext(node1_3);
            node1_3.setNext(node1_4);
            node1_4.setNext(null);

            // 构建链表2
            Node head2 = new Node(2);
            Node node2_2 = new Node(4);
            Node node2_3 = new Node(6);
            Node node2_4 = new Node(8);
            head2.setNext(node2_2);
            node2_2.setNext(node2_3);
            node2_3.setNext(node2_4);
            node2_4.setNext(null);
            System.out.println("链表1：");
            printNodes(head1);
            System.out.println("-------------");
            System.out.println("链表2：");
            printNodes(head2);
            System.out.println("-------------");

            System.out.println("合并后的链表：");
            Node mergedList = mergeSortedLists(head1, head2);
            printNodes(mergedList);
        }
    }

    /**
     * 12. 二叉树的镜像
     *
     * 给定一棵二叉树，将其每一个结点的左右子树交换，这就叫做镜像。
     *
     * ---------------------
     * 思路：
     *     先对其根节点的左右子树处理，交换左右子树，此时再递归处理左右子树。
     *     这里要注意分为三种情况：
     *     1、树为空；2、只有根结点；3、左右子树至少有一个不为空。
     * ---------------------
     */
    private static class SolutionMirrorOfBinaryTree {

        private static class Node<E extends Comparable<E>> {
            E value;
            Node left;
            Node right;

            Node(E value) {
                this.value = value;
                this.left = null;
                this.right = null;
            }

            void setLeft(Node left) {
                this.left = left;
            }

            void setRight(Node right) {
                this.right = right;
            }
        }

        private static void preorderTree(Node root) {
            if (root != null) {
                // 根左右，根在前就是前序，根在中就是中序，根在后就是后序
                System.out.println(root.value);
                preorderTree(root.left);
                preorderTree(root.right);
            }
        }

        private static void mirrorRecursively(Node root) {
            // 如果根为空
            if (root == null)
                return;
            // 如果左右子树都为空
            if (root.left == null && root.right == null)
                return;
            Node tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            // 递归求解左右子树
            if (root.left != null)
                mirrorRecursively(root.left);
            if (root.right != null)
                mirrorRecursively(root.right);
        }

        static void test() {
            Node root = new Node(8);
            Node t1 = new Node(6);
            Node t2 = new Node(10);
            Node t3 = new Node(5);
            Node t4 = new Node(7);
            Node t5 = new Node(9);
            Node t6 = new Node(11);
            root.setLeft(t1);
            root.setRight(t2);
            t1.setLeft(t3);
            t1.setRight(t4);
            t2.setLeft(t5);
            t2.setRight(t6);
            t3.setLeft(null);
            t3.setRight(null);
            t4.setLeft(null);
            t4.setRight(null);
            t5.setLeft(null);
            t5.setRight(null);
            t6.setLeft(null);
            t6.setRight(null);

            mirrorRecursively(root);
            preorderTree(root);
        }
    }

    public static void main(String[] args) {
//         numberOf1(235);
//         SolutionStackImplQueue.test();
//         SolutionTreeErgodic.test();

//        int[][] array = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
//        System.out.println(findInPartiallySortedMatrix(array, 15));
//        System.out.println(findInPartiallySortedMatrix(array, 1));
//        System.out.println(findInPartiallySortedMatrix(array, 7));

//        String s = " we are  happy ";
//        char[] oldChars = s.toCharArray();
//        char[] newChars = new char[100];
//        for (int i = 0; i < oldChars.length; i++) {
//            newChars[i] = oldChars[i];
//        }
//        System.out.println(replaceBank(newChars));

//        SolutionPrintNodes.test();

//        SolutionRevolveArray.test();

//        SolutionDeleteNodeList.test();

//        int[] array = {2, 4, 6, 8, 1, 3, 5, 7};
//        reorderOddEven(array);
//        System.out.println(Arrays.toString(array));

//        SolutionReverseList.test();

//        SolutionMergeSortedLists.test();

//        SolutionMirrorOfBinaryTree.test();
    }
}
