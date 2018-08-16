package com.xf.basic;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by xiaofeng on 2018/8/15
 * Description:
 */
public class QueueTest {
    public static void main(String[] args) {
//        Runnable runnable
//        Thread thread = new Thread()
    }


    static class Task implements Runnable{

        private ArrayBlockingQueue<Integer> queue;

        public Task(ArrayBlockingQueue queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0;i < 100; i++ ){
                try {
                    queue.put(new Integer(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("");
            }
        }
    }
}