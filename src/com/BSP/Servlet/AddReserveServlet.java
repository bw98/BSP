package com.BSP.Servlet;

import com.BSP.Service.ReserveService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.Reserve;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddReserveServlet extends HttpServlet {
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
            Reserve reserve = new Reserve();
            reserve.setBookId(Integer.valueOf(jsonObject.getString("bookId")));
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            UserService userService = new UserService();
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            reserve.setUserId(userId);

            //设置图书归还时间
            Date endDate = java.sql.Date.valueOf(jsonObject.getString("endDate"));
            reserve.setEndDate(endDate);

            ReserveService reserveService = new ReserveService();
            int status = reserveService.reserve(reserve);

            if (status == 0) {
                map.put("status", Integer.toString(status));
                JSONObject jsonMap = JSONObject.fromObject(map);
                response.getWriter().print(jsonMap);
            } else {
                map.put("status", Integer.toString(status));
                JSONObject jsonMap = JSONObject.fromObject(map);
                response.getWriter().print(jsonMap);
            }
        } catch (Exception e) {
            map.put("status", "false");
            map.put("error", e.getMessage());
            JSONObject jsonMap = JSONObject.fromObject(map);
            response.getWriter().print(jsonMap);
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
