package com.xf.basic.schedule;

import com.xf.basic.DateUtil;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaofeng
 * @Date: 2019/7/19 11:00
 * @Description:
 */
public class TestTimer {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(DateUtil.getDate());
				System.out.println("running timer task1");
			}
		}, 1000, 1000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(DateUtil.getDate());
				System.out.println("running timer task2");
				throw new RuntimeException();
			}
		}, 1000, 1000);

		try {
			TimeUnit.SECONDS.sleep(10);
			timer.cancel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}