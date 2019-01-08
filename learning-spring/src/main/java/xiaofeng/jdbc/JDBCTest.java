package xiaofeng.jdbc;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2017/3/1.
 */
public class JDBCTest
{
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
		DataSource dataSource = (DataSource) ac.getBean("dataSource");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<Map<Integer,String>> list = jdbcTemplate.query("select id,name from customer where id > 0", new RowMapper<Map<Integer,String>>()
		{
			@Override
			public Map mapRow(ResultSet resultSet, int i) throws SQLException {
				Map<Integer, String> map = new HashMap<>();
				map.put(resultSet.getInt("id"),resultSet.getString("name"));
				return map;
			}
		});



	}
}
