package com.ssafy.server.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Validator {

    public void validateImageFile(MultipartFile file) {
        if (file.isEmpty()){
            throw new IllegalArgumentException("이미지 파일이 비었습니다.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }

        if (file.getSize() > 5 * 1024 * 1024){
            throw new IllegalArgumentException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }

    public String extractKeyFromUrl(String url){
        return url.substring(url.lastIndexOf("profile/"));
    }

}
