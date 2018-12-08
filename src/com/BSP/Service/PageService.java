package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Page;

import java.util.List;

public class PageService {
    public Page<Book> findbookbypage(int pagenum, int pagesize){
        BookDAO bookDAO = new BookDAO();
        List<Book> list = bookDAO.queryBookList();
        int amount=list.size();

        Page<Book> page = new Page(pagenum,pagesize,amount);
        int startindex=page.getStartindex();
        page.setList(bookDAO.pageAllBook(startindex,pagesize));
        return page;
    }
}