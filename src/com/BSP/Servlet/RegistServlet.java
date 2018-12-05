package com.BSP.Servlet;

import com.BSP.bean.User;
import com.BSP.service.Userservice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistServlet")
public class RegistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        User u = new User(req.getParameter("name"), req.getParameter("password"), req.getParameter("phonenum"));
        Userservice userService = new Userservice();


        if (userService.regist(u)) {
            resp.getWriter().print("0");
        } else {
            resp.getWriter().print("1");
        }
    }
}