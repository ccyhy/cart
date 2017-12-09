package com.ccyhy.servlet;

import com.ccyhy.bean.User;
import com.ccyhy.dao.UserDao;
import com.ccyhy.utils.SopUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zqh
 */
@WebServlet("/login")
public class LoginServlet  extends HttpServlet{
    @Override
    public void init() throws ServletException {
        super.init();
        SopUtils.sop("init方法执行");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
        SopUtils.sop("doGet方法执行");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);//不能加这句
        SopUtils.sop("doPost方法执行");
        String username=req.getParameter("username");
        SopUtils.sop(username);
        String userpwd=req.getParameter("userpwd");
        SopUtils.sop(userpwd);
        //调用Dao层访问数据库，判断user是否存在
        User user=new UserDao().getUser(username,userpwd);
        if(user!=null)
        {
            SopUtils.sop(user.getName()+"--"+user.getPassword());
            //将user添加到session
            req.getSession().setAttribute("user",user);
            //跳转到商品列表页
            resp.sendRedirect("/ProductListServlet");
//            req.getRequestDispatcher("/WEB-INF/jsp/listProduct.jsp").forward(req,resp);
        }
        else
       {
            //给出账号或者密码错误提示，3s后自动挑战到登录页面
           String message=String.format("抱歉，登录名或者密码错误，3s后跳转到登录界面，请重新登录!<meta http-equiv='refresh' content='3;url=%s'/>",
                   "loginUIServlet");
            req.setAttribute("message",message);
           req.getRequestDispatcher("message.jsp").forward(req,resp);

//            String message=String.format("登录名或者密码错误!3秒后自动跳转到登录界面!<meta http-equiv='refresh' content='3;url=%s'/>",
//                    "LoginUIServlet");
////            SopUtils.sop(request.getContextPath());
//            req.setAttribute("message",message);
//            req.getRequestDispatcher("message.jsp").forward(req,resp);
        }
    }

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        SopUtils.sop("service方法执行");
//        /**
//         * 根据request中携带的参数，判断用户名和密码，查询是否有此对象，有则到产品列表界面
//         * 没有则跳转到登录界面，重新登录
//         */
//        String username=req.getParameter("username");
//        String userpwd=req.getParameter("username");
//        //调用Dao层访问数据库，判断user是否存在
//        User user=new UserDao().getUser(username,userpwd);
//        if(user!=null)
//        {
//            //将user添加到session
//            req.getSession().setAttribute("user",user);
//            //跳转到商品列表页
//            resp.sendRedirect("/WEB-INF/jsp/listProduct.jsp");
//        }
//        else
//        {
//            //给出账号或者密码错误提示，3s后自动挑战到登录页面
//            String message=String.format("抱歉，登录名或者密码错误，3s后跳转到登录界面，请重新登录!<meta http-equiv='refresh' content='3;url=%s'",
//                    "/WEB-INF/jsp/login.jsp");
//            req.setAttribute("message",message);
//        }
//    }
}
