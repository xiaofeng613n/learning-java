package com.xf.basic.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaofeng
 * @Date: 2019/7/19 11:39
 * @Description:
 */
public class TestQuartz {

	public static void main(String[] args) throws SchedulerException, InterruptedException {

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();

		JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
				.withIdentity("job1", "group1")
				.build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?"))
				.build();

		Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                // 从当前时间的下 1 秒开始执行，默认为立即开始执行（.startNow()）
                .startAt(DateBuilder.evenSecondDate(new Date()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1) // 每隔 1 秒执行 1 次
                        .withRepeatCount(2))      // 重复执行 2 次，一共执行 3 次
                .build();

		//初始化参数传递到 job
		jobDetail.getJobDataMap().put("myDescription", "Hello Quartz");
		jobDetail.getJobDataMap().put("myValue", 18);


		scheduler.scheduleJob(jobDetail, trigger);

		scheduler.getListenerManager().addJobListener(new JobListener() {
			@Override
			public String getName() {
				return "counter";
			}

			@Override
			public void jobToBeExecuted(JobExecutionContext context) {

			}

			@Override
			public void jobExecutionVetoed(JobExecutionContext context) {

			}

			@Override
			public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
				System.out.println("job " + context.getJobDetail().getKey().getName() + " executed");
			}
		}, KeyMatcher.keyEquals(new JobKey("job1", "group1")));



		scheduler.start();

		TimeUnit.SECONDS.sleep(20);

		scheduler.shutdown(true);
	}

}