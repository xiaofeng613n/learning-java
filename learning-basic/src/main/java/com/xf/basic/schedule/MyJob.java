package com.xf.basic.schedule;

import org.quartz.*;

/**
 * @Auther: xiaofeng
 * @Date: 2019/7/19 15:19
 * @Description:
 */
public class MyJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		System.out.println("MyJob");
		final JobDetail jobDetail = context.getJobDetail();
		String name = jobDetail.getKey().getName();
		String group = jobDetail.getKey().getName();
		String desc = jobDetail.getDescription();
		System.out.println("job: " +  name + " group:" + group + " desc:" + desc);

		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String myDescription = jobDataMap.getString("myDescription");
		int value = jobDataMap.getIntValue("myValue");
		System.out.println("myDescription: " + myDescription + " value: " +value);
	}
}