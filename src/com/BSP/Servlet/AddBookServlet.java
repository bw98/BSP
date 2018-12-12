package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.Util.ImgBinUtil;
import com.BSP.bean.Book;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Book book=new Book();
        Map map=new HashMap();
        BookService bookService1=new BookService();
        int id;
        try {
            //存储图书信息获得id
            book.setName(request.getParameter("name"));
            book.setType(request.getParameter("type"));
            book.setAuthor(request.getParameter("author"));
            book.setIntro(request.getParameter("intro"));
            book.setStatus(Integer.valueOf(request.getParameter("status")));
            book.setUserId(Integer.valueOf(request.getParameter("userId")));
            book.setReserveId(Integer.valueOf(request.getParameter("reserveId")));
            BookService bookService =new BookService();
            id=bookService.addBook(book);

            //存储图片
            String projRealPath = request.getServletContext().getRealPath("/");
            int startIdx = projRealPath.indexOf("BSP/");
            projRealPath = projRealPath.substring(0, startIdx + 4);

            String img=request.getParameter("img");
            System.out.println("img: " + img);
            bookService.uploadBookImg(String.valueOf(id),img, projRealPath);

            map.put("status",true);
            JSONObject jsonObject = JSONObject.fromObject(map);
            response.getWriter().print(jsonObject);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("error","Some properties are empty");
            JSONObject jsonObject = JSONObject.fromObject(map);
            response.getWriter().print(jsonObject);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
