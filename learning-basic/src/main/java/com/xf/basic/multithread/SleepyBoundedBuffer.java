package com.xf.basic.multithread;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-06 23:43
 * @Description:
 * 通过轮询和休眠来实现阻塞，支持中断
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public SleepyBoundedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(1000);
        }
    }


    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return dotake();
                }
            }
            Thread.sleep(1000);
        }
    }
}
