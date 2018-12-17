package com.BSP.DAO;

import com.BSP.bean.Rent;
import com.BSP.bean.Reserve;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentDAO {
    database DB=new database();
    //增加租赁记录
    public void addRent(Rent rent){
        SqlSession sqlSession =null;
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.insert("Rent.rentBook",rent);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    //删除租赁
    public void deleteRent(int id){
        SqlSession sqlSession =null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession = DB.getSqlsession();
            sqlSession.update("Rent.deleteRent",id);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }

    //根据id查询租赁记录
    public Rent findRentById(int id){
        SqlSession sqlSession=null;
        Rent rent=null;
        try {
            sqlSession=DB.getSqlsession();
            rent=sqlSession.selectOne("Rent.findRent",id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return rent;
    }

    //根据userid查找租赁记录
    public List<Rent> allRent(int userId){
        SqlSession sqlSession=null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession=DB.getSqlsession();
            list=sqlSession.selectList("Rent.findRentByUserId",userId);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return list;
    }

    public List<Rent> allOverRent(){
        SqlSession sqlSession=null;
        List<Rent> list = new ArrayList<Rent>();
        try {
            sqlSession=DB.getSqlsession();
            list=sqlSession.selectList("Rent.findAllRent");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return list;
    }

    public Rent findRentByUseridBookid(Rent rent){
        SqlSession sqlSession=null;
        Rent rent1=new Rent();
        try {
            sqlSession=DB.getSqlsession();
            rent1=sqlSession.selectOne("Rent.findRentByUseridBookid",rent);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
        return rent1;
    }
}
