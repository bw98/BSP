package com.BSP.Servlet;


import com.BSP.Service.RentService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.Rent;
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


public class AddRentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map<String, String> map = new HashMap<String, String>();

        try {
            Rent rent=new Rent();
            rent.setBookId(Integer.valueOf(jsonObject.getString("bookId")));
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            UserService userService=new UserService();
            int userId=userService.findIdByUserName((String)c.get("user_name"));
            rent.setUserId(userId);
            RentService rentService =new RentService();
            boolean status=rentService.rent(rent);
            if(status==true){
                map.put("status", "true");
                JSONObject jsonMap = JSONObject.fromObject(map);
                response .getWriter().print(jsonMap);
            }else{
                    map.put("status", "false");
                    JSONObject jsonMap = JSONObject.fromObject(map);
                    response .getWriter().print(jsonMap);
                }
        } catch (Exception e) {
            map.put("status", "false");
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
