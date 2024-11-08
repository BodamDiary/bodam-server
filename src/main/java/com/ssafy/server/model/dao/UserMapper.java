package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(User user);

}
