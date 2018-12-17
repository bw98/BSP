package com.BSP.DAO.mybatisINF;

import com.BSP.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User findUserByName(String userName);

    User findUserById(@Param("id") int id);

    void addUser(User user);

    void updateUserOnPassword(@Param("userName") String userName, @Param("password") String password);

    void updateUserOntel(@Param("userName") String userName, @Param("tel") String tel);

    List<User> findAllUser();

    void deleteUser(@Param("id") int id);
}
