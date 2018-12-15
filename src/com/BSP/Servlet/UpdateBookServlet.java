package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.bean.Book;
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
import java.util.HashMap;
import java.util.Map;


public class UpdateBookServlet extends HttpServlet {
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

        Book book=new Book();
        BookService bookService =new BookService();
        book.setName(jsonObject.getString("name"));
        book.setType(jsonObject.getString("type"));
        book.setAuthor(jsonObject.getString("author"));
        book.setIntro(jsonObject.getString("intro"));
        book.setId(Integer.valueOf(jsonObject.getString("id")));
        try {
            bookService.updateBook(book);
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "true");
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "true");
            map.put("error",e.getMessage());
            JSONObject jsonMap = JSONObject.fromObject(map);
            response .getWriter().print(jsonMap);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
