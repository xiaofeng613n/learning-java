package com.xf.basic.event;

public abstract class AbstractParallelQueueExecutor implements IParallelQueueExecutor{


    public final static int DEFAULT_QUEUE_SIZE = Runtime.getRuntime().availableProcessors();

}
