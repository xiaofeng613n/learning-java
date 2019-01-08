package com.xf.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

	@org.junit.Test
	public void runAsyncExample() {
		CompletableFuture cf = CompletableFuture.runAsync(() -> {
			assertTrue(Thread.currentThread().isDaemon());
			randomSleep(1);
		});
		assertFalse(cf.isDone());
		assertTrue(cf.isDone());
		randomSleep(2);
		assertTrue(cf.isDone());
	}

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
	@org.junit.Test
	public void runAfterBothExample() {
		String original = "Message";
		StringBuilder result = new StringBuilder();
		CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
					CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
					() -> result.append("done"));
		System.out.println(result);
		assertTrue("Result was empty", result.length() > 0);
	}

//	@org.junit.Test
//	public void completeExceptionallyExample() {
//		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
//				CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
//		CompletableFuture<String>exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
//		cf.completeExceptionally(new RuntimeException("completed exceptionally"));
//		assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
//		try {
//			cf.join();
//			fail("Should have thrown an exception");
//		} catch(CompletionException ex) { // just for testing
//			assertEquals("completed exceptionally", ex.getCause().getMessage());
//		}
//		assertEquals("message upon cancel", exceptionHandler.join());
//	}
//
//
//
//	@org.junit.Test
//	public void applyToEitherExample() {
//		String original = "Message";
//		CompletableFuture cf1 = CompletableFuture.completedFuture(original)
//				.thenApplyAsync(s -> delayedUpperCase(s));
//		CompletableFuture cf2 = cf1.applyToEither(
//				CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
//				s -> s + " from applyToEither");
//		assertTrue(cf2.join().endsWith(" from applyToEither"));
//	}
}
