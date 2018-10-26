package xiaofeng.disruptor.dsl;

/**
 * Created by xiaofeng on 2017/11/22
 * Description:
 */
import com.lmax.disruptor.EventHandler;
//import com.lmax.disruptor.util.PaddedLong;

import java.util.concurrent.CountDownLatch;

public final class ValueAdditionEventHandler implements EventHandler<ValueEvent> {

    private final PaddedLong value = new PaddedLong();
    private long count;
    private CountDownLatch latch;

    public long getValue() {
        return value.get();
    }

    public void reset(final CountDownLatch latch, final long expectedCount) {
        value.set(0L);
        this.latch = latch;
        count = expectedCount;
    }

    @Override
    public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
        value.set(value.get() + event.getValue());

        if (count == sequence) {
            latch.countDown();
        }
    }

}