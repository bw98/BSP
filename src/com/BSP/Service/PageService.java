package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.CommentDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Comment;
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

    public Page<Comment> findCommentByPage(int pageNum, int pageSize) {
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> comments = commentDAO.findAllComment();
        int amount = comments.size();

        Page<Comment> page = new Page<>(pageNum, pageSize, amount);
        int startIndex = page.getStartindex();
        page.setList(commentDAO.pageAllComment(startIndex, pageSize));

        return page;
    }

}