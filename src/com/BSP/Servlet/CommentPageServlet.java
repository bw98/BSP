package com.BSP.Servlet;

import com.BSP.Service.PageService;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Comment;
import com.BSP.bean.Page;
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
import java.util.List;

public class CommentPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //读入流并转换为json，提取当前页号pageNum
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        int pageNum = Integer.valueOf(jsonObject.getString("pagenum"));  //获取当前页号
        int pageSize = 5;  //每页要展示的数据条数
        PageService pageService = new PageService();
        Page<Comment> page = pageService.findCommentByPage(pageNum, pageSize);

        List<Comment> list = page.getList();
        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        resp.getWriter().print(json);
    }

}
