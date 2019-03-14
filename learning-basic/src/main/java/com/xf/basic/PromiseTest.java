package com.xf.basic;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class PromiseTest {

    public static void main(String[] args) {

    }

    public static void NettyFutureDemo(){


        EventExecutorGroup group = new DefaultEventExecutorGroup(2);
        Future<Integer> f = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        });

        ((io.netty.util.concurrent.Future<Integer>) f).addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {

            }
        });
    }
}
