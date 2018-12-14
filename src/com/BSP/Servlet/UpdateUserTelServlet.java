package com.BSP.Servlet;

import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.User;
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
import java.util.HashMap;
import java.util.Map;

public class UpdateUserTelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //读入流并转换为json，最后转换为 java Bean
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        User u = (User)JSONObject.toBean(jsonObject, User.class);

        String jwt = req.getHeader("Authorization");
        try {
            Claims c = JWTUtil.parseJWT(jwt);
            u.setUserName((String) c.get("user_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserService userService = new UserService();
        Map<String, String> map = new HashMap<String, String>();

        if(userService.updateUserTel(u)) {
            map.put("status", "0");
        } else {
            map.put("status", "1");
        }
        String jsonMap = JSONObject.fromObject(map).toString();
        resp.getWriter().print(jsonMap);
    }

}
