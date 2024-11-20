package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.DiaryMapper;
import com.ssafy.server.model.dto.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    DiaryMapper diaryMapper;

    @Override
    public List<Diary> getAllDiaries(int userId) {
        //사용자 검증 필요
        return diaryMapper.selectAllDiaries(userId);
    }

    @Override
    public Diary getDiary(int diaryId) {
        //사용자 검증 필요
        return diaryMapper.selectDiary(diaryId);
    }

    @Override
    public boolean deleteDiary(int diaryId) {
        return diaryMapper.deleteDiary(diaryId) == 1;
    }

    @Override
    public boolean registDiary(Diary diary) {
        return diaryMapper.insertDiary(diary);
    }


}
