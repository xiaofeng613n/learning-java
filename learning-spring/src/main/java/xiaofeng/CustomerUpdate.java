package xiaofeng;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by xiao on 2017/1/30.
 */
public class CustomerUpdate extends SqlUpdate {

    public void updateCustomer(DataSource dataSource) {
        setDataSource(dataSource);
        setSql("update customer set name =? where id=?");
        declareParameter(new SqlParameter(Types.CHAR));
        declareParameter(new SqlParameter(Types.INTEGER));
        compile();
    }
}
