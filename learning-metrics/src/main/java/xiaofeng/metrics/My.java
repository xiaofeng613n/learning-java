package xiaofeng.metrics;

/**
 * Created by xiaofeng on 2017/11/27
 * Description:
 */
public class My implements MyMBean {

    private String name;

    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public void stop() {
        System.out.println("end");
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}