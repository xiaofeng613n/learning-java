package com.xf.basic.event;

public interface IActionQueue<T> {

    void enqueue(T t);

    void dequeue(T t);
}
