package com.BSP.DAO;

import com.BSP.DAO.mybatisINF.UserMapper;
import com.BSP.bean.User;
import org.apache.ibatis.session.SqlSession;

public class UserDAO {
    public User findUserByName(User user) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.findUserByName(user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return null;
    }

    public void addUser(User user) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.addUser(user);
            sqlSession.commit();
            // 如果添加user后需要返回id，可以把method type改成int并取消下一行的注释
            // return user.getId();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public void updateUserOnPassword(User user) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            System.out.println("UserDAO's updateUserOnPassword: " + user.getUserName() + " " + user.getPassword() + " " + user.getTel() + " " + user.getStatus());
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.updateUserOnPassword(user.getUserName(), user.getPassword());
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    public void updateUserOntel(User user) {
        database db = new database();
        SqlSession sqlSession = null;
        try {
            sqlSession = db.getSqlsession();
            System.out.println("UserDAO's updateUserOntel: " + user.getUserName() + " " + user.getPassword() + " " + user.getTel() + " " + user.getStatus());
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.updateUserOntel(user.getUserName(), user.getTel());
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }


}
