package com.BSP.Servlet;

import com.BSP.Service.CollectionService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.Util.JsonDateValueProcessor;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FindCollectionByUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String jwt = req.getHeader("Authorization");
        UserService userService = new UserService();
        int userId = -1;
        try {
            Claims c = JWTUtil.parseJWT(jwt);
            userId = userService.findIdByUserName((String) c.get("user_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        CollectionService collectionService = new CollectionService();
        ArrayList<Map> list = collectionService.findCollectionByUserId(userId);
        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        resp.getWriter().print(json);
    }

}
