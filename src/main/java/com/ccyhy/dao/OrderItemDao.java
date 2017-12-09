package com.ccyhy.dao;

import com.ccyhy.bean.OrderItem;
import com.ccyhy.bean.Product;
import com.ccyhy.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 订单列表访问数据库对象
 * 提供如下功能
 * 1，根据订单商品ID和数量，创建一个订单列表对象即，OrderItem进行返回
 * @author zqh
 */
public class OrderItemDao
{
    /**
     * 思路
     * 1，根据商品id，访问ProductDao对象，找到对应商品对象
     * 2，根据商品数量num，和商品对象，创建OrderItem返回
     */
    public OrderItem getOrderItem(int id,int num)
    {
        OrderItem orderItem=null;
        Product product=new ProductDao().getProduct(id);
        if(product!=null)
        {
            orderItem=new OrderItem();
            orderItem.setProduct(product);
            orderItem.setNum(num);
        }
        return orderItem;
    }
    /**
     * 将订单项对象存入数据库，即将各个属性值插入到数据库对应字段中
     * 此处对应
     * 1，订单项对象id  orderItem表自动生成
     * 2，商品id
     * 3，商品数量
     * 4，订单id--order_表自动生成
     */
    public void insert(OrderItem orderItem)
    {
        //数据库访问链接
        Connection connection=null;
        //执行sql语句的PreparedStatement对象
        PreparedStatement preparedStatement=null;
        //结果集
        ResultSet resultSet=null;
        try {
            connection=JdbcUtils.getConnection();
            //需要执行的sql语句
            String sql="insert into orderitem values(null,?,?,?)";
            //获取基于指定sql语句的执行sql的对象
            preparedStatement=connection.prepareStatement(sql);
            //为sql语句的占位符设定值
            preparedStatement.setInt(1,orderItem.getProduct().getId());
            preparedStatement.setInt(2,orderItem.getNum());
            preparedStatement.setInt(3,orderItem.getOrder().getId());
            //执行
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
    }
}
