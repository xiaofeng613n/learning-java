package com.xf.basic.pattern.observer;

/**
 * Created by xiao on 2018/8/15.
 */
public interface IObserver {

	void notified(String key,Object value);
}
