package com.xf.zk;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiao on 2018/7/6.
 */
public class FollowerRunnable implements Runnable{

	private Callback leaderCallback;
	private long sleepTime ;
	private ClusterState masterClusterState;

	private volatile boolean state = true;

	public FollowerRunnable(final ClusterState masterClusterState,int sleepTime, Callback leaderCallback) {
		this.masterClusterState = masterClusterState;
		this.sleepTime = sleepTime;
		this.leaderCallback = leaderCallback;


	}

	@Override
	public void run() {
		while (state){
			try {
				TimeUnit.MILLISECONDS.sleep(sleepTime);
				if ( !masterClusterState.leaderExisted()) {
					masterClusterState.tryToBeLeader();
					continue;
				}
				boolean isLeader = masterClusterState.isLeader();
				if( isLeader ){
					leaderCallback.execute();
					state = false;
					Runtime.getRuntime().halt(1);
				}

			} catch (InterruptedException e) {
				//e.printStackTrace();
				continue;
			} catch (Exception e){

			}
		}
		System.out.println("FollowerRunnable close!");
	}
}
