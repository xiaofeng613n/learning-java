package xiaofeng;

import javax.sql.DataSource;

/**
 * Created by xiao on 2017/1/30.
 */
public class JDBCTest {
    public static void main(String[] args) {

        DataSource dataSource = null;
    }

   /* public Customer getCustomer(Integer id) {
        CustomerMappingQuery customerMappingQuery = new CustomerMappingQuery(dataSource);
        Object[] params = new Object[1];
        params[0] = id;
        List customers =  customerMappingQuery.execute(params);
        if (customers != null && customers.size() > 0)
        {
            return (Customer) customers.get(0);
        }
        else
        {
            return null;
        }
    }*/

   /* public int updateCustomer(int id, String name) {
        Object[] params = new  Object[]{new Integer(id),new String(name)}
        CustomerUpdate customerUpdate = new CustomerUpdate();
        customerUpdate.updateCustomer(dataSource);
    }*/
}
