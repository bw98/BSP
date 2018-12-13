package com.BSP.Service;

import com.BSP.DAO.UserDAO;
import com.BSP.bean.User;
import org.omg.CORBA.UserException;

public class UserService {

    public int findIdByUserName(String userName) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUserName(userName);
        User u = userDAO.findUserByName(user);
        return u.getId();
    }

    public boolean regist(User user) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.findUserByName(user);
        if (u != null) {
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
        if (u.getUserName() == "admin" && u.getPassword() == "admin") {
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

}
