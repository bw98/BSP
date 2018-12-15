package com.BSP.Servlet;

import com.BSP.DAO.RentDAO;
import com.BSP.Service.ReserveService;
import com.BSP.Util.JsonDateValueProcessor;
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


public class AllRentServlet extends HttpServlet {
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

        try {
            RentDAO rentDAO=new RentDAO();
            int userId=Integer.valueOf(jsonObject.getString("userId"));
            List list=rentDAO.allRent(userId);
            //处理date类型
            JsonConfig config = new JsonConfig();
            JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
            config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
            String json = JSONArray.fromObject(list, config).toString();
            response .getWriter().print(json);
        } catch (NumberFormatException e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "false");
            map.put("error",e.getMessage());
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
