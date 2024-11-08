package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    User selectUser(int userId);

    List<User> selectAllUsers();

    int updateUser(User user);

    int deleteUser(int userId);
}
