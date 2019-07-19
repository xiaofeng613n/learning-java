package com.xf.basic.schedule;

import com.xf.basic.DateUtil;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaofeng
 * @Date: 2019/7/19 11:18
 * @Description:
 */
public class TestScheduleExecutorService {

	public static void main(String[] args) {

		//scheduleAtFixedRate 间隔指的是连续两次任务开始执行的间隔
		// 当任务执行的时间大于间隔，不会新起线程执行,而是等待该线程执行完毕


		//executeFixedDelay 间隔指的是连续上次执行完成和下次开始执行之间的间隔。

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

				String id = UUID.randomUUID().toString();
				System.out.println(id + "start " + DateUtil.getDate());
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(id + "end " + DateUtil.getDate());
			}
		}, 0, 1, TimeUnit.SECONDS);


		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}