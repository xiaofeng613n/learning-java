package com.xf.basic.event;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractActionQueue implements IActionQueue<Action>{

    protected Queue<Action> queue;

    protected Lock lock = new ReentrantLock();

    public AbstractActionQueue(Queue<Action> queue) {
        this.queue = queue;
    }

    @Override
    public void enqueue(Action action) {
        int queueSize = 0;

        lock.lock();
        try {
            queue.add(action);
            queueSize = queue.size();
        } finally {
            lock.unlock();
        }

        if ( queueSize ==1 ) {

        }
    }

    @Override
    public void dequeue(Action action) {
        Action nextAction = null;
        int queueSize = 0;
        lock.lock();
        try {
            queueSize = queue.size();
            Action tmp = queue.remove();

        } finally {
            lock.unlock();
        }

    }


    public abstract void doExecutor(Action action);





}
