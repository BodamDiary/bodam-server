package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.Diary;

import java.util.List;

public interface DiaryService {
    List<Diary> getAllDiaries(int userId);

    Diary getDiary(int diaryId);

    boolean deleteDiary(int diaryId, int userId);

    boolean registDiary(Diary diary);

    boolean updateDiary(Diary diary);
}
