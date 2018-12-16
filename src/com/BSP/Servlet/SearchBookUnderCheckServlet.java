package com.BSP.Servlet;

import com.BSP.Service.BookService;
import com.BSP.bean.Book;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchBookUnderCheckServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        BookService userService = new BookService();
        List<Book> list = userService.searchBookUnderCheck();
        String json = JSONArray.fromObject(list).toString();
        resp.getWriter().print(json);
    }
}
