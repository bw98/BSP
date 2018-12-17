package com.BSP.Servlet;

import com.BSP.Service.CommentService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Comment;
import io.jsonwebtoken.Claims;
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
import java.util.Map;

public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受json并转化为 Comment 对象
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Comment comment = (Comment) JSONObject.toBean(jsonObject, Comment.class);

        CommentService commentService = new CommentService();
        UserService userService = new UserService();
        String jwt = req.getHeader("Authorization");

        try {
            //从token中获取user_name，然后查找数据库得到user_id
            Claims c = JWTUtil.parseJWT(jwt);
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            comment.setUserId(userId);
            long nowMills = System.currentTimeMillis();
            Date now = new Date(nowMills);
            comment.setCreateTime(now);
            commentService.addComment(comment);

//            JsonConfig config = new JsonConfig();
//            JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
//            config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
//            String json = JSONObject.fromObject(comment, config).toString();
//            resp.getWriter().print(json);
            Map<String, String> map = new HashMap<>();
            map.put("status", "true");
            String json = JSONObject.fromObject(map).toString();
            resp.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
