package com.xf.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: xiaofeng
 * @Date: 2019/7/31 15:42
 * @Description:
 */
public  class PrintThreadTest {

	public static void main(String[] args) throws InterruptedException {

		Lock lock = new ReentrantLock();
		Condition printA = lock.newCondition();
		Condition printB = lock.newCondition();
		Condition printC = lock.newCondition();
		PrintThread a = new PrintThread("A", lock, printA, printB);
		PrintThread b = new PrintThread("B", lock, printB, printC);
		PrintThread c = new PrintThread("C", lock, printC, printA);


		a.start();
		b.start();
		c.start();

		TimeUnit.SECONDS.sleep(2);
		lock.lock();
		try {
			printA.signal();
		} finally {
			lock.unlock();
		}

		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
	}


	 static class PrintThread extends Thread{

		private String string;

		private Lock lock;

		private Condition condition1;
		private Condition condition2;

		public PrintThread(String string,  Lock lock, Condition condition1, Condition condition2) {
			super(string);
			this.string = string;
			this.lock = lock;
			this.condition1 = condition1;
			this.condition2 = condition2;
		}

		@Override
		public void run() {
			lock.lock();
			try{
				while (true) {
					condition1.await();
					System.out.println(Thread.currentThread().getName() + ":" + string);
					condition2.signal();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				lock.unlock();
			}



		}
	}
}

