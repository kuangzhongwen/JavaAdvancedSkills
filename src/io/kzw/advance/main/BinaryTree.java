package io.kzw.advance.main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    public static void main(String[] args) {
        Tree root = insert(null, 5);
        insert(root, 8);
        insert(root, 1);
        insert(root, 9);
        insert(root, 2);
        insert(root, 10);
        insert(root, 15);

        /*
         *          5
         *  |  |         |   |
         *  1   2        8   9
         *                  |  |
         *                  10 15
         */

//        preorderTraversalRec(root);
//        preorderTraversal2(root);
//        inorderTraversalRec(root);
//        inorderTraversal2(root);
//        postorderTraversalRec(root);
//        postorderTraversal2(root);
//        levelTraversal(root);

//        System.out.println("节点数:" + getNodeNumRec(root));
//        System.out.println("深度:" + getDepthRec(root));
//        System.out.println("第k层的节点数:" + getNodeNumKthLevelRec(root, 3));
//        System.out.println("叶子节点数:" + getNodeNumLeafRec(root));
    }

    private static final class Tree {

        int value;
        Tree left;
        Tree right;

        Tree() {
        }

        Tree(int value) {
            this.value = value;
        }
    }

    private static Tree insert(Tree root, int value) {
        Tree tree = new Tree(value);
        if (root == null) {
            root = tree;
            root.left = null;
            root.right = null;
            return root;
        } else {
            Tree current = root;
            Tree parent;
            while (true) {
                parent = current;
                if (tree.value > current.value) {
                    current = current.right;
                    if (current == null) {
                        parent.right = tree;
                        return root;
                    }
                } else {
                    current = current.left;
                    if (current == null) {
                        parent.left = tree;
                        return root;
                    }
                }
            }
        }
    }

    // =========================== 二叉树的遍历 ================================

    /**
     * 前序遍历 - 递归实现
     */
    private static void preorderTraversalRec(Tree root) {
        if (root == null)
            return;
        System.out.println(root.value);
        preorderTraversalRec(root.left);
        preorderTraversalRec(root.right);
    }

    /**
     * 前序遍历 - 非递归实现
     * 用一个辅助 stack，总是先把右孩子放进栈
     */
    private static void preorderTraversal2(Tree root) {
        if (root == null)
            return;
        Stack<Tree> stack = new Stack<>();
        Tree current = root;
        while (current != null || !stack.isEmpty()) {
            // 不断将左子节点入栈，直到 current 为空
            while (current != null) {
                stack.push(current);
                // 前序遍历，先打印当前节点在打印左子节点，然后再把右子节点加到栈中
                System.out.println(current.value);
                current = current.left;
            }
            // 栈不为空，弹出栈元素
            if (!stack.isEmpty()) {
                // 此时弹出最左边的节点
                current = stack.pop();
                // 令当前节点为右子节点
                current = current.right;
            }
        }
    }

    /**
     * 中序遍历 - 递归实现
     */
    private static void inorderTraversalRec(Tree root) {
        if (root == null)
            return;
        inorderTraversalRec(root.left);
        System.out.println(root.value);
        inorderTraversalRec(root.right);
    }

    /**
     * 中序遍历 - 非递归实现
     */
    private static void inorderTraversal2(Tree root) {
        if (root == null)
            return;
        Stack<Tree> stack = new Stack<>();
        Tree current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            if (!stack.isEmpty()) {
                current = stack.pop();
                System.out.println(current.value);
                current = current.right;
            }
        }
    }

    /**
     * 后序遍历 - 递归实现
     */
    private static void postorderTraversalRec(Tree root) {
        if (root == null)
            return;
        postorderTraversalRec(root.left);
        postorderTraversalRec(root.right);
        System.out.println(root.value);
    }

    /**
     * 后序遍历 - 非递归实现
     */
    private static void postorderTraversal2(Tree root) {
        if (root == null)
            return;
        Stack<Tree> stack1 = new Stack<>();
        Stack<Tree> stack2 = new Stack<>();
        stack1.add(root);
        while (!stack1.isEmpty()) {
            Tree temp = stack1.pop();
            stack2.push(temp);
            if (temp.left != null) {
                stack1.push(temp.left);
            }
            // 右子节点后入栈
            if (temp.right != null) {
                stack1.push(temp.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().value);
        }
    }

    /**
     * 层次遍历二叉树，使用队列
     */
    private static void levelTraversal(Tree root) {
        if (root == null)
            return;
        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Tree temp = queue.poll();
            System.out.println(temp.value);
            if (temp.left != null)
                queue.add(temp.left);
            if (temp.right != null)
                queue.add(temp.right);
        }
    }

    /**
     * 求二叉树中的节点个数
     */
    private static int getNodeNumRec(Tree root) {
        if (root == null)
            return 0;
        return getNodeNumRec(root.left) + getNodeNumRec(root.right) + 1;
    }

    /**
     * 求数的深度
     */
    private static int getDepthRec(Tree root) {
        if (root == null)
            return 0;
        return Math.max(getDepthRec(root.left), getDepthRec(root.right)) + 1;
    }

    /**
     * 求第k层的节点个数
     */
    private static int getNodeNumKthLevelRec(Tree root, int k) {
        if (root == null || k < 1)
            return 0;
        if (k == 1)
            return 1;
        return getNodeNumKthLevelRec(root.left, k - 1) + getNodeNumKthLevelRec(root.right, k - 1);
    }

    /**
     * 求叶子节点的个数
     */
    private static int getNodeNumLeafRec(Tree root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        return getNodeNumLeafRec(root.left) + getNodeNumLeafRec(root.right);
    }
}

