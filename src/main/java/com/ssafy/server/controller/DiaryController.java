package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Diary;
import com.ssafy.server.model.service.DiaryService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/get-all-diaries")
    ResponseEntity<List<Diary>> getAllDiaries(Authentication authentication) {

        int userId = Integer.parseInt(authentication.getName());

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
    ResponseEntity<Diary> getDiary(@PathVariable int diaryId, Authentication authentication) {

        int userId = Integer.parseInt(authentication.getName());
        System.out.println("userId=" + userId);

        Diary diary = diaryService.getDiary(diaryId);

        if (diary != null) {
            if (diary.getUserId() != userId) {
                System.out.println("userId not matching");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            System.out.println(diary);
            return ResponseEntity.ok(diary);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete-diary/{diaryId}")
    ResponseEntity<String> deleteDiary(@PathVariable int diaryId, Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());

        System.out.println("deleteDiary");
        boolean isDeleted = diaryService.deleteDiary(diaryId, userId);
        System.out.println(isDeleted);

        if (isDeleted) {
            return  ResponseEntity.ok("Diary Deleted Successfully");
        }

        return ResponseEntity.badRequest().body("Diary Deletion Failed");
    }

    @PostMapping("/regist-diary")
    ResponseEntity<Integer> registDiary(@RequestBody Diary diary, Authentication authentication) {
        System.out.println("registDiary");

        int userId = Integer.parseInt(authentication.getName());

        diary.setUserId(userId);
        boolean isRegistered = diaryService.registDiary(diary);
        System.out.println(diary.getDiaryId());

        if (isRegistered) {
            return ResponseEntity.ok(diary.getDiaryId());
        }

        return ResponseEntity.badRequest().build();
    }
}
