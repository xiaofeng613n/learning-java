package com.xf.basic.offer;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-17 23:42
 * @Description: 02
 * 双重检查加锁(double-checked locking)
 * volatile关键字确保：当 uniqueInstance 变量初始化成Single实例时，多个线程正确处理uniqueInstance变量
 */
public class Singleton {

    private volatile static Singleton uniqueInstance;

    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
