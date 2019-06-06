package com.BSP.Servlet;

import com.BSP.Service.RentService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public class OverdueServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");


        RentService rentService = new RentService();
        UserService userService = new UserService();

        try {
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            List list = rentService.overdue(userId);
            JSONArray jsonArray = JSONArray.fromObject(list);
            response.getWriter().print(jsonArray);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
