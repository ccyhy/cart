package com.ccyhy.servlet;

import com.ccyhy.bean.Order;
import com.ccyhy.bean.OrderItem;
import com.ccyhy.bean.User;
import com.ccyhy.dao.OrderDao;
import com.ccyhy.dao.OrderItemDao;
import com.ccyhy.utils.SopUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zqh
 */
@WebServlet("/createOrderServlet")
public class CreateOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 新增订单
     * 执行思路
     * 1，当用户在购物车界面点击生成订单后，该servlet执行
     * 2，首先取得session中存储的用户对象，并根据用户生成订单对象order
     * 3，调用OrderDao将生成的order对象插入到数据库
     * 4，取出session中存储订单项对象的lsit集合，遍历list集合，取出每一个OrderItem对象，并为他们设置order对象
     * 5，将设置Order对象后的OrderItem对象，调用OrderItemDao方法插入到数据库
     * 6，将存储订单项集合清空
     * 7，跳转到信息界面，通知用户，订单生成成功
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取session中的user
        User user=(User) req.getSession().getAttribute("user");
        //判断，如果user==null，则尚未登录，直接跳转到登录界面
        if(user==null)
        {
            resp.sendRedirect("/loginUIServlet");
            //返回
            return;
        }
        //创建订单对象
        Order order=new Order();
        order.setUser(user);
        //调用数据访问层dao对象，将order插入到数据库
        order=new OrderDao().insert(order);
        SopUtils.sop("创建订单的时候id="+order.getId());
        //取出存储在session中的订单项集合
        List<OrderItem> itemList=(List<OrderItem>)req.getSession().getAttribute("orderItemList");
        //遍历，然后插入到数据库
        for (OrderItem orderItem:itemList)
        {
            //为orderItem设置订单对象
            orderItem.setOrder(order);
            //调用数据访问层，将订单项数据插入到数据库
            new OrderItemDao().insert(orderItem);
        }
        //清空存储订单项的集合
        itemList.clear();
        //通知客户订单项目生成成功
        String message=String.format("恭喜，订单已经生成功存入数据库!系统3s后为您跳转到商品列表页！" +
                "<meta http-equiv='refresh' content='3;url=%s'>","ProductListServlet");
        req.setAttribute("message",message);
        req.getRequestDispatcher("message.jsp").forward(req,resp);
    }
}
