package com.xf.basic;

/**
 * Created by xiao on 2018/8/17.
 */
import java.lang.reflect.Method;
public class TestHotSwap {
	public static void main(String[] args) throws Exception {
		//开启线程，如果class文件有修改，就热替换
		Thread t = new Thread(new MonitorHotSwap());
		t.start();
	}
}
