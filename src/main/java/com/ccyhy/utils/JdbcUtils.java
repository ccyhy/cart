package com.ccyhy.utils;


import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author zqh
 */
public class JdbcUtils
{
    private static String driver=null;
    private static String url=null;
    private static String username=null;
    private static String password=null;
    private static String prop_name="db.properties";

    /**
     * 静态加载db.properties文件
     */
    static
    {
        try
        {
            /**
             * 读取db.properties文件中的数据库链接信息
             */
            InputStream in=JdbcUtils.class.getClassLoader().getResourceAsStream(prop_name);
            /**
             * 使用Properties对象加载
             */
            Properties prop=new Properties();
            prop.load(in);

            //prop是一个域对象，可以用getProperty来根据key获取对应的value
            /**
             * 获取数据库驱动
             */
            driver=prop.getProperty("driver");
            /**
             * 获取数据库url
             */
            url=prop.getProperty("url");
            /**
             * 数据库用户名
             */
            username=prop.getProperty("username");
            /**
             * 数据库链接密码
             */
            password=prop.getProperty("password");

            /**
             * 加载驱动，推荐使用Class.forName（驱动名方法）
             */
            Class.forName(driver);
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e);
        }
    }

    /**
     * 根据数据链接url，username，password，利用数据库驱动程序，获取一个数据库链接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url,username,password);
    }

    /**
     * 释放资源
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void release(Connection connection, Statement statement, ResultSet resultSet)
    {
        if(resultSet!=null)
        {
            try
            {
                resultSet.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            resultSet=null;
        }
        if(statement!=null)
        {
            try
            {
                statement.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(connection!=null)
        {
            try {
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
