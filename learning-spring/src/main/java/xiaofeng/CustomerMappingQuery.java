package xiaofeng;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by xiao on 2017/1/30.
 */
public class CustomerMappingQuery extends MappingSqlQuery {

    CustomerMappingQuery(DataSource dataSource) {
        super(dataSource,"SELECT id,name from customer where id =?");
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
        compile();
    }
    @Override
    protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();
        customer.setId((Integer) resultSet.getObject("id"));
        customer.setName(resultSet.getString("name"));
        return customer;
    }
}
