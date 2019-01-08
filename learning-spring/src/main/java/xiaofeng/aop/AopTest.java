package xiaofeng.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xiaofeng.aop.proxy.Sleepable;

/**
 * Created by xiao on 2017/9/15.
 */
public class AopTest
{
	public static void main(String[] args)
	{
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		Sleepable humanProxy = (Sleepable) ac.getBean("humanProxy");
		humanProxy.sleep();
/*
		Service service = (Service) ac.getBean("Service");
		service.service();*/
	}
}
