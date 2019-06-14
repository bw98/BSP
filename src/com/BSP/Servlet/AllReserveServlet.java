package com.BSP.Servlet;

import com.BSP.Service.ReserveService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.Util.JsonDateValueProcessor2;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllReserveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            ReserveService reserveService=new ReserveService();
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            UserService userService=new UserService();
            int userId=userService.findIdByUserName((String)c.get("user_name"));
            List list=reserveService.allReserve(userId);
            JsonConfig config = new JsonConfig(); //通过工具类实现DateTime的格式化，以方便前端显示
            JsonDateValueProcessor2 jsonDateValueProcessor = new JsonDateValueProcessor2();
            config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
            String json = JSONArray.fromObject(list, config).toString();
            response .getWriter().print(json);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "false");
            map.put("error",e.getMessage());
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
