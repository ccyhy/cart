package com.ccyhy.servlet;

import com.ccyhy.bean.OrderItem;
import com.ccyhy.dao.OrderItemDao;
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
 * 删除订单项的思路
 * 1，获取需要删除的订单项对象中商品的id和商品数量
 * 2，根据商品id
 */
@WebServlet("/deleteOrderItem")
public class DeleteOrderItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 根据表单提交的字段，获取需要删除的商品的id和数量
         *
         */
        int id=Integer.parseInt(req.getParameter("pid"));
//        int num=Integer.parseInt(req.getParameter("num_delete"));
        //获取订单项对象
//        OrderItem orderItem=new OrderItemDao().getOrderItem(id,num);
        //获取session中存储的订单项list集合
        List<OrderItem> itemList=(List<OrderItem>)req.getSession().getAttribute("orderItemList");
        //新建一个list集合，存储需要被删除的商品订单项，用id作为判断依据
        List<OrderItem> itemListDelete=new ArrayList<OrderItem>();
        //判断是否为空，然后遍历itemlist，并且找到orderItem对象，如果存在，则删除
        if(itemList!=null)
        {
            for (OrderItem orderItem1:itemList)
            {
                if(orderItem1.getProduct().getId()==id)
                {
                    //将需要删除的订单项存入集合
                    itemListDelete.add(orderItem1);
                }
            }
        }
        itemList.removeAll(itemListDelete);
        //判断购物车是否还有内容，如无内容
        //重新定向到显示购物车的servlet
        resp.sendRedirect("/ListOrderItemServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
