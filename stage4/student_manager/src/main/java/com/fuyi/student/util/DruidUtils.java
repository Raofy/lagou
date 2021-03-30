package com.fuyi.student.util;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mysql.jdbc.Statement;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DruidUtils {

    //定义成员变量dataSource
    static DataSource  dataSource=null;

    //2.获取数据库连接池对象 初始化连接
    static {

        Properties properties=new Properties();
        //读取配置文件
        InputStream resourceAsStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        //载入流文件
        try {
            properties.load(resourceAsStream);
            //工程类创建dtaSource
             dataSource = DruidDataSourceFactory.createDataSource(properties);

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    //返回连接池的对象
    public static DataSource getDataSource(){
        return  dataSource;
    }
    //返回连接Connecton
    public  static  Connection getConnection(){
        try {
            return  dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    //关闭资源
    public static void close(Statement stament, Connection con, ResultSet set) {
        DbUtils.closeQuietly(set);
        DbUtils.closeQuietly(con);
        DbUtils.closeQuietly(stament);

    }

    /*重载*/
    public static void close(Statement stament, Connection con) {
        DbUtils.closeQuietly(con);
        DbUtils.closeQuietly(stament);
    }

}
