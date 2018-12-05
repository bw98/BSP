package com.BSP.Service;

import com.BSP.DAO.UserDAO;
import com.BSP.bean.User;

public class UserService {
    UserDAO userDAO = new UserDAO();

    public boolean regist(User user) {
        User u = userDAO.findUserByName(user);
        if (u != null) {
            return false;
        }
        userDAO.addUser(user);
        return true;
    }

    public int login(User user) {
        User u = userDAO.findUserByName(user);
        if (u == null) {
            return 2;
        }
        if (!user.getPassword().equals(u.getPassword())) {
            return 1;
        }
        if (u.getUserName() == "admin") {
            return 4;
        }

        return 3;
    }
}
