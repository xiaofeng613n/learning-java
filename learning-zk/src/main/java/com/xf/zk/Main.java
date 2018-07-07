package com.xf.zk;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by xiao on 2018/7/6.
 */
public class Main {

	public static void main(String[] args) {

	}

	private void launchServer(){
		initFollowerThread();
	}

	private void initFollowerThread() {
		Callback leaderCallback = new Callback() {
			@Override
			public <T> Object execute(T[] args) {
				try{
					init();
				} catch (Exception e){

				}
				return null;
			}
		};
		FollowerRunnable follower = new FollowerRunnable(5000, leaderCallback);
		Thread thread = new Thread(follower);
		thread.setDaemon(true);
		thread.start();
		System.out.println("Successfully init Follower thread");
	}

	private void init() {
	}
}
