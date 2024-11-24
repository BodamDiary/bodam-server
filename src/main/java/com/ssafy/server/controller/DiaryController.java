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

    @PostMapping("/update-diary/{diaryId}")
    ResponseEntity<String> updateDiary(@RequestBody Diary diary, @PathVariable int diaryId, Authentication authentication) {
        System.out.println("update-diary");
        int userId = Integer.parseInt(authentication.getName());
        System.out.println("userId="+userId);

        Diary diary1 = diaryService.getDiary(diaryId);

        if (diary1 == null) {
            return ResponseEntity.badRequest().body("일기 수정이 정상적으로 처리되지 않았습니다.");
        }
        if (diary1.getUserId() != userId) {
            System.out.println("unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        diary.setDiaryId(diaryId);
        diary.setUserId(userId);
        boolean isUpdated = diaryService.updateDiary(diary);

        if(isUpdated) {
            System.out.println("diary updated");
            return ResponseEntity.ok("일기 수정이 완료되었습니다.");
        }

        System.out.println("diary update fail");
        return ResponseEntity.badRequest().body("일기 수정이 정상적으로 처리되지 않았습니다.");
    }
}
