package com.xf.basic.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class BasicParallelActionExecutor extends AbstractParallelQueueExecutor {

    private ThreadPoolExecutor[] pools;

    private String prefix;

    private Map<String, IActionQueue<Action>> parallelQueue = new ConcurrentHashMap<>();



    @Override

    public void execute(String topic, Runnable command) {

    }

    @Override
    public void stop() {

    }
}
