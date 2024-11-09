package com.ssafy.server.model.daosc;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface SaltMapper {

    public int insertSecuInfo(@Param("userId") int userId, @Param("salt") String salt);

    public String selectSalt(int userId);

}
