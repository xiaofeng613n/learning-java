package xiaofeng.aop.proxy;

/**
 * Created by xiao on 2017/9/15.
 */
public class Human implements Sleepable
{
	public void sleep()
	{
		System.out.println("睡觉中...！");
	}
}