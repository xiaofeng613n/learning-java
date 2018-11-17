package xiaofeng.metrics;

/**
 * Created by xiaofeng on 2017/11/27
 * Description:
 */
public interface MyMBean{

    public void start();
    public void stop();
    public void setName(String name);
    public String getName();

}