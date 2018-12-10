package com.BSP.Service;

import com.BSP.DAO.UserDAO;

public class UserService {

<<<<<<< HEAD
   /* public void regist (User user) throws UserException{
=======
    public boolean regist(User user) {
        UserDAO userDAO = new UserDAO();
>>>>>>> 6fdc48eb556ee963280fedcd765b43f3caba01a7
        User u = userDAO.findUserByName(user);
        if (u != null) {
            throw new UserException("用户名" + u.getUserName() + "已经存在!!!");
        }
        userDAO.addUser(user);
    }*/

<<<<<<< HEAD
}
=======
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
>>>>>>> 6fdc48eb556ee963280fedcd765b43f3caba01a7
