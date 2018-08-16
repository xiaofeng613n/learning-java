package com.xf.basic.queue;



import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiao on 2018/8/15.
 */
public class BufferedQueue {

	private ArrayQueue[] queueArray;

	private int writerIndex;

	private int queueNum;
	private int queueSize;

	private Executor executor;

	public BufferedQueue(int queueSize, int queueNum, Executor executor) {
		this.queueSize = queueSize;
		this.queueNum = queueNum;
		this.executor = executor;

		queueArray = new ArrayQueue[queueNum];

		for (int i = 0; i < queueNum; i ++){
			queueArray[i] = new ArrayQueue(queueSize);
		}
	}

	public void put(String s){
		ArrayQueue current = queueArray[writerIndex];
		current.put(s); //阻塞的 // voliate + condition
		if( current.isFull()){
			//1.create a task
			executor.execute(new Task(current));
			//executor.execute(new SinkTask());
			//2.
			for (int i = 1; i < queueNum; i ++){
				int index = (writerIndex + i) % queueNum;
				System.out.println("next index:" + index);
				if( queueArray[index].isEmpty() ){
					System.out.println("index:"+ writerIndex);
					writerIndex = index;
					System.out.println("index:"+ writerIndex);
//					current = list.get(index);
					break;
				}
			}
		}
	}

	static class Task implements Runnable{

		private ArrayQueue queue;


		public Task(ArrayQueue queue){
			this.queue = queue;
		}

		@Override
		public void run() {

			//Contxt.getSink().process(queue);
			// queue 清空

//			try {
//				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			for (int i = 0; i < queue.getCapacity(); i ++){
				//System.out.println("consumer:"+queue.get(i));
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			queue.release();
		}
	}


	public static void main(String[] args) {
		Executor executor = Executors.newSingleThreadExecutor();

		BufferedQueue bufferedQueue = new BufferedQueue(5,3,executor);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < Integer.MAX_VALUE; i ++){
					bufferedQueue.put(String.valueOf(i));
				}
			}
		});
		thread.start();

		try {
			TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
