package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Diary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {
    List<Diary> selectAllDiaries(int userId);

    Diary selectDiary(int diaryId);

    int deleteDiary(int diaryId);
}
