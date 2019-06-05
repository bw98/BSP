package com.BSP.Servlet;

import com.BSP.Service.RentService;
import com.BSP.Util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class AllOverdueServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        RentService rentService=new RentService();
        List list=rentService.allOverdue();
        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        response.getWriter().print(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
