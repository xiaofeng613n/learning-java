package xiaofeng.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by xiaofeng on 2017/9/29
 * Description:
 */
public class LongEventProducerWithTranslator
{
	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
			new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
				public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
					event.setValue(bb.getLong(0));
				}
			};

	public void onData(ByteBuffer bb)
	{
		ringBuffer.publishEvent(TRANSLATOR, bb);
	}
}