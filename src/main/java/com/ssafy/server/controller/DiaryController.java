package com.ssafy.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.server.model.dto.Diary;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.DiaryService;
import com.ssafy.server.model.service.UserService;
import com.ssafy.server.model.service.UserServiceImpl;
import com.ssafy.server.util.JwtTokenProvider;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private GenericResponseService responseBuilder;
    @Autowired
    private UserServiceImpl userServiceImpl;

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
            List<String> imagePath = diaryService.getDiaryImage(diaryId);
            System.out.println(imagePath);
            if (imagePath != null) {
                diary.setFilePaths(imagePath);
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
    ResponseEntity<Map<String, Object>> registDiary(@RequestParam(value = "diaryImages", required = false) MultipartFile[] files, @RequestParam("diary") String diaryJson, Authentication authentication) {
        System.out.println("registDiary");

        ObjectMapper mapper = new ObjectMapper();
        Diary diary;
        try{
            diary=mapper.readValue(diaryJson, Diary.class);
            int userId = Integer.parseInt(authentication.getName());
            System.out.println("userId=" + userId);
            diary.setUserId(userId);

            User user = userService.getUser(userId);
            if (user == null){
                System.out.println("유저를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "사용자를 찾을 수 없습니다."));
            }
            boolean isRegistered = diaryService.registDiary(diary);
            if (!isRegistered) {
                System.out.println("다이어리가 등록되지 않았습니다. 1");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "다이어리 등록에 실패했습니다."));
            }

            int diaryId = diary.getDiaryId();
            System.out.println("diaryId = "+ diaryId);

            List<String> imageUrls = diaryService.uploadDiaryImages(files, diaryId);
            Map<String, Object> response = new HashMap<>();
            if (imageUrls.size() == 0) {
                System.out.println("다이어리 이미지가 없습니다 2");
            }
            System.out.println("다이어리가 성공적으로 등록되었습니다. ");
            response.put("message", "다이어리가 성공적으로 등록되었습니다.");
            response.put("imageUrls", imageUrls);
            response.put("diaryId", diaryId);

            return ResponseEntity.ok(response);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "게시글 등록에 실패했습니다."));
        }

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
