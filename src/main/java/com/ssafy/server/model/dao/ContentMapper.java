package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Content;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper {

    Content selectContent(int contentId);

}
