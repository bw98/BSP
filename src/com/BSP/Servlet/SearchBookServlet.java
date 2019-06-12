package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.Util.JsonDateValueProcessor2;
import com.BSP.bean.Book;
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


public class SearchBookServlet extends HttpServlet {
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

        BookService bookService=new BookService();
        String key=jsonObject .getString("key");
        List<Book> list = bookService.searchbook(key);
        JsonConfig config = new JsonConfig(); //通过工具类实现DateTime的格式化，以方便前端显示
        JsonDateValueProcessor2 jsonDateValueProcessor = new JsonDateValueProcessor2();
        config.registerJsonValueProcessor(Date.class, jsonDateValueProcessor);
        String json = JSONArray.fromObject(list, config).toString();
        response.getWriter().print(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
