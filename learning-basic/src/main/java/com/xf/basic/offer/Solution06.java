package com.xf.basic.offer;

import java.util.Stack;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-18 11:48
 * @Description:
 */
public class Solution06 {

    public static void printListReverse(ListNode listNode) {
        Stack<ListNode> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode);
            listNode = listNode.next;
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.pop().value);
        }
    }

    public static void printListReverse2(ListNode listNode) {
        if (listNode != null) {
            if (listNode.next != null) {
                printListReverse2(listNode.next);
            }
            System.out.println(listNode.value);
        }
    }

    public static void main(String[] args) {
        ListNode ln1 = new ListNode();
        ListNode ln2 = new ListNode();
        ListNode ln3 = new ListNode();
        ln1.next = ln2;
        ln2.next = ln3;
        ln1.value = 1;
        ln2.value = 2;
        ln3.value = 3;
        printListReverse(ln1);
        printListReverse2(ln1);
    }
}

class ListNode {
    ListNode next = null;
    int value;

    public void printOut() {
        System.out.println(value);
        ListNode tmp = next;
        while (tmp != null) {
            System.out.println(tmp.value);
            tmp = tmp.next;
        }
    }
}
