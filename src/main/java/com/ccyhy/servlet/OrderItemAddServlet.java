package com.ccyhy.servlet;

import com.ccyhy.bean.OrderItem;
import com.ccyhy.bean.Product;
import com.ccyhy.dao.ProductDao;
import com.ccyhy.utils.SopUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zqh
 * 1,当用户点击购买的时候，就是创建一条订单项的动作，此刻需要
 * -- 1，获取购买数量
 * 2，获取购买商品id
 * 3，根据id创建商品对象
 * 4，创建一个新的orderItem订单项对象
 * 5，从session只不过取出一个lsit，这个list中存放用户陆续购买的商品
 * 6，将新创建的订单项放入list中
 * 7，跳转到显示购物车的 listOrderItem jsp中
 */
@WebServlet("/OrderItemAddServlet")
public class OrderItemAddServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //取出商品的数量和pid
        int num=Integer.parseInt(req.getParameter("num"));
        int pid=Integer.parseInt(req.getParameter("pid"));
        //根据pid，访问数据库创建product对象
        Product product=new ProductDao().getProduct(pid);
        SopUtils.sop(product.getName()+"--"+product.getPrice());
        //新创建一个订单项对象
        OrderItem oi=new OrderItem();
        oi.setNum(num);
        oi.setProduct(product);

        //从session中取出用户购买记录
        List<OrderItem> orderItemList=(List<OrderItem>)req.getSession().getAttribute("orderItemList");
        //判断是否为null，为null则新建
        if(orderItemList==null)
        {
            orderItemList=new ArrayList<OrderItem>();
            //放入request的session中
            req.getSession().setAttribute("orderItemList",orderItemList);
        }
        //添加订单前，判断所购买的商品id是否一致，即是否重复，如果重复，不需要新增，找到session中存储的item，增加数量
        boolean found=false;
        for(OrderItem orderItem:orderItemList)
        {
            if(orderItem.getProduct().getId()==oi.getProduct().getId())
            {
                //重新调整oi的数量，即两个对象中数量之和
                oi.setNum(oi.getNum()+orderItem.getNum());
                found=true;
                //找到了就跳出循环
                break;
            }
        }
        //找不到，再新增
        if(!found)
        {
            orderItemList.add(oi);
        }
        //跳转到显示购物车的servlet--listOrderItem
        resp.sendRedirect("/ListOrderItemServlet");
    }
}
