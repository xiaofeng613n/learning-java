package com.xf.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Auther: xiaofeng
 * @Date: 2019/8/1 10:53
 * @Description:
 */
public class ShareLock implements Lock {

	public ShareLock( int num) {
		sync = new Sync(num);
	}

	static class Sync extends AbstractQueuedSynchronizer {

		private int shareNum;

		public Sync(int shareNum) {
			this.shareNum = shareNum;
		}

		@Override
		protected boolean tryAcquire(int arg) {
			int c = getState();
			if( c < shareNum && compareAndSetState( c, c + 1) ) {
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			int c = getState();

			if ( c > 0 && compareAndSetState( c, c - 1)) {
				return true;
			}
			return false;
		}
	}

	private final Sync sync;

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		ShareLock lock = new ShareLock(3);

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try{
					System.out.println("thread 1 get lock");
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("thread 1 release lock");
					lock.unlock();
				}
			}
		});
		t1.start();


		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try{
					System.out.println("thread 2 get lock");
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("thread 2 release lock");
					lock.unlock();
				}
			}
		});
		t2.start();

		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try{
					System.out.println("thread 3 get lock");
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("thread 3 release lock");
					lock.unlock();
				}
			}
		});
		t3.start();

		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				try{
					System.out.println("thread 4 get lock");
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("thread 4 release lock");
					lock.unlock();
				}
			}
		});
		t4.start();


		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
	}
}