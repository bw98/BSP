package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.Util.ImgBinUtil;
import com.BSP.bean.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    public List<Book> searchbook(String name) {
        BookDAO bookDAO = new BookDAO();
        List booklist = bookDAO.searchBook(name);
        return booklist;
    }

    public ArrayList<Map> searchBookUnderCheck() {
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        List<Book> books = bookDAO.searchBookUnderCheck();
        ArrayList<Map> list = new ArrayList<Map>();
        for (Book b: books) {
            Map<String, String> map = new HashMap<>();  //Map不能被 new，它是一个接口
            map.put("id", Integer.toString(b.getId()));
            map.put("name", b.getName());
            map.put("userName", userDAO.findUserById(b.getUserId()).getUserName());
            list.add(map);
        }
        return list;
    }

    public ArrayList<Map> findAllStoredBook () {
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        List<Book> books = bookDAO.findAllStoredBook();
        ArrayList<Map> list = new ArrayList<Map>();
        for (Book b: books) {
            Map<String, String> map = new HashMap<>();
            map.put("id", Integer.toString(b.getId()));
            map.put("name", b.getName());
            map.put("userName", userDAO.findUserById(b.getUserId()).getUserName());
            list.add(map);
        }
        return list;

    }

    public boolean deleteBook(int id){
        BookDAO bookDAO=new BookDAO();
        if(bookDAO.findBookByBookId(id)!=null){
            bookDAO.updateBookStatus(id,4);
        }else{
            return false;
        }
        return true;
    }

    public boolean approveBook(int id){
        BookDAO bookDAO=new BookDAO();
        if(bookDAO.findBookByBookId(id)!=null){
            bookDAO.updateBookStatus(id,0);
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

    public int findStatusById(int id){
        BookDAO bookDAO=new BookDAO();
        Book book=bookDAO.findBookByBookId(id);
        return book.getStatus();
    }

    public List<Book> findMyBook(int userId){
        BookDAO bookDAO=new BookDAO();
        return bookDAO.findMyBook(userId);
    }
}
