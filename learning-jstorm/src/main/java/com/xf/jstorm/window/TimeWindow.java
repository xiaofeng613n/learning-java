package com.xf.jstorm.window;


import com.xf.jstorm.window.util.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Created by xiao on 2018/7/2.
 */
public class TimeWindow {

	private static Logger LOG = LoggerFactory.getLogger(TimeWindow.class);

	private long windowLength;
	private long slidingInterval;
	private long lag;

	private long nextWindowEndTs;

	private Queue<Event> eventQueue;

	private Consumer consumer;

	public TimeWindow(long windowLength,long slidingInterval,long lag,Consumer consumer){
		this.windowLength = windowLength;
		this.slidingInterval = slidingInterval;
		this.lag = lag;
		this.consumer = consumer;
		this.eventQueue = new ConcurrentLinkedQueue<>();
		this.nextWindowEndTs = System.currentTimeMillis() +  windowLength;
	}

	public void add(long ts,Event event){
		if( (nextWindowEndTs - windowLength) < ts) {
			this.eventQueue.add(event);
		} else {
			LOG.warn("event too late:{},{},{}",nextWindowEndTs - windowLength,ts,event);
		}
	}

	public void fireWaterMark(){
		eventQueue.stream().filter( event -> event.getTs() > windowLength );
//		Event event = eventQueue.stream().min((Long t1, Long t2) -> t1 > t2 ? 1 : 0).get();
	}
}
