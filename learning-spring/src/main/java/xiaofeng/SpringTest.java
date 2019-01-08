package xiaofeng;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import xiaofeng.aop.BussinessServiceImp;
import xiaofeng.aop.IBussinessService;
import xiaofeng.aop.LogAfterReturningAdvice;
import xiaofeng.aop.LogBeforeAdvice;
import xiaofeng.tag.ServerBean;

/**
 * Created by xiao on 2017/1/26.
 */
public class SpringTest {

    public static void main(String[] args) {

     /*   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:Test.xml");
        Student student = (Student) applicationContext.getBean(Student.class);
        System.out.println(student.getId()+ " " + student.getName());*/

        ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\MySpace\\CodeSpace\\ideaSpace\\learning-java\\learning-spring\\src\\main\\resources\\application.xml");
        /*IOC*/
        Student student = (Student) ac.getBean(Student.class);
        System.out.println(student.toString());

        /*AOP ProxyFactoryBean*/
        IBussinessService bussinessServiceImpl =  ac.getBean("methodProxy",IBussinessService.class);
        bussinessServiceImpl.bussiness();

        /*AOP ProxyFactory*/
        ProxyFactory proxyFactory = ac.getBean(ProxyFactory.class);
        proxyFactory.addAdvice(new LogBeforeAdvice());
        proxyFactory.addAdvice(new LogAfterReturningAdvice());

        IBussinessService bussinessService = (IBussinessService) proxyFactory.getProxy();
        bussinessService.bussiness();


        /* tag */
        ServerBean serverBean = (ServerBean) ac.getBean("testServer");
        System.out.println(serverBean.toString());
    }
}
