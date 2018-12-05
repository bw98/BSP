package com.BSP.Servlet;

import com.BSP.bean.User;
import com.BSP.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        User u = new User(req.getParameter("name"), req.getParameter("password"), req.getParameter("phonenum"));
        UserService userService = new UserService();
		int code = userService.login(u);
        resp.getWriter().print(code);
    }

}
