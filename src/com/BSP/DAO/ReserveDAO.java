package com.BSP.DAO;

import com.BSP.bean.Book;
import com.BSP.bean.Reserve;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveDAO {
    database DB = new database();

    //增加预约记录
    public void addReserve(Reserve reserve) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.insert("Reserve.reserveBook", reserve);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //在加锁的情况下增加预约记录并修改图书的状态，可能会存在多人同时预约时其他人先约到而导致失败的情况
    //参数id是图书id
    //参数status是想要修改的图书状态
    public boolean addReserveUnderLock(Reserve reserve, int id, int status) {
        SqlSession sqlSession = null;
        Map map=new HashMap();
        map.put("id", id);
        map.put("status", status);
        try {
            sqlSession = DB.getSqlsession();
            Book currentBook = sqlSession.selectOne("Book.findBookByIdUnderLock", id);
            if (currentBook.getStatus() == 3) {//图书状态为借阅时图书才能被预约
                sqlSession.insert("Reserve.reserveBook", reserve);
                sqlSession.update("Book.deleteBook",map);
            } else {
                throw new Exception("预约时图书状态已经被改了");
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

    //删除预约
    public void deleteReserve(int id) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.deleteReserve", id);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //更新某条预约记录的Status
    public void updateStatus(Reserve reserve) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.updateStatus", reserve);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //更新某条预约记录的NoticeStatus
    public void updateNoticeStatus(Reserve reserve) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.updateNoticeStatus", reserve);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //更新某条预约记录的finalStatus
    public void updateFinalStatus(Reserve reserve) {
        SqlSession sqlSession = null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.updateFinalStatus", reserve);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //根据id查找预约记录
    public Reserve findReserveById(int id) {
        SqlSession sqlSession = null;
        Reserve reserve = null;
        try {
            sqlSession = DB.getSqlsession();
            reserve = sqlSession.selectOne("Reserve.findReserve", id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return reserve;
    }

    //根据userId查找预约记录
    public List<Reserve> allReserve(int userId) {
        SqlSession sqlSession = null;
        List<Reserve> list = new ArrayList<Reserve>();
        try {
            sqlSession = DB.getSqlsession();
            list = sqlSession.selectList("Reserve.findReserveByUserId", userId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return list;
    }

    //根据bookId查找该书正在被预约的记录，一本书只允许一个预约者
    public Reserve findCurrentReserveByBookId(int bookId) {
        SqlSession sqlSession = null;
        Reserve reserve = null;
        try {
            sqlSession = DB.getSqlsession();
            reserve = sqlSession.selectOne("Reserve.findCurrentReserveByBookId", bookId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return reserve;
    }

    //根据id查找通知
    public List<Reserve> allNotice(int userId) {
        SqlSession sqlSession = null;
        List<Reserve> list = new ArrayList<Reserve>();
        try {
            sqlSession = DB.getSqlsession();
            list = sqlSession.selectList("Reserve.findNotice", userId);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return list;
    }

    //删除通知（失效）
    public List<Reserve> deleteNotice(int id) {
        SqlSession sqlSession = null;
        List<Reserve> list = new ArrayList<Reserve>();
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.deleteNotice", id);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return list;
    }


}
