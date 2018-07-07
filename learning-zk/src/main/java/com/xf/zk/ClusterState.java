package com.xf.zk;

/**
 * Created by xiao on 2018/7/6.
 */
public interface ClusterState {

	public void tryToBeLeader();

	boolean leaderExisted();

	boolean isLeader();
}
