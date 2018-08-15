package com.xf.basic.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2018/8/15.
 */
public class Observable {

	private List<IObserver> observerList = new ArrayList<>();

	public void register(IObserver observer){
		observerList.add(observer);
	}

	public void notify(String key, Object value) {
		for (IObserver iObserver : observerList) {
			iObserver.notified(key,value);
		}
	}
}
