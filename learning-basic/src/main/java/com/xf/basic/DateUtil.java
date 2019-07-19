package com.xf.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Auther: xiaofeng
 * @Date: 2019/6/4 10:49
 * @Description:
 */
public class DateUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String BEIJING_TIME_ZONE = "GMT+8";

	public static String getDate(Date date,String timeZone){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return simpleDateFormat.format(date);
	}

	public static String getDefaultDate(Date date ){
		return getDate( date, BEIJING_TIME_ZONE);
	}

	public static String getDefaultDate(){
		Date date = new Date();
		return getDefaultDate( date);
	}

	public static String getDate(long seconds){
		Date date = new Date(seconds * 1000);
		return getDefaultDate( date);
	}

	public static String getDate(){
		Date date = new Date(System.currentTimeMillis());
		return getDefaultDate( date);
	}

	public static Date getDate(String dateStr, String pattern) throws ParseException {
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse(dateStr);
		return date;
	}
}