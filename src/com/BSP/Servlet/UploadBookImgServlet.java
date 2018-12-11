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

        //读入流并转换为json，得到图书的ID还有jpg图像的Base64编码
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        System.out.println("[jsonObject]" + jsonObject.toString());
        String bookId = jsonObject.getString("bookId");
        String imgBin = jsonObject.getString("imgBin");
        String projRealPath = req.getServletContext().getRealPath("/");
        int startIdx = projRealPath.indexOf("BSP/");
        projRealPath = projRealPath.substring(0, startIdx + 4);

        BookService bookService = new BookService();
        String imgUrl = bookService.uploadBookImg(bookId, imgBin, projRealPath);
        HashMap<String, String> map = new HashMap<String, String>();

        if(imgUrl != null) {

            map.put("imgUrl", imgUrl);
            JSONObject jsonMap = JSONObject.fromObject(map);
            resp.getWriter().print(jsonMap);
        } else {
            map.put("imgUrl", "null");
            JSONObject jsonMap = JSONObject.fromObject(map);
            resp.getWriter().print(jsonMap);
        }
    }

}
