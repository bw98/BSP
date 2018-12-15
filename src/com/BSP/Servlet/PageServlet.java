package com.BSP.Servlet;

import com.BSP.Service.PageService;
import com.BSP.bean.Book;
import com.BSP.bean.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);

        int pagenum = Integer.valueOf(jsonObject .getString("pagenum"));
        int pagesize;
        if(pagenum==1){
            pagesize = 7;
        }else{
            pagesize = 8;
        }
        PageService pageService = new PageService();
        Page page = pageService.findbookbypage(pagenum, pagesize);
        List<Book> list = page.getList();
        String json = JSONArray.fromObject(list).toString();
        response.getWriter().print(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
