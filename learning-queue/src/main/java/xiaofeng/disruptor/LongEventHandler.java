package xiaofeng.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by xiaofeng on 2017/9/29
 * Description:
 */
public class LongEventHandler implements EventHandler<LongEvent>
{
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception
	{
		System.out.println("Event:" + event);
	}
}