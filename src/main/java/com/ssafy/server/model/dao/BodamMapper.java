package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Bodam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BodamMapper {

    int insertBodam(Bodam bodam);

}
