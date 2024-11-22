package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Content;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMapper {

    Content selectContent(int contentId);

    List<Content> selectTodayContent();
}
