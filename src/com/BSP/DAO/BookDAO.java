package com.BSP.DAO;

import com.BSP.bean.Book;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAO {
    database DB=new database();
    //获得书籍信息（分页）
    public List<Book> pageAllBook(int startindex,int pagesize) {
        List<Book> booklist = new ArrayList<Book>();
        Map map = new HashMap();
        map.put("startindex", startindex);
        map.put("pagesize", pagesize);
        SqlSession sqlsession = null;
        try {
            sqlsession = DB.getSqlsession();
            booklist = sqlsession.selectList("Book.page", map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }
   
    //获得当前所有书籍信息
    public List<Book> queryBookList(){
        List<Book> booklist=new ArrayList<Book>();
        SqlSession sqlsession=null;
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.allbook");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }

    //查找书籍
    public List<Book> searchBook(String name){
        SqlSession sqlsession=null;
        List<Book> booklist=new ArrayList<Book>();
        Book book=new Book();
        book.setName(name);
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.searchbook",book);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }

    //查找所有待审核的图书
    public List<Book> searchBookUnderCheck(){
        SqlSession sqlsession=null;
        List<Book> booklist=new ArrayList<Book>();
        Book book=new Book();
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.searchBookUnderCheck", book);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }

    //查找所有已上架的图书
    public List<Book> findAllStoredBook(){
        SqlSession sqlsession=null;
        List<Book> booklist=new ArrayList<Book>();
        Book book=new Book();
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.findAllStoredBook", book);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }

    //根据id查找书籍
    public  Book findBookByBookId(int id){
        SqlSession sqlSession=null;
        Book book=null;
        try {
            sqlSession = DB.getSqlsession();
            book=sqlSession.selectOne("Book.findBookByBookId",id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return book;
    }

    //修改图书图片url
    public boolean updateImgUrl(int id,String imgUrl){
        SqlSession sqlSession=null;
        Map map=new HashMap();
        map.put("id",id);
        map.put("imgUrl",imgUrl);
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.update("Book.updateImgUrl",map);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return true;
    }

    //增加图书
    public int addBook(Book book){
        SqlSession sqlSession=null;
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.insert("Book.addBook",book);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return book.getId();
    }

    //修改status( 0在架 1待审核 2已预约 3已借 4下架)
    public boolean updateBookStatus(int id,int status){
        SqlSession sqlSession=null;
        Map map=new HashMap();
        map.put("id",id);
        map.put("status",status);
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.update("Book.updateBook",map);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return true;
    }

    //借阅图书并发修改status( 0在架 1待审核 2已预约 3已借 4下架)
    public boolean fingAndUpdateBook(int id,int status){
        SqlSession sqlSession=null;
        Map map=new HashMap();
        map.put("id",id);
        map.put("status",status);
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.selectOne("Book.findByLock",id);
            sqlSession.update("Book.updateBook",map);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return true;
    }

    //修改图书信息
    public boolean updateBook(Book book){
        SqlSession sqlSession=null;
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.update("Book.updateBook",book);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return true;
    }

    //查找个人上传的所有图书
    public List<Book> findMyBook(int userId){
        SqlSession sqlSession=null;
        List list=new ArrayList();
        try {
            sqlSession=DB.getSqlsession();
            list=sqlSession.selectList("Book.findMyBook",userId);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return list;
    }

}
