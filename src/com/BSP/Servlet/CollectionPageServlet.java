package com.BSP.Servlet;

import com.BSP.Service.PageService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.Util.JsonDateValueProcessor;
import com.BSP.bean.Collection;
import com.BSP.bean.Page;
import io.jsonwebtoken.Claims;
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

public class CollectionPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //读入流并转换为json
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        int pageNum = Integer.valueOf(jsonObject.getString("pagenum"));  //从json获取当前页号
        int pageSize = 10;  //每页要展示的数据条数
        int userId = -1;

        String jwt = req.getHeader("Authorization");

        try {
            Claims c = JWTUtil.parseJWT(jwt);
            UserService userService = new UserService();
            userId = userService.findIdByUserName((String) c.get("user_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        PageService pageService = new PageService();
        Page<Collection> page = pageService.findCollectionByPage(pageNum, pageSize, userId); //取得某用户的收藏分页

        List<Collection> list = page.getList();
        JsonConfig config = new JsonConfig();
        JsonDateValueProcessor jsonDateValueProcessor = new JsonDateValueProcessor();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        resp.getWriter().print(json);
    }

}
