package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.CollectionDAO;
import com.BSP.DAO.CommentDAO;
import com.BSP.bean.Book;
import com.BSP.bean.Collection;
import com.BSP.bean.Comment;
import com.BSP.bean.Page;

import java.util.List;

public class PageService {
    public Page<Book> findbookbypage(int pagenum, int pagesize) {
        BookDAO bookDAO = new BookDAO();
        List<Book> list = bookDAO.queryBookList();
        int amount = list.size();

        Page<Book> page = new Page(pagenum, pagesize, amount);
        int startindex = page.getStartindex();
        page.setList(bookDAO.pageAllBook(startindex, pagesize));
        return page;
    }

    public Page<Comment> findCommentByPage(int pageNum, int pageSize, int bookId) {
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> comments = commentDAO.findAllCommentByBookId(bookId);
        int amount = comments.size();

        Page<Comment> page = new Page<>(pageNum, pageSize, amount);
        int startIndex = page.getStartindex();
        page.setList(commentDAO.pageAllComment(startIndex, pageSize, bookId));

        return page;
    }

    public Page<Collection> findCollectionByPage(int pageNum, int pageSize, int userId) {
        CollectionDAO collectionDAO = new CollectionDAO();
        List<Collection> collections = collectionDAO.findAllCollectionByUserId(userId);
        int amount = collections.size();

        Page<Collection> page = new Page<>(pageNum, pageSize, amount);
        int startIndex = page.getStartindex();
        page.setList(collectionDAO.pageAllCollection(startIndex, pageSize, userId));
        return page;
    }

}