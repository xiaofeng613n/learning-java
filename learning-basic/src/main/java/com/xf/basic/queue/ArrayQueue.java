package com.xf.basic.queue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaofeng on 2018/8/16
 * Description:
 */
public class ArrayQueue<E> {

    private Object[] elementData;

    private int putIndex;

    private volatile boolean isLock;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public ArrayQueue(int initialCapacity){
        this.elementData = new Object[initialCapacity];
    }

    public void put(E e){
        if( isFull() ){
            lock.lock();
            System.out.println("put lock");
            try {
                System.out.println("start wait");
                condition.await();
                System.out.println("end wait");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("put  unlock");
            }
        }
        elementData[putIndex] = e;
        if (++putIndex == elementData.length)
            putIndex = 0;
    }

    public boolean isFull() {
        return putIndex == elementData.length -1;
    }

    public boolean isEmpty() {
        return putIndex == 0;
    }
    public E get(int index){
        return (E) elementData[index];
    }

    public int getCapacity(){
        return elementData.length;
    }

    public void release(){
        putIndex = 0;
        lock.lock();
        System.out.println("release lock");
        try {
            condition.signal();
        } finally {
            lock.unlock();
            System.out.println("release unlock");
        }
    }

    public static void main(String[] args) {
        ArrayQueue<String> queue = new ArrayQueue<>(5);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               for(int i = 0; i < Integer.MAX_VALUE; i ++){
                   queue.put(String.valueOf(i));
                   System.out.println(i);
               }
            }
        });
        thread.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if( queue.isFull()){
                        for (int i = 0; i < queue.getCapacity(); i ++){
                            System.out.println(queue.get(i));
                        }
                        queue.release();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}