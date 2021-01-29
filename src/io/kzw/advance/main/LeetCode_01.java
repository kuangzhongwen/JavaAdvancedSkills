package io.kzw.advance.main;

import java.util.*;

/**
 * LeetCode 初级题库.
 *
 * @author kuang on 2018/11/30.
 */
public class LeetCode_01 {

    public static void main(String[] args) {
//        int[] array = new int[]{1, 1, 2};
//        removeDuplicates(array);
//        int[] array = {1, 2, 3, 4, 5, 6, 7};
//        rotate(array, 3);
//        int[] array = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
//        boolean containsDuplicate = containsDuplicate(array);
//        System.out.println("containsDuplicate = " + containsDuplicate);
//        System.out.println("singleNums = " + singleNumber(new int[]{4, 1, 2, 1, 2}));

//        int[] a = new int[] {2, 2, 5, 3, 1};
//        int[] b = new int[] {2, 2, 6, 6, 6};
//        System.out.println(Arrays.toString(intersect(a, b)));

//        int[] array = {0, 1, 0, 3, 12};
//        moveZeroes(array);

//        System.out.println("reverse string: " + reverseString("kuangzhongwen"));
//        System.out.println("reverse number: " + reverse(123));
        System.out.println(firstUniqChar("loveleetcode"));
    }


    // =========================================  数组 =====================================================

    /**
     * 1. 从排序数组中删除重复项
     */
    public static int removeDuplicates(int[] nums) {
        int len = nums.length;
        Set<Integer> result = new HashSet<>(len);
        for (int i = 0; i < len; i++) {
            result.add(nums[i]);
        }
        nums = new int[result.size()];
        int index = -1;
        for (Iterator<Integer> iterator = result.iterator(); iterator.hasNext(); ) {
            nums[++index] = iterator.next();
        }
        System.out.println(Arrays.toString(nums));
        return nums.length;
    }

    /**
     * 2. 旋转数组
     * <p>
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     * <p>
     * 输入: [1,2,3,4,5,6,7] 和 k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     */
    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        if (k <= 0 || k > len || k == len)
            return;
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[(i + k) % len] = nums[i];
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 3. 给定一个整数数组，判断是否存在重复元素。
     * <p>
     * 如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。
     * <p>
     * 示例 1:
     * <p>
     * 输入: [1,2,3,1]
     * 输出: true
     * 示例 2:
     * <p>
     * 输入: [1,2,3,4]
     * 输出: false
     * 示例 3:
     * <p>
     * 输入: [1,1,1,3,3,4,3,2,4,2]
     * 输出: true
     */
    public static boolean containsDuplicate(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 4. 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * <p>
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * 示例 1:
     * 输入: [2,2,1]
     * <p>
     * 输出: 1
     * <p>
     * 示例 2:
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public static int singleNumber(int[] nums) {
        int num = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            num ^= nums[i];
        }
        return num;
    }

