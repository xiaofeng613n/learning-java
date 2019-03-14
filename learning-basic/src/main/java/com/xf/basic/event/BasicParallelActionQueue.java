package com.xf.basic.event;

import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

public class BasicParallelActionQueue extends AbstractActionQueue{

    private ThreadPoolExecutor executor;

    public BasicParallelActionQueue(ThreadPoolExecutor executor) {
        super(new LinkedList<Action>());
        this.executor = executor;
    }

    @Override
    public void doExecutor(Action action) {
        this.executor.execute(action);
    }
}
