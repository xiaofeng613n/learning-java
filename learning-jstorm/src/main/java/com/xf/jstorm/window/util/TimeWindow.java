package com.xf.jstorm.window.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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
	//bizKey -> ( id -> ts)
	private Map<String,Map<String,Long>> tsMap;

	public TimeWindow(long windowLength,long slidingInterval,long lag,Consumer consumer){
		this.windowLength = windowLength;
		this.slidingInterval = slidingInterval;
		this.lag = lag;
		this.consumer = consumer;
		this.eventQueue = new ConcurrentLinkedQueue<>();
		this.nextWindowEndTs = System.currentTimeMillis() +  windowLength;
		this.tsMap = new HashMap<>();
	}

	public void add(long ts,Event event){
		if( (nextWindowEndTs - windowLength) < ts) {
			this.eventQueue.add(event);
		} else {
			LOG.warn("event too late:{},{},{}",nextWindowEndTs - windowLength,ts,event);
		}
	}

	public void fireWaterMark(){
		long waterMark = getWaterMark();
		long windowEndTs = nextWindowEndTs;
		while ( windowEndTs <= waterMark){
			List<Event> eventList = findEventsInWindow(windowEndTs - windowLength,windowEndTs);
			if( !eventList.isEmpty()){
				this.consumer.accept(eventList);
				windowEndTs += slidingInterval;
			} else {

			}
		}
		this.nextWindowEndTs = windowEndTs;
	}

	private List<Event> findEventsInWindow(long windowStartTs, long windowEndTs) {
		List<Event> eventList = new LinkedList<>();
		Iterator<Event> it = eventQueue.iterator();
		while (it.hasNext()) {
			Event event = it.next();
			long ts = event.getTs();
			if( ts <= windowStartTs){
				it.remove();
			} else if (windowStartTs < ts && ts < -windowEndTs) {
				eventList.add(event);
			}
		}
		return eventList;
	}

	private long getWaterMark() {
		List<Long> tsList = new ArrayList<>();
		for (Map.Entry<String, Map<String, Long>> entry : tsMap.entrySet()) {
			entry.getValue().values().forEach( ts -> tsList.add(ts) );
		}
		return tsList.stream().min( (Long t1, Long t2) -> t1 > t2 ? 1: 0).get() - lag;
	}


}
