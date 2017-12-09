package com.ccyhy.dao;

import com.ccyhy.bean.User;
import com.ccyhy.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 访问数据库
 * 查找user
 * @author zqh
 */
public class UserDao {
    public User getUser(String  name,String pwd)
    {
        User user=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try
        {
            //获取数据库链接
            connection=JdbcUtils.getConnection();
            //需要执行的sql语句
            String sql="select * from user where name=? and password=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,pwd);
            //执行
            resultSet=preparedStatement.executeQuery();
            //判断结果集
            while (resultSet.next())
            {
                user=new User();
                user.setId(resultSet.getInt(1));
                user.setName(name);
                user.setPassword(pwd);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
        return user;
    }
}