    /**
     * 5. 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2,2]
     * 示例 2:
     * <p>
     * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出: [4,9]
     * 说明：
     * <p>
     * 输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
     * 我们可以不考虑输出结果的顺序。
     * 进阶:
     * <p>
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     * 如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     */
    public static int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0, len = nums1.length; i < len; i++) {
            Integer value = map.get(nums1[i]);
            // 去重
            map.put(nums1[i], value == null ? 1 : value + 1);
        }
        for (int j = 0, len = nums2.length; j < len; j++) {
            if (map.containsKey(nums2[j]) && map.get(nums2[j]) != null) {
                list.add(nums2[j]);
                map.put(nums2[j], map.get(nums2[j]) - 1);
            }
        }
        int size = list.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 6. 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 示例:
     * <p>
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 说明:
     * <p>
     * 必须在原数组上操作，不能拷贝额外的数组。
     * 尽量减少操作次数。
     */
    public static void moveZeroes(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != k) {
                    int temp = nums[i];
                    nums[i] = nums[k];
                    nums[k] = temp;
                }
                k++;
            }
        }
        System.out.println(Arrays.toString(nums));
    }


    // ========================================= 字符串 =====================================================

    /**
     * 1. 编写一个函数，其作用是将输入的字符串反转过来。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "hello"
     * 输出: "olleh"
     * 示例 2:
     * <p>
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: "amanaP :lanac a ,nalp a ,nam A"
     */
    public static String reverseString(String s) {
        int len = s.length();
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = len - 1; i >= 0; i--) {
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 2. 整数反转
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     * <p>
     * 示例 1:
     * <p>
     * 输入: 123
     * 输出: 321
     * 示例 2:
     * <p>
     * 输入: -123
     * 输出: -321
     * 示例 3:
     * <p>
     * 输入: 120
     * 输出: 21
     * 注意:
     * <p>
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     */
    public static int reverse(int x) {
        // 123
        int result = 0;
        while (x > 0) {
            result *= 10;
            result += x % 10;
            x /= 10;
        }
        return result;
    }

    /**
     * 3. 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     * <p>
     * 案例:
     * <p>
     * s = "leetcode"
     * 返回 0.
     * <p>
     * s = "loveleetcode",
     * 返回 2.
     * <p>
     * <p>
     * 注意事项：您可以假定该字符串只包含小写字母。
     */
    public static int firstUniqChar(String s) {
        int len = s.length();
        boolean repeated;
        for (int i = 0; i < len; i++) {
            repeated = false;
            for (int j = 0; j < len; j++) {
                if (i != j && s.charAt(i) == s.charAt(j)) {
                    repeated = true;
                    break;
                }
            }
            if (!repeated) {
                return i;
            }
        }
        return -1;
    }

    // ====================================== 链表 ======================================

    /**
     * 1. 删除链表中的节点
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
     * <p>
     * 现有一个链表 -- head = [4,5,1,9]，它可以表示为:
     * <p>
     * 4 -> 5 -> 1 -> 9
     * 示例 1:
     * <p>
     * 输入: head = [4,5,1,9], node = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     * 示例 2:
     * <p>
     * 输入: head = [4,5,1,9], node = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     * 说明:
     * <p>
     * 链表至少包含两个节点。
     * 链表中所有节点的值都是唯一的。
     * 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     * 不要从你的函数中返回任何结果。
     */
    private static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    private static void deleteNode(Node head, int x) {
        Node p = head;
        Node q = p.next;
        while (q != null) {
            if (q.val == x) {
                p.next = q.next;
            }
            p = q;
            q = q.next;
        }
    }


    /**
     * 2. 反转链表
     * 反转一个单链表。
     * <p>
     * 示例:
     * <p>
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * 进阶:
     * 你可以迭代或递归地反转链表。
     */
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

    /**
     * 3. 合并两个有序链表
     * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * <p>
     * 示例：
     * <p>
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */
    private static Node mergeTwoLists(Node l1, Node l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        Node head;
        if (l1.val < l2.val) {
            head = l1;
            head.next = mergeTwoLists(l1.next, l2);
        } else {
            head = l2;
            head.next = mergeTwoLists(l1, l2.next);
        }
        return head;
    }


    // ====================================== 树 ===================================================

    /**
     * 1. 将有序数组转换为二叉搜索树
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * <p>
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     * <p>
     * 示例:
     * <p>
     * 给定有序数组: [-10,-3,0,5,9],
     * <p>
     * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
     * <p>
     * 0
     * / \
     * -3   9
     * /   /
     * -10  5
     */
    private static class TreeNode {

        int data;
        TreeNode left;
        TreeNode right;

        TreeNode(int data) {
            this.data = data;
        }
    }

    /**
     * 这个题有点类似二分查找，根节点在正中间，左子树的根节点在左边一半数组的正中间，右子树的根节点在右边一半数组的正中间；
     * 于是可以递归解决：先确定根节点，然后构造左子树，然后构造右子树， 结束条件类似二分查找(low > high)。
     */
    private static TreeNode sortedArrayToTree(int[] array) {
        return sortedArrayToTree(array, 0, array.length - 1);
    }

    private static TreeNode sortedArrayToTree(int[] array, int left, int right) {
        if (left <= right) {
            int mid = (left + right) / 2;
            TreeNode treeNode = new TreeNode(array[mid]);
            treeNode.left = sortedArrayToTree(array, left, mid - 1);
            treeNode.right = sortedArrayToTree(array, mid + 1, right);
            return treeNode;
        } else {
            return null;
        }
    }

    /**
     * 2. 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回它的最大深度 3 。
     */
    private static int treeDepth(TreeNode root) {
        if (root == null)
            return 0;
        int leftLevel = treeDepth(root.left) + 1;
        int rightLevel = treeDepth(root.right) + 1;
        return Math.max(leftLevel, rightLevel);
    }

    /**
     * 3. 二叉树的层次遍历，广度优先，深度非递归用栈实现，广度用队列实现.
     * <p>
     * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
     * <p>
     * 例如:
     * 给定二叉树: [3,9,20,null,null,15,7],
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回其层次遍历结果：
     * <p>
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     */
    private static void breadthFirstTraverse(TreeNode root) {
        // 深度遍历利用栈，广度遍历利用队列
        // 利用队列
//        LinkedList<TreeNode> queue = new LinkedList<>();
//        TreeNode current;
//        queue.offer(root);
//        while (!queue.isEmpty()) {
//            current = queue.pop();
//            System.out.println("data = " + current.data);
//            // 先进先出
//            if (current.left != null) {
//                queue.offer(current.left);
//            }
//            if (current.right != null) {
//                queue.offer(current.right);
//            }
//        }
    }

    // =================================== 回溯算法 ======================================

    /**
     * 1. 全排列
     * 给定一个没有重复数字的序列，返回其所有可能的全排列。
     * <p>
     * 示例:
     * <p>
     * 输入: [1,2,3]
     * 输出:
     * [
     * [1,2,3],
     * [1,3,2],
     * [2,1,3],
     * [2,3,1],
     * [3,1,2],
     * [3,2,1]
     * ]
     */
    private static class Solution {

        // 当没有下一个排列时return false
        public boolean nextPermutation(int[] nums) {
            if (nums.length == 1) {
                return false;
            }
            int p = -1;
            for (int i = nums.length - 2; i >= 0; i--) {
                if (nums[i] < nums[i + 1]) {
                    p = i;
                    break;
                }
            }

            if (p != -1) {
                int tmp = nums[p];
                int q = nums.length - 1;
                while (nums[q] <= tmp) {
                    q--;
                }

                nums[p] = nums[q];
                nums[q] = tmp;

                reverse(p + 1, nums);
            } else {
//            reverse(0, nums);
                return false;
            }

            return true;
        }


        public void reverse(int k, int[] nums) {
            if (k >= nums.length) return;
            int i = k;
            int j = nums.length - 1;
            while (i < j) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
            }
        }

        public List<List<Integer>> permute(int[] nums) {
            Arrays.sort(nums);
            List<List<Integer>> ans = new ArrayList<>();
            do {
                List<Integer> tmp = new ArrayList<>();
                for (int num : nums) {
                    tmp.add(num);
                }
                ans.add(tmp);
            } while (nextPermutation(nums));
            return ans;
        }
    }

    /**
     * 2. java实现LRU
     */
    public class LRUCache2<K, V> extends LinkedHashMap<K, V> {

        private final int MAX_CACHE_SIZE;

        public LRUCache2(int cacheSize) {
            super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
            MAX_CACHE_SIZE = cacheSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_CACHE_SIZE;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<K, V> entry : entrySet()) {
                sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
            }
            return sb.toString();
        }
    }

    private static class LRUCache {

        Map<Integer, Integer> map;
        Stack<Integer> stack;
        int size;

        public LRUCache(int capacity) {
            stack = new Stack<>();
            map = new HashMap<>(capacity);
            size = capacity;
        }

        public int get(int key) {
            if (!stack.contains(key)) {
                return -1;
            }
            stack.remove(key);
            stack.push(key);
            return map.get(key);
        }

        public void put(int key, int value) {
            if (stack.contains(key)) {
                stack.remove(key);
            } else {
                if (stack.size() == size) {
                    int count = stack.remove(0);
                    map.remove(count);
                }
            }
            stack.push(key);
            map.put(key, value);
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

}
