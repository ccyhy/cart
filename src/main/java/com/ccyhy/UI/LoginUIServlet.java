package com.ccyhy.UI;

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
@WebServlet("/loginUIServlet")
public class LoginUIServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        SopUtils.sop("init方法执行");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        SopUtils.sop("service方法执行");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
        SopUtils.sop("doPost方法执行");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SopUtils.sop("doGet方法执行");
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,resp);
    }
}
