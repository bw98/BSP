package com.BSP.Service;

import com.BSP.DAO.UserDAO;

public class UserService {
    UserDAO userDAO = new UserDAO();

   /* public void regist (User user) throws UserException{
        User u = userDAO.findUserByName(user);
        if (u != null) {
            throw new UserException("用户名" + u.getUserName() + "已经存在!!!");
        }
        userDAO.addUser(user);
    }*/

}
