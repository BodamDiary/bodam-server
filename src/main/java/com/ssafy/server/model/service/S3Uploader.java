package com.ssafy.server.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Component
@Slf4j
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 받아서 파일을 로컬 시스템에 저장, S3에 업로드, 업로드된 파일의 URL을 반환
    public String uploadFile(MultipartFile multipartFile, String dirName) throws IOException{
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // UUID와 확장자로 새로운 파일명 생성
        String fileName = dirName + "/" + UUID.randomUUID() + extension;

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .acl("public-read")
                    .contentType(multipartFile.getContentType())
                    .build();

            byte[] bytes = multipartFile.getBytes();
            RequestBody requestBody = RequestBody.fromBytes(bytes);

            s3Client.putObject(putObjectRequest, requestBody);

        }catch (IOException e){
            log.error("파일 업로드 중 에러 발생");
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
        String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileName;
        System.out.println("fileURL ::: " + fileUrl);
        return fileUrl;
    }

    public List<String> uploadMultipleFiles(MultipartFile[] multipartFiles, String dirName) throws IOException{
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = dirName + "/" + UUID.randomUUID() + extension;

            try{
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .acl("public-read")
                        .contentType(multipartFile.getContentType())
                        .build();

                byte[] bytes = multipartFile.getBytes();
                RequestBody requestBody = RequestBody.fromBytes(bytes);

                s3Client.putObject(putObjectRequest, requestBody);

            }catch (IOException e){
                log.error("파일 업로드 중 에러 발생");
                throw new RuntimeException("파일 업로드에 실패했습니다.", e);
            }
            String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + fileName;
            System.out.println("fileURL ::: " + fileUrl);

            fileUrls.add(fileUrl);
        }
        return fileUrls;
    }

    public void deleteFile(String fileName){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }



}
