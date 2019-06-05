package com.BSP.Service;

import com.BSP.DAO.BookDAO;
import com.BSP.DAO.UserDAO;
import com.BSP.Util.ImgBinUtil;
import com.BSP.bean.Book;
import com.BSP.bean.User;

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

    //获取所有书籍
    public List<Book> allBook(){
        BookDAO bookDAO = new BookDAO();
        List list=bookDAO.queryBookList();
        return list;
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

//        String savePath = "/usr/java/tomcat/apache-tomcat-9.0.13/webapps/BSP/BookPhoto/" + bookId + ".jpg";  //zqw服务器使用
        String savePath = projRealPath + "web/BookPhoto/" + bookId + ".jpg"; //本地测试使用
        String savePath2 = "/BookPhoto/" + bookId + ".jpg";
        System.out.println(savePath);
        ImgBinUtil.base64StringToImage(imgBin,savePath);
        bookDAO.updateImgUrl(bookid, savePath2);
        return savePath;
    }

    //根据书籍id查找相关信息
    public Map findBookById(int id){
        BookDAO bookDAO=new BookDAO();
        UserDAO userDAO=new UserDAO();
        Book book=bookDAO.findBookByBookId(id);
        User user=userDAO.findUserById(book.getUserId());
        Map map=new HashMap();

        map.put("bookName",book.getName());
        map.put("author",book.getAuthor());
        map.put("type",book.getType());
        map.put("userName",user.getUserName());
        map.put("intro",book.getIntro());
        map.put("status",book.getStatus());
        map.put("userId",book.getUserId());
        map.put("imgUrl", book.getImgUrl());
        map.put("finalDay",book.getFinalDay());
        return map;
    }

    public List<Map> findMyBook(int userId){
        BookDAO bookDAO=new BookDAO();
        List list=bookDAO.findMyBook(userId);
        List<Map> alllist=new ArrayList<Map>();
        for(int i=0;i<list.size();i++){
            Book book=(Book)list.get(i);
            Map map=new HashMap();
            map.put("name",book.getName());
            map.put("status",book.getStatus());
            map.put("bookId",book.getId());
            alllist.add(map);
        }
        return alllist;
    }
}
