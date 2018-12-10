package com.BSP.Servlet;

import com.BSP.Service.UserService;
import com.BSP.bean.User;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RegistServlet extends HttpServlet {
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

        UserService userService = new UserService();
        Map<String, String> map = new HashMap<String, String>();

        if (userService.regist(u)) {
            map.put("status", "0");

        } else {
            map.put("status", "1");
        }
        JSONObject jsonMap = JSONObject.fromObject(map);
        resp.getWriter().print(jsonMap);
    }
}
