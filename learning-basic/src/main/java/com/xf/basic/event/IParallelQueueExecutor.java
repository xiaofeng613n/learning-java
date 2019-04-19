package com.xf.basic.event;

public interface IParallelQueueExecutor {

    void execute(String topic, Runnable command);

    void stop();
}
