package io.kzw.advance.main;

public class LinkedList {

    public static void main(String[] args) {
        Node node = new Node(1);
        Node node1 = new Node(2);
        node.next = node1;
        Node node2 = new Node(3);
        node1.next = node2;
        Node node3 = new Node(4);
        node2.next = node3;
        Node node4 = new Node(5);
        node3.next = node4;
//        node4.next = new Node(6);
        node4.next = node;

//        deleteNode(node3);
//        node = revert(node);
//        print(node);
        System.out.println("是否有环:" + hasCycle(node));
    }

    private static class Node {

        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private static void print(Node node) {
        Node current = node;
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    /**
     * 在 O(1) 时间删除链表节点
     */
    private static void deleteNode(Node node) {
        if (node == null || node.next == null)
            return;
        node.value = node.next.value;
        node.next = node.next.next;
    }

    /**
     * 逆转单链表
     */
    private static Node revert(Node node) {
        Node revert = null;
        Node current = node;
        while (current != null) {
            Node temp = current;
            current = current.next;
            temp.next = revert;
            revert = temp;
        }
        return revert;
    }

    /**
     * 判断单链表是否存在环
     * <p>
     * 还是通过快慢指针来解决，两个指针从头节点开始，慢指针每次向后移动一步，
     * 快指针每次向后移动两步，如果存在环，那么两个指针一定会在环内相遇。
     */
    private static boolean hasCycle(Node node) {
        if (node == null)
            return false;
        // 初始化快慢指针
        Node fast = node;
        Node slow = node;
        while (fast.next != null && fast.next.next != null) {
            // 快指针一次走2步
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                return true;
        }
        return false;
    }
}
