package com.xf.basic.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-10 22:37
 * @Description:
 */
public class DateUtil {

//    1、UTC（协调世界时）又称世界统一时间、世界标准时间、国际协调时间。
//    2、GMT（格林尼治标准时间）一般指世界时
//    3、CST（中央标准时间）可视为美国、澳大利亚、古巴或中国的标准时间。
//
//    CST可以为如下4个不同的时区的缩写：
//    美国中部时间：Central Standard Time (USA) UT-6:00
//    澳大利亚中部时间：Central Standard Time (Australia) UT+9:30
//    中国标准时间：China Standard Time UT+8:00
//    古巴标准时间：Cuba Standard Time UT-4:00

//    Date：
//    尽管 Date 类打算反映协调世界时 (UTC)，但无法做到如此准确，这取决于 Java 虚拟机的主机环境。
//    一些计算机标准是按照格林威治标准时 (GMT) 定义的，格林威治标准时和世界时 (UT) 是相等的。
//    UTC 和 UT 的区别是：UTC 是基于原子时钟的，UT 是基于天体观察的，两者在实际应用中难分轩轾。
//    闰秒是根据需要引入 UTC 的，以便把 UTC 保持在 UT1 的 0.9 秒之内，UT1 是应用了某些更正的 UT 版本。
//
//    DateFormat：
//    将日期表示为 Date 对象，或者表示为从 GMT（格林尼治标准时间）1970 年 1 月 1 日 00:00:00 这一刻开始的毫秒数
//


    public static void main(String[] args) throws Exception {
        //当前时间Date
        Date now = new Date();
        System.out.println(now);
//Wed Jan 31 23:32:03 GMT+08:00 2018

//例如我的环境时区为：(UTC+08:00)北京，重庆，香港特别行政区，乌鲁木齐（+0800）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        System.out.println(sdf.getTimeZone());
        System.out.println(sdf.format(now));


        String utcTime = "2018-01-31T14:32:19Z";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//设置时区UTC
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
//格式化，转当地时区时间
        Date after = df.parse(utcTime);
        System.out.println(after);
//Wed Jan 31 22:32:19 GMT+08:00 2018


        df.applyPattern("yyyy-MM-dd HH:mm:ss");
//默认时区
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(df.format(after));
//2018-01-31 22:32:19
    }
}
