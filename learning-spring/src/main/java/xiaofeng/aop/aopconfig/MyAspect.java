package xiaofeng.aop.aopconfig;

/**
 * Created by xiao on 2017/9/15.
 */
public class MyAspect
{
	public void mybefore()
	{
		System.out.println("前置增强");
	}
	public void  myafterReturning()
	{
		System.out.println("后置增强");
	}
}
