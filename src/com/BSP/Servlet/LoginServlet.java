package com.BSP.Servlet;

import com.BSP.Util.JWTUtil;
import com.BSP.bean.User;
import com.BSP.Service.UserService;
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


public class LoginServlet extends HttpServlet {


    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //接受json并转化为 User
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        User u = (User) JSONObject.toBean(jsonObject, User.class);

        //执行登陆业务
        UserService userService = new UserService();
        int code = userService.login(u);


        //设置 token 并传输 json
        if ((code == 3) || (code == 4)) {
            try {
                JWTUtil jwtUtil = new JWTUtil();
                Map<String, Object> payload = new HashMap<String, Object>();
                payload.put("user_name", u.getUserName());
                String token = jwtUtil.createJWT(u, 60000000, payload);
                Map<String, String> jsonMap = new HashMap<String, String>();
                jsonMap.put("token", token);
                jsonMap.put("status", Integer.toString(code));
                resp.getWriter().print(jsonMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
