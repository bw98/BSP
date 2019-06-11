package com.BSP.Service;

import com.BSP.DAO.UserDAO;
import com.BSP.bean.User;

import java.util.List;

public class UserService {

    public List<User> findAllUser() {
        UserDAO userDAO = new UserDAO();
        return userDAO.findAllUser();
    }

    public void deleteUser(int id) {
        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(id);
    }

    public int findIdByUserName(String userName) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUserName(userName);
        User u = userDAO.findUserByName(user);
        return u.getId();
    }

    public boolean regist(User user) {
        UserDAO userDAO = new UserDAO();

        // User实例中除了status和id外的每一项属性都必须非空
        // 如果Bean中的属性类型是String，则 JSONObject.toBean 会把json串中缺失的键值转为null
        // 比如传入的串为{"userName":"lwf123123","password":"123456789"}，tel键值对是缺失的
        // 当使用 JSONObject.toBean 转化json串为User实例时，tel就会被设置为null
        if ((user.getUserName() == null) || (user.getPassword() == null) || (user.getTel() == null)) {
            return false;
        }

        // 查询是否在数据库中已存在该用户名的用户，存在的话就不允许注册
        User u = userDAO.findUserByName(user);
        if ((u != null)) {
            return false;
        }

        userDAO.addUser(user);
        return true;
    }


    public int login(User user) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.findUserByName(user);
        if (u == null) {
            return 2;
        }
        if (!user.getPassword().equals(u.getPassword())) {
            return 1;
        }

        if (user.getUserName().equals("admin") && user.getPassword().equals("admin")) {
            return 4;
        }

        return 3;
    }

    public boolean updateUserPassword(User user) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.findUserByName(user);
        if (u == null) {
            return false;
        }
        userDAO.updateUserOnPassword(user);
        return true;

    }

    public boolean updateUserTel(User user) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.findUserByName(user);
        if (u == null) {
            return false;
        }
        userDAO.updateUserOntel(user);
        return true;

    }

    public boolean updateUserStatus(User user, int status) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.findUserByName(user);

        if (u == null) {
            return false;
        }
        userDAO.updateUserStatus(user.getId(), status);
        return true;
    }

}
