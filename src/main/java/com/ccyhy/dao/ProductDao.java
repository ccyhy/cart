package com.ccyhy.dao;

import com.ccyhy.bean.Product;
import com.ccyhy.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 提供对product的查询
 */
public class ProductDao
{
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    //根据产品id获取产品
    public Product getProduct(int id)
    {
        Product product=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        //根据ID，从数据库查询
        try{
            //获取数据库链接
            connection=JdbcUtils.getConnection();
            //需要执行的sql语句
            String sql="select*from product where id=?";
            //获取基于sql的PreparedStatement对象
            preparedStatement=connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setInt(1,id);
            //执行sql
            resultSet=preparedStatement.executeQuery();
            //取出结果中的数据
            while (resultSet.next())
            {
                product=new Product();
                product.setId(id);
                String name=resultSet.getString(2);
                float price=resultSet.getFloat(3);
                product.setName(name);
                product.setPrice(price);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
        return product;
    }
    public List<Product> listProduct()
    {
        List<Product> products=new ArrayList<Product>();
        //需要执行的sql，这里是从product表中选出所有商品 且按照id降序排列
        String sql="select * from product order by id desc";
        try
        {
            //利用jdbcutils获取数据链接
            connection= JdbcUtils.getConnection();
            //获取执行指定sql的对象
            preparedStatement=connection.prepareStatement(sql);
            //获取结果集
            resultSet=preparedStatement.executeQuery();
            //循环取出结果集中的数据，填充入一个Product对象
            while (resultSet.next())
            {
                Product product=new Product();
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                float price=resultSet.getFloat(3);

                product.setId(id);
                product.setName(name);
                product.setPrice(price);

                products.add(product);
            }
            //执行完毕，释放资源
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.release(connection,preparedStatement,resultSet);
        }
        return products;
    }
}
