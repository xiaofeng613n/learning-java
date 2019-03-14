package com.xf.basic.event;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FastParallelActionQueue extends AbstractActionQueue{

    private ThreadPoolExecutor singleThreadExecutor;

    public FastParallelActionQueue(ThreadPoolExecutor singleThreadExecutor) {
        super(new LinkedList<Action>());
        if ( singleThreadExecutor.getCorePoolSize() != 1 ) {
            singleThreadExecutor.setCorePoolSize(1);
            singleThreadExecutor.setMaximumPoolSize(1);
            singleThreadExecutor.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
        }
        this.singleThreadExecutor = singleThreadExecutor;
    }

    @Override
    public void doExecutor(Action action) {
        if ( singleThreadExecutor.getQueue().isEmpty()) {
            singleThreadExecutor.execute(action);
        } else {
            singleThreadExecutor.getQueue().offer(action);
        }

    }
}
