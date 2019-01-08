package xiaofeng.aop;

import org.springframework.aop.ThrowsAdvice;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Created by xiao on 2017/1/30.
 */
public class LogThrowsAdvice implements ThrowsAdvice  {

//    public void afterThrowing(IOException ex) throws Throwable{
//        count(IOException.class.getName());
//    }
//
//    public void afterThrowing(UncheckedIOException ex) throws Throwable{
//        count(UncheckedIOException.class.getName());
//    }

}
