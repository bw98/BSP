package com.BSP.DAO;

import com.BSP.bean.Book;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAO {
    //获得书籍信息（分页）
    public List<Book> pageAllBook(int startindex,int pagesize) {
        database DB = new database();
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
        }
        return booklist;
    }
   
    //获得当前所有书籍信息
    public List<Book> queryBookList(){
        database DB=new database();
        List<Book> booklist=new ArrayList<Book>();
        SqlSession sqlsession=null;
        try {
            sqlsession=DB.getSqlsession();
            booklist=sqlsession.selectList("Book.allbook");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return booklist;
    }

    //获得单个书籍信息
    public Book findBook(){
        database DB=new database();
        SqlSession sqlsession=null;
        Book book=null;
        try {
            sqlsession=DB.getSqlsession();
            book=sqlsession.selectOne("Book.findbook","");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(sqlsession!=null){
                sqlsession.close();
            }
        }
        return book;
    }

}
