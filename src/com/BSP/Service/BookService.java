package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.bean.Book;

import java.util.List;

public class BookService {
    public List<Book> searchbook(String name){
        BookDAO bookDAO=new BookDAO();
        List booklist = bookDAO.searchBook(name);
        return booklist;
    }
}
