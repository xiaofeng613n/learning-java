package com.xf.basic.queue;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2018/8/15.
 */
public class BufferedQueue {

	private List<ArrayQueue> list;

	private int writerIndex;

	private int queueNum;
	private int queueSize;
	public BufferedQueue(int queueSize,int queueNum) {

	}

	public void put(String s){
		ArrayQueue current = list.get(writerIndex);
		current.put(s); //阻塞的 // voliate + condition
		if( current.isFull()){
			//1.create a task
			//2.
			for (int i = 1; i < queueNum; i ++){
				int index = (writerIndex + 1) / queueNum;
				if( list.get(index).isEmpty() ){
					writerIndex = index;
					current = list.get(index);
					break;
				}
			}
		}
	}

	static class Task implements Runnable{

		private ArrayQueue queue;


		public Task(ArrayQueue queue){

		}

		@Override
		public void run() {

			//Contxt.getSink().process(queue);
			// queue 清空
		}
	}
}
