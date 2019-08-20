package com.xf.basic.practice;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-19 11:04
 * @Description:
 */
public class LinkedListTest {

    // 单链表反转
    public static ListNode reverseList(ListNode head) {



        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    // 链表中环的检测
    // 两个有序的链表合并
    // 删除链表倒数第n个结点
    // 求链表的中间结点


    public static void main(String[] args) {
        ListNode<String> node1 = new ListNode();
        node1.value = "a";
        ListNode<String> node2 = new ListNode();
        node2.value = "b";
        ListNode<String> node3 = new ListNode();
        node3.value = "c";

        node1.next = node2;
        node2.next = node3;

        ListNode reversedList = reverseList(node1);
        reversedList.print();


    }
}

class ListNode<T> {
    public T value;
    public ListNode next;

    public void print() {
        ListNode tmp = this;
        do {
            System.out.println(tmp.value);
            tmp = tmp.next;
        } while ( tmp != null );
    }

}

