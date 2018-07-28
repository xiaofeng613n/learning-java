package com.xf.jstorm.window;


import com.xf.jstorm.window.util.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by xiao on 2018/7/2.
 */
public class TimeWindow<R> {

	private static Logger LOG = LoggerFactory.getLogger(TimeWindow.class);

	private long windowLength;
	private long slidingInterval;
	private long lag;

	private long nextWindowEndTs;

	private Map<String,Queue<R>> map;

	private Function<Event,R> function;

	public TimeWindow(long windowLength,long slidingInterval,long lag,Function<Event,R> function){
		this.windowLength = windowLength;
		this.slidingInterval = slidingInterval;
		this.lag = lag;
		this.function = function;
		this.nextWindowEndTs = System.currentTimeMillis() +  windowLength;
		this.map = new HashMap<>();
	}

	public void add(long ts,Event t){
		if( (nextWindowEndTs - windowLength) < ts) {
			R r = function.apply(t);
			if( r != null){
				Queue<R> queue = map.get(t.getKey());
				if( queue == null ){
					queue = new LinkedBlockingQueue<>();
					map.put(t.getKey(),queue);
				}
				queue.add(r);
			}
		} else {
			LOG.warn("event too late:{},{},{}",nextWindowEndTs - windowLength,ts,t);
		}
	}

	public void fireWaterMark(){
//		eventQueue.stream().filter( event -> event.getTs() > windowLength );
//		Event event = eventQueue.stream().min((Long t1, Long t2) -> t1 > t2 ? 1 : 0).get();
	}
}
