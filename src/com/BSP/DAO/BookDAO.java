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
            // TODO Auto-generated catch block
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
        Book book=null;
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.searchbook",name);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
        Book book = null;
        try {
            sqlSession = DB.getSqlsession();
            book=sqlSession.selectOne("Book.findBookByBookId",id);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
        int id;
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

    //下架图书（status=4）
    public boolean deleteBook(int id){
        SqlSession sqlSession=null;
        try {
            sqlSession=DB.getSqlsession();
            sqlSession.update("Book.deleteBook",id);
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

}
