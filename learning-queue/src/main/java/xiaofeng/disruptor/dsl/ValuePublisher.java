package xiaofeng.disruptor.dsl;

/**
 * Created by xiaofeng on 2017/11/22
 * Description:
 */
import java.util.concurrent.CyclicBarrier;

import com.lmax.disruptor.RingBuffer;

public final class ValuePublisher implements Runnable {
    private final CyclicBarrier cyclicBarrier;
    private final RingBuffer<ValueEvent> ringBuffer;
    private final long start;
    private final long end;

    public ValuePublisher(
            final CyclicBarrier cyclicBarrier,
            final RingBuffer<ValueEvent> ringBuffer,
            final long start,
            final long end) {
        this.cyclicBarrier = cyclicBarrier;
        this.ringBuffer = ringBuffer;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
            for (long i = start; i < end; i++) {
                long sequence = ringBuffer.next();
                ValueEvent event = ringBuffer.get(sequence);
                event.setValue(i);
                ringBuffer.publish(sequence);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}