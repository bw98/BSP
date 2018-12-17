package com.BSP.Servlet;

import com.BSP.Service.CollectionService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.Collection;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;

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
import java.util.Map;

public class AddCollectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受json并转化为 Collection 对象, 将前端传来 bookId 放入对象中
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Collection collection = (Collection) JSONObject.toBean(jsonObject, Collection.class);

        CollectionService collectionService = new CollectionService();
        UserService userService = new UserService();
        String jwt = req.getHeader("Authorization");

        try {
            //从token中获取user_name，然后查找数据库得到user_id
            Claims c = JWTUtil.parseJWT(jwt);
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            collection.setUserId(userId);
            long nowMills = System.currentTimeMillis();
            Date now = new Date(nowMills);
            collection.setCollTime(now);

            if (collectionService.addCollection(collection) == -1) {
                Map<String, String> map = new HashMap<>();
                map.put("status", "false");
                String jsonMap = JSONObject.fromObject(map).toString();
                resp.getWriter().print(jsonMap);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("status", "true");
                String jsonMap = JSONObject.fromObject(map).toString();
                resp.getWriter().print(jsonMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
