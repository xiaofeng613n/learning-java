package com.xf.basic;

import java.sql.*;

/**
 * @Auther: xiaofeng
 * @Date: 2019-06-20 15:20
 * @Description:
 */

public class DBTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sampdb?charactorEncoding=utf8";
        String user = "root";
        String password = "123456";

        String drv = "com.mysql.jdbc.Driver"; //声明mysql驱动

        try {
            //1.动态加载mysql驱动Class.forName(drv).newInstance()
            Class.forName(drv);
            //2.数据库管理驱动, 打开一个连接
            Connection connection = DriverManager.getConnection(url, user, password);
            //3.使用Connection接口的createStatement方法创建一个Statement语句对象
            Statement stm = connection.createStatement();
            //4.执行查询语句 executeQuery / executeUpdate / execute
            String sql1 = "select * from student where name like 'G%'";
            ResultSet r = stm.executeQuery(sql1);
            while (r.next()) {
                System.out.print("name: " + r.getString(1));
                System.out.println("\r sex: " + r.getString(2));
            }

            String sql2 = "delete from a where id = 11";
            int n = stm.executeUpdate(sql2); //返回执行行数

            // PreparedStatement
            String sql3 = "select * from student where name like ?";
            PreparedStatement pstm = (PreparedStatement) connection.prepareStatement(sql3);
            pstm.setString(1, "A%");
            ResultSet r2 = pstm.executeQuery();
            while (r2.next()) {
                System.out.print("name: " + r2.getString(1));
                System.out.println("\r sex: " + r2.getString(2));
            }
            // CallableStatement
            String sql4 = "";
            CallableStatement cstm = (CallableStatement) connection.prepareCall(sql4);

            //关闭连接
            r.close();
            r2.close();
            stm.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

