package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.DiaryMapper;
import com.ssafy.server.model.dto.Diary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryAnalysisService {

    private final DiaryMapper diaryMapper;
    private final ChatGptService chatGptService;

    public DiaryAnalysisService(DiaryMapper diaryMapper, ChatGptService chatGptService) {
        this.diaryMapper = diaryMapper;
        this.chatGptService = chatGptService;
    }

    public String analyzeDiaries(int userId) {
        List<Diary> diaries = diaryMapper.selectAllDiaries(userId);

        // 다이어리 본문 추출
        StringBuilder allBodies = new StringBuilder();
        for (Diary diary : diaries) {
            allBodies.append(diary.getBody()).append("\n\n");
        }
        System.out.println(allBodies);

        // ChatGPT로 분석 요청
        return chatGptService.analyze(allBodies.toString());
    }
}
