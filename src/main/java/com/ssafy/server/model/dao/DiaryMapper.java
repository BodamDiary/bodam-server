package com.ssafy.server.model.dao;

import com.ssafy.server.model.dto.Diary;
import com.ssafy.server.model.dto.DiaryImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {
    List<Diary> selectAllDiaries(int userId);

    Diary selectDiary(int diaryId);

    int deleteDiary(int diaryId);

    boolean insertDiary(Diary diary);

    boolean insertDiaryImage(DiaryImage diaryImage);
}
