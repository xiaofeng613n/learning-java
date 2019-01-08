package xiaofeng.aop.proxy;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by xiao on 2017/9/15.
 */
public class SleepHelper implements MethodBeforeAdvice,AfterReturningAdvice
{

	public void before(Method method, Object[] arguments, Object target) throws Throwable
	{
		System.out.println("睡觉前");
	}

	public void afterReturning(Object rturnValue, Method method, Object[] arguments, Object target) throws Throwable
	{
		System.out.println("睡觉后");
	}

}