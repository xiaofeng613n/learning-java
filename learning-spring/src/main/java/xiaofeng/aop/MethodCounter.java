package xiaofeng.aop;



import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by xiao on 2017/1/30.
 */
public class MethodCounter implements Serializable {

    private HashMap<String, Integer> map = new HashMap<>();
    private int allCount;

    protected void count(Method method) {
        count(method.getName());
    }

    protected void count(String methodName) {
        Integer i = map.get(methodName);
        i = (i != null) ? new Integer((i.intValue() + 1)) : new Integer(1);
        map.put(methodName, i);
        ++ allCount;
    }

    public int getCalls(String methodName) {
        Integer i = map.get(methodName);
        return (i != null ? i.intValue() : 0);
    }

    public int getCalls() {
        return allCount;
    }

    @Override
    public boolean equals(Object other) {
        return (other != null && other.getClass() == this.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
