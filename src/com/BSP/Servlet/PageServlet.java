package com.BSP.Servlet;

import com.BSP.Service.PageService;
import com.BSP.bean.Book;
import com.BSP.bean.Page;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class PageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=utf-8");
                /*
                 * 1.获取当前页数
                 * 2.设置显示条数
                 * 3.根据pageservice的findbookbypage方法获得list
                 * 4.将list存入request域中；
                 */
                int pagenum=Integer.valueOf(request.getParameter("pagenum"));
                int pagesize=8;
                PageService pageService = new PageService();
               Page page=pageService.findbookbypage(pagenum,pagesize);
                List<Book> list=page.getList();
                String json=JSONArray.fromObject(list).toString();
                  response.getWriter().print(json);

            }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
