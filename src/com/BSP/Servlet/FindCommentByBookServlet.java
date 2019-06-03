package com.BSP.Servlet;

import com.BSP.Service.CommentService;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Comment;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FindCommentByBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //读取前端传来的json串并转换为Comment对象
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Comment comment = (Comment)JSONObject.toBean(jsonObject, Comment.class);

        //根据BookId查询某个图书下面的所有评论，并以json数组的形式返回给前端
        CommentService commentService = new CommentService();
        ArrayList<Map> list = commentService.findCommentByBookId(comment);
        JsonConfig config = new JsonConfig(); //通过工具类实现DateTime的格式化，以方便前端显示
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        resp.getWriter().print(json);
    }
}
