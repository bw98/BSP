package com.BSP.Servlet;

import com.BSP.Service.BookService;
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

public class UploadBookImgServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //读入流并转换为json，最后转换为 java Bean
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String bookId = jsonObject.getString("bookId");
        String imgBin = jsonObject.getString("imgBin");

        BookService bookService = new BookService();
        String imgPath = bookService.uploadBookImg(bookId, imgBin);
        HashMap<String, String> map = new HashMap<String, String>();

        if(imgPath != null) {

            map.put("imgPath", imgPath);
            JSONObject jsonMap = JSONObject.fromObject(map);
            resp.getWriter().print(jsonMap);
        } else {
            map.put("imgPath", "null");
            JSONObject jsonMap = JSONObject.fromObject(map);
            resp.getWriter().print(jsonMap);
        }
    }

}
