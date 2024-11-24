package com.ssafy.server.model.dto;

import java.time.LocalDateTime;

public class DiaryImage {

    private int diaryImageId, diaryId;
    private String filePath;
    private LocalDateTime createdAt;

    public int getDiaryImageId() {
        return diaryImageId;
    }

    public void setDiaryImageId(int diaryImageId) {
        this.diaryImageId = diaryImageId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DiaryImage{" +
                "diaryImageId=" + diaryImageId +
                ", diaryId=" + diaryId +
                ", filePath='" + filePath + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
