package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.DiaryMapper;
import com.ssafy.server.model.dto.Diary;
import com.ssafy.server.model.dto.DiaryImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    DiaryMapper diaryMapper;
    @Autowired
    private S3Validator s3Validator;
    @Autowired
    S3Uploader s3Uploader;

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
    public boolean deleteDiary(int diaryId, int userId) {
        Diary diary = diaryMapper.selectDiary(diaryId);
        if (diary.getUserId() != userId) {
            return false;
        }

        return diaryMapper.deleteDiary(diaryId) == 1;
    }

    @Override
    public boolean registDiary(Diary diary) {
        return diaryMapper.insertDiary(diary) == 1;
    }

    @Override
    public boolean updateDiary(Diary diary) {
        return diaryMapper.updateDiary(diary) == 1;
    }

    @Override
    public List<String> getDiaryImage(int diaryId) {
        return diaryMapper.selectDiaryImage(diaryId);
    }

    @Override
    public List<String> uploadDiaryImages(MultipartFile[] files, int diaryId) {
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }
        try{

            for (MultipartFile file : files) {
                s3Validator.validateImageFile(file);
            }

            List<String> imageUrls = s3Uploader.uploadMultipleFiles(files, "diary");
            List<String> imagePaths = new ArrayList<>();
            System.out.println("이미지 몇개일까? :: " + imageUrls.size());
            for (String imageUrl : imageUrls) {
                System.out.println("이미지 버킷에 업로드 된 뒤 : ");
                DiaryImage diaryImage = new DiaryImage();
                System.out.println("diaryImage초기화 완료");
                diaryImage.setDiaryId(diaryId);
                System.out.println("set 다이어리 아이디");
                diaryImage.setCreatedAt(LocalDateTime.now());
                System.out.println("set 현재 시간");
                diaryImage.setFilePath(imageUrl);
                System.out.println("set image Path");

                boolean isUploadImage = diaryMapper.insertDiaryImage(diaryImage);
                System.out.println("isUploadImage: " + isUploadImage);
                System.out.println("이미지 DB에 업로드 한 개 완료!");
                imagePaths.add(imageUrl);
            }

            System.out.println("이미지가 모두 업로드 됨");
            return imagePaths;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
