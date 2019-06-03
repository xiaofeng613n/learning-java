package com.xf.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-02 21:40
 * @Description:
 */
public class LockSupportTest {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        //main1();
//        main2();
//        main3();

        t2();
    }


    public static void t2() throws Exception
    {
        Thread t = new Thread(new Runnable()
        {
            private int count = 0;

            @Override
            public void run()
            {
                long start = System.currentTimeMillis();
                long end = 0;

                while ((end - start) <= 1000)
                {
                    count++;
                    end = System.currentTimeMillis();
                }

                System.out.println("after 1 second.count=" + count);

                //等待或许许可
                LockSupport.park();
                System.out.println("thread over." + Thread.currentThread().isInterrupted());

            }
        });

        t.start();

        Thread.sleep(2000);

        // 中断线程
        t.interrupt();


        System.out.println("main over");
    }


    public static void main1() {

        LockSupport.unpark(Thread.currentThread());
        System.out.println("c");
        LockSupport.park();
        System.out.println("d");
    }

    public static void main2() throws Exception {

        System.out.println("a");
        LockSupport.park();
        System.out.println("b");
        Thread.sleep(2000);
        LockSupport.unpark(Thread.currentThread());//无法结束
        System.out.println("c");
    }

    public static void main3() throws Exception {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
                LockSupport.park();
                System.out.println("b");
            }
        });

        t.start();

        TimeUnit.SECONDS.sleep(3);
        LockSupport.unpark(t);

    }
}
