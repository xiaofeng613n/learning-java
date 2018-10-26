package xiaofeng.disruptor.test;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Created by xiaofeng on 2017/10/7
 * Description:
 */
public class DirectingPublisher implements EventPublisher
{
	private TestHandler handler;
	private TestEvent event = new TestEvent();


	public DirectingPublisher(TestHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void publish(int data)
	{
		event.setValue(data);
		handler.process(event);
	}

	@Override
	public void start()
	{

	}

	@Override
	public void stop()
	{

	}
}