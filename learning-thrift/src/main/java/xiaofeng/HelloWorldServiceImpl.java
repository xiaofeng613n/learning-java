package xiaofeng;

import org.apache.thrift.TException;

/**
 * Created by xiaofeng on 2018/11/19
 * Description:
 */
public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    @Override
    public String say(String username) throws TException {
        return "Hello " + username;
    }
}