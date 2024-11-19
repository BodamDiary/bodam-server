package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Diary;
import com.ssafy.server.model.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @GetMapping("/get-all-diaries")
    ResponseEntity<List<Diary>> getAllDiaries() {
        int userId = 1;
        List<Diary> list = diaryService.getAllDiaries(userId);

        if (list != null) {
            if (!list.isEmpty()) {
                return ResponseEntity.ok(list);
            }
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get-diary/{diaryId}")
    ResponseEntity<Diary> getDiary(@PathVariable int diaryId) {
        Diary diary = diaryService.getDiary(diaryId);
        System.out.println(diary);

        if (diary != null) {
            return ResponseEntity.ok(diary);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete-diary/{diaryId}")
    ResponseEntity<String> deleteDiary(@PathVariable int diaryId) {
        System.out.println("deleteDiary");
        boolean isDeleted = diaryService.deleteDiary(diaryId);
        System.out.println(isDeleted);

        if (isDeleted) {
            return  ResponseEntity.ok("Diary Deleted Successfully");
        }

        return ResponseEntity.badRequest().body("Diary Deletion Failed");
    }
}
