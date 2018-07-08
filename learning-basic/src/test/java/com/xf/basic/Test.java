package com.xf.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by xiao on 2018/7/9.
 */
public class Test {

	@org.junit.Test
	public void completedFutureExample(){
		CompletableFuture cf = CompletableFuture.completedFuture("message");
		assertTrue(cf.isDone());
		assertEquals("message", cf.getNow(null));
	}

//	@org.junit.Test
//	public void runAsyncExample() {
//		CompletableFuture cf = CompletableFuture.runAsync(() -> {
//			assertTrue(Thread.currentThread().isDaemon());
//			randomSleep();
//		});
//		assertFalse(cf.isDone());
//		sleepEnough();
//		assertTrue(cf.isDone());
//	}
//
	private void randomSleep(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void thenApplyExample() {
		CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(s -> {
			assertFalse(Thread.currentThread().isDaemon());
			randomSleep(10);
			return s.toUpperCase();
		});
//		assertEquals("MESSAGE", cf.getNow(null));
		System.out.println(cf.getNow(null));
	}

	@org.junit.Test
	public void thenApplyAsyncExample() {
		CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
			assertTrue(Thread.currentThread().isDaemon());
			randomSleep(5);
			return s.toUpperCase();
		});
//		assertEquals("MESSAGE", cf.getNow(null));
		System.out.println(cf.getNow(null));
		System.out.println(cf.join());
	}

	@org.junit.Test
	public void thenAcceptExample() {
		StringBuilder result = new StringBuilder();
		CompletableFuture.completedFuture("thenAccept message")
				.thenAccept(s -> result.append(s));
		assertTrue("Result was empty", result.length() > 0);
		System.out.println(result);
	}
}
