package com.BSP.Servlet;

import com.BSP.DAO.ReserveDAO;
import com.BSP.Service.ReserveService;
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


public class DeleteReserveServlet extends HttpServlet {
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
            ReserveService reserveService=new ReserveService();
            int id=Integer.valueOf(jsonObject.getString("id"));
            reserveService.deleteReserve(id);
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "true");
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
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
