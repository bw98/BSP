package com.BSP.DAO.mybatisINF;

import com.BSP.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findUserByName(String userName);

    void addUser(User user);

    void updateUserOnPassword(@Param("userName") String userName, @Param("password") String password);

    void updateUserOntel(@Param("userName") String userName, @Param("tel") String tel);

}
