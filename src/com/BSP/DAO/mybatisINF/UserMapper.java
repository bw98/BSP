package com.BSP.DAO.mybatisINF;

import com.BSP.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    public User findUserByName(String userName);

    public void addUser(@Param("userName") String userName, @Param("password") String password, @Param("tel") String tel, @Param("status") int status);
}
