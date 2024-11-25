package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.dto.Family;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BodamMapper {

    int insertBodam(Bodam bodam);

    Bodam selectBodamByUser(int bodamId);

    int insertFamily(Family family);

    Integer selectBodamIdByUser(int userId);

    int updateBodam(int bodamId);
    
    int selectUserIdByBodamId(int bodamId);

}
