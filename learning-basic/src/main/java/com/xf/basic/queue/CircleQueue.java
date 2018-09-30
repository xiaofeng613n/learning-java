package com.xf.basic.queue;

/**
 * Created by xiaofeng on 2018/8/24
 * Description:
 */
public class CircleQueue<E> {
    private Object [] a; //对象数组，队列最多存储a.length-1个对象
    private volatile int front;  //队首下标
    private volatile int tail;   //队尾下标

    public CircleQueue(int size){
        a = new Object[size + 1];
        front = 0;
        tail =0;
    }
    /**
     * 将一个对象追加到队列尾部
     * @param obj 对象
     * @return 队列满时返回false,否则返回true
     */
    public boolean enqueue(E obj){
        if((tail +1)%a.length==front){
            return false;
        }
        a[tail]=obj;
        tail = (tail +1)%a.length;
        return true;
    }
    /**
     * 队列头部的第一个对象出队
     * @return 出队的对象，队列空时返回null
     */
    public E dequeue(){
        if(tail ==front){
            return null;
        }
        E obj = (E) a[front];
        front = (front+1)%a.length;
        return obj;
    }
}