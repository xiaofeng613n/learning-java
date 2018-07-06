package com.xf.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by xiao on 2018/7/1.
 */
public class LogTest {

	public static void main(String[] args) {
		MDC.put("trackId","xx");
		Logger LOG = LoggerFactory.getLogger(LogTest.class);
		LOG.info("log:{}",1);
		MDC.remove("trackId");
		LOG.info("log:{}",2);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
//				MDC.put("trackId","xx");
				Logger LOG = LoggerFactory.getLogger(LogTest.class);
				LOG.info("log:{}",1);
			}
		});
		thread.start();
	}
}
