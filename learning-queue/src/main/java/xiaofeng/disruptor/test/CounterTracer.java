package xiaofeng.disruptor.test;

public interface CounterTracer
{
	public void start() ;

	public long getMilliTimeSpan() ;

	public boolean count() ;

	public void waitForReached();

	void printResult();
}
