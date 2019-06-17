package com.BSP.DAO;

import com.BSP.bean.Book;
import com.BSP.bean.Rent;
import com.BSP.bean.Reserve;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentDAO {
    database DB = new database();

    //增加租赁记录
    public void addRent(Rent rent) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.insert("Rent.rentBook", rent);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //在加锁的情况下增加租赁记录并修改图书的状态，可能出现多人同时借阅时其他人先借到而导致失败的情况
    //参数id是图书id
    //参数status是想要修改的图书状态
    public boolean addRentUnderLock(Rent rent, int id, int status) {
        SqlSession sqlSession = null;
        Map map=new HashMap();
        map.put("id", id);
        map.put("status", status);
        try {
            sqlSession = DB.getSqlsession();
            Book currentBook = sqlSession.selectOne("Book.findBookByIdUnderLock", id);
            if (currentBook.getStatus() == 0) {//图书状态为在架时图书才能被借阅
                sqlSession.insert("Rent.rentBook", rent);
                sqlSession.update("Book.deleteBook", map);
            } else {
                throw new Exception("借书时图书状态已经被改了");
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return true;
    }

    //删除租赁
    public void deleteRent(int id) {
        SqlSession sqlSession = null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Rent.deleteRent", id);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //根据id查询租赁记录
    public Rent findRentById(int id) {
        SqlSession sqlSession = null;
        Rent rent = null;
        try {
            sqlSession = DB.getSqlsession();
            rent = sqlSession.selectOne("Rent.findRent", id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return rent;
    }

    //根据userId查找租赁记录
    public List<Rent> allRent(int userId) {
        SqlSession sqlSession = null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession = DB.getSqlsession();
            list = sqlSession.selectList("Rent.findRentByUserId", userId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return list;
    }

    public List<Rent> allOverRent() {
        SqlSession sqlSession = null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession = DB.getSqlsession();
            list = sqlSession.selectList("Rent.findAllRent");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return list;
    }

    public Rent findRentByUseridBookid(Rent rent) {
        SqlSession sqlSession = null;
        Rent rent1 = new Rent();
        try {
            sqlSession = DB.getSqlsession();
            rent1 = sqlSession.selectOne("Rent.findRentByUseridBookid", rent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return rent1;
    }

    //根据bookId查询当前正在借阅该书的借阅记录
    public Rent findRentByBookId(int bookId) {
        SqlSession sqlSession = null;
        Rent rent1 = new Rent();
        try {
            sqlSession = DB.getSqlsession();
            rent1 = sqlSession.selectOne("Rent.findRentByBookId", bookId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return rent1;
    }
}
