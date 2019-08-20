package com.xf.basic.practice;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-19 12:08
 * @Description: 基于数组实现的顺序栈
 */
public class ArrayStack {

    private String[] items;
    private int count;
    private int capacity;

    public ArrayStack(int capacity ) {
        this.capacity = capacity;
        this.items = new String[capacity];
        this.count = 0;
    }

    public boolean push(String item) {
        if (count == capacity)
            return false;
        items[count++] = item;
        return true;
    }

    public String pop() {
        if (count == 0)
            return null;
        String tmp = items[count-1];
        count --;
        return tmp;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);
        stack.push("a");
        stack.push("b");
        stack.push("c");

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push("d");
        System.out.println(stack.pop());
    }
}


