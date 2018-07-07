package com.xf.zk;

/**
 * Created by xiao on 2018/7/6.
 */
public interface Callback {

	public <T> Object execute(T... args);
}
