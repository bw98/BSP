package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.Book;
import io.jsonwebtoken.Claims;
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

public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //获取前端传来的json串
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        Book book = new Book();
        Map map = new HashMap();
        int id;
        try {
            //存储图书信息获得id
            book.setName(jsonObject.getString("name"));
            book.setType(jsonObject.getString("type"));
            book.setAuthor(jsonObject.getString("author"));
            book.setIntro(jsonObject.getString("intro"));
            book.setStatus(1);
            String jwt = request.getHeader("Authorization");
            Claims c = JWTUtil.parseJWT(jwt);
            UserService userService = new UserService();
            int userId = userService.findIdByUserName((String) c.get("user_name"));
            book.setUserId(userId);
            BookService bookService = new BookService();
            id = bookService.addBook(book);

            //存储图片
            String projRealPath = request.getServletContext().getRealPath("/");
            int startIdx = projRealPath.indexOf("BSP/");
            projRealPath = projRealPath.substring(0, startIdx + 4);

            String img = jsonObject.getString("img");
            bookService.uploadBookImg(String.valueOf(id), img, projRealPath);

            map.put("status", true);
            JSONObject jsonObject1 = JSONObject.fromObject(map);
            response.getWriter().print(jsonObject1);
        } catch (NumberFormatException e) {
            map.put("status", false);
            map.put("error", "Some properties are empty");
            JSONObject jsonObject1 = JSONObject.fromObject(map);
            response.getWriter().print(jsonObject1);
        } catch (Exception e) {
            map.put("status", false);
            map.put("error", "Some properties are empty");
            JSONObject jsonObject1 = JSONObject.fromObject(map);
            response.getWriter().print(jsonObject1);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
