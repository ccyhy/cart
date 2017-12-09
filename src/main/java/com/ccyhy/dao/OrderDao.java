package com.ccyhy.dao;

import com.ccyhy.bean.Order;
import com.ccyhy.utils.JdbcUtils;
import com.ccyhy.utils.SopUtils;

import java.sql.*;

/**
 * @author zqh
 * 注意，在order对象保存到数据库中后，该对象就会有对应的id，这个id是自动增长的，在后续保存OrderItem中的时候，是作为order id存在的
 * 因此这里保存数据的时候，要获取自增长id
 */
public class OrderDao
{
    /**
     * 主要将订单数据存入数据库
     */
    public Order  insert(Order order)
    {
        //数据库链接
        Connection connection=null;
        //负责执行sql语句的对象
        PreparedStatement preparedStatement=null;
        //结果集对象
        ResultSet resultSet=null;
        try
        {
            connection= JdbcUtils.getConnection();
            String sql="insert into order_ values(null,?)";
            //注意，一定要加上参数：Statement.RETURN_GENERATED_KEYS，否则获取不到自动增长的key
            preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //为sql语句占位符设置属性
            preparedStatement.setInt(1,order.getUser().getId());
            //执行
            preparedStatement.execute();
            //取出自增长id
            resultSet=preparedStatement.getGeneratedKeys();
            //为order对象设置id属性，这个id就是数据库自动生成的id
            while (resultSet.next())
            {
                int id=resultSet.getInt(1);
                SopUtils.sop("id="+id);
                order.setId(id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
        return  order;
    }
}
