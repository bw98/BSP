package com.BSP.DAO;

import com.BSP.bean.Reserve;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReserveDAO {
    database DB=new database();
    //增加预约记录
    public void addReserve(Reserve reserve){
        SqlSession sqlSession =null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.insert("Reserve.reserveBook",reserve);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    //删除预约
    public void deleteReserve(int id){
        SqlSession sqlSession =null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Reserve.deleteReserve",id);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    //根据id查找预约记录
    public Reserve findReserveById(int id){
        SqlSession sqlSession=null;
        Reserve reserve=null;
        try {
            sqlSession=DB.getSqlsession();
            reserve=sqlSession.selectOne("Reserve.findReserve",id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return reserve;
    }

    //根据userid查找预约记录
    public List<Reserve> allReserve(int userId){
        SqlSession sqlSession=null;
        List<Reserve> list = new ArrayList<Reserve>();
        try {
            sqlSession=DB.getSqlsession();
            list=sqlSession.selectList("Reserve.findReserveByUserId",userId);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return list;
    }

    //根据id查找通知
    public List<Reserve> allNotice(int userId){
        SqlSession sqlSession=null;
        List<Reserve> list = new ArrayList<Reserve>();
        try {
            sqlSession=DB.getSqlsession();
            list=sqlSession.selectList("Reserve.findNotice",userId);
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
