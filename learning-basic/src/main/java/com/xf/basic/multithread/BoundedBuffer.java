package com.xf.basic.multithread;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-06 23:49
 * @Description:
 * 使用内置锁（条件队列）
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public BoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException{
        while (isEmpty()) {
            wait();
        }
        V v = dotake();
        notifyAll();
        return v;
    }
}
