package com.xf.basic;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by xiao on 2018/7/9.
 */
public class CompletableFutureTest {
	public static CompletableFuture<Integer> compute() {
		final CompletableFuture<Integer> future = new CompletableFuture<>();
		return future;
	}
	public static void main(String[] args) throws Exception {
//		test1();
		test2();
	}

	public static void test2(){
		final CompletableFuture<AtomicInteger> f = new CompletableFuture<>();
		f.thenApplyAsync(integer -> {
//			integer = integer + 2;
			integer.addAndGet(1);
			System.out.println(Thread.currentThread().getName() + ":" +integer);
			return integer;
		}).thenApplyAsync( integer -> {
//			integer = integer + 1;
			integer.addAndGet(1);
			System.out.println(Thread.currentThread().getName() + ":" +integer);
			return integer;
		}).thenAcceptAsync(integer -> System.out.println(Thread.currentThread().getName() + ":" +integer));

		f.complete(new AtomicInteger(10));

		try {
//			Thread.sleep(5000);
			AtomicInteger result = f.get();
			System.out.println(Thread.currentThread().getName() + ":" +result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}
	public static void test1(){
		final CompletableFuture<Integer> f = compute();
		class Client extends Thread {
			CompletableFuture<Integer> f;
			Client(String threadName, CompletableFuture<Integer> f) {
				super(threadName);
				this.f = f;
			}
			@Override
			public void run() {
				try {
					System.out.println(this.getName() + ": " + f.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		new Client("Client1", f).start();
		new Client("Client2", f).start();
		System.out.println("waiting");
		f.complete(100);
		//f.completeExceptionally(new Exception());
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}