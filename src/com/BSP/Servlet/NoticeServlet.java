package com.BSP.Servlet;

import com.BSP.Service.ReserveService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.Util.JsonDateValueProcessor2;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        ReserveService reserveService = new ReserveService();
        UserService userService = new UserService();

        List list = new ArrayList();
        try {
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            list = reserveService.notice2(userId); //使用新版notice，仅返回bookName和finalStatus
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonConfig config = new JsonConfig(); //通过工具类实现DateTime的格式化，以方便前端显示
        JsonDateValueProcessor2 jsonDateValueProcessor = new JsonDateValueProcessor2();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        response.getWriter().print(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
