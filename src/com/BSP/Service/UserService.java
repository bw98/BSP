package com.BSP.service;

import com.BSP.DAO.UserDAO;
import com.BSP.bean.User;

public class Userservice {
    UserDAO userDAO = new UserDAO();

    public void regist (User user) throws UserException{
        User u = userDAO.findUserByName(user);
        if (u != null) {
            throw new UserException("用户名" + u.getUserName() + "已经存在!!!");
        }
        userDAO.addUser(user);
    }

}
