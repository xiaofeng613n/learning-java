package com.xf.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiao on 2018/7/1.
 */
public class LogTest {

	public static void main(String[] args) {
		Logger LOG = LoggerFactory.getLogger(LogTest.class);
		LOG.info("log:{}",1);
	}
}
