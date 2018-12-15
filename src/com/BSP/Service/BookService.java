package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.Util.ImgBinUtil;
import com.BSP.bean.Book;

import java.util.List;

public class BookService {
    public List<Book> searchbook(String name) {
        BookDAO bookDAO = new BookDAO();
        List booklist = bookDAO.searchBook(name);
        return booklist;
    }

    public boolean deleteBook(int id){
        BookDAO bookDAO=new BookDAO();
        if(bookDAO.findBookByBookId(id)!=null){
            bookDAO.deleteBook(id);
        }else{
            return false;
        }
        return true;
    }

    public int addBook(Book book){
        BookDAO bookDAO = new BookDAO();
        return bookDAO.addBook(book);
    }

    public boolean updateBook(Book book){
        BookDAO bookDAO=new BookDAO();
        if(bookDAO.findBookByBookId(book.getId())!=null){
            bookDAO.updateBook(book);
        }else{
            return false;
        }
        return true;
    }


    public String uploadBookImg(String bookId, String imgBin, String projRealPath) {
        BookDAO bookDAO = new BookDAO();
        int bookid = Integer.valueOf(bookId);
        Book book = bookDAO.findBookByBookId(bookid);
        if (book == null) {
            return null;
        }

        String savePath = projRealPath + "web/WEB-INF/BookPhoto/" + bookId + ".jpg";
        System.out.println(savePath);
        ImgBinUtil.base64StringToImage(imgBin,savePath);
        bookDAO.updateImgUrl(bookid, savePath);
        return savePath;
    }
}
