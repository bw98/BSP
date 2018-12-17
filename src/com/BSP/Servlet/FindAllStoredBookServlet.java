package com.BSP.Servlet;

import com.BSP.Service.BookService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FindAllStoredBookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        BookService bookService = new BookService();
        ArrayList<Map> list = bookService.findAllStoredBook();

        String json = JSONArray.fromObject(list).toString();
        resp.getWriter().print(json);
    }
}
