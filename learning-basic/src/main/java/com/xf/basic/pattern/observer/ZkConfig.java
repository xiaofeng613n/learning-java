package com.xf.basic.pattern.observer;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by xiao on 2018/8/15.
 */
public class ZkConfig extends Observable {

	private CuratorFramework client;

	private List<String> targetApp;

	private Consumer consumer;

	public ZkConfig(List<String> targetApp, Consumer consumer){

		this.consumer = consumer;

	}

	private String loadData(){
		return "";
	}

	public void init(){

		register(new IObserver() {
			@Override
			public void notified(String key, Object value) {
				String data = ZkConfig.this.loadData();
				consumer.accept(data);
			}
		});


		PathChildrenCache childrenCache = new PathChildrenCache(client,"",true);
		childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
				String path = event.getData().getPath();
				if( !targetApp.contains(path)){
					return;
				}
				switch (event.getType()){
					case CHILD_ADDED:
						ZkConfig.this.notify(path,event.getData());
						break;
					case CHILD_UPDATED:

						break;

				}
			}
		});

		try {
			childrenCache.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		ZkConfig zkConfig = new ZkConfig(new ArrayList<>(), new Consumer() {
			@Override
			public void accept(Object o) {


			}
		});
		zkConfig.init();

	}
}
