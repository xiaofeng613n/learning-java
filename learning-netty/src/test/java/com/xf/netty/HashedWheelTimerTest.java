package com.xf.netty;

import io.netty.util.HashedWheelTimer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: xiaofeng
 * @Date: 2019-07-07 16:16
 * @Description:
 */
public class HashedWheelTimerTest {

    @Test
    public void test() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
        System.out.println("start:" + LocalDateTime.now().format(formatter));
        hashedWheelTimer.newTimeout(timeout -> {
            System.out.println("task :" + LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);
        Thread.sleep(5000);
    }
}
