package com.ssafy.server.model.dto;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class KakaoUser {
    private int userId;
    private String kakaoId, nickname, userName, profileImage, address, phoneNumber;
    private boolean agreeCondition;
    private LocalDateTime createdAt;

    public KakaoUser() {
        super();
    }

    public KakaoUser(int userId, String kakaoId, String nickname, String userName, String profileImage, String address, String phoneNumber, boolean agreeCondition, LocalDateTime createdAt) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.userName = userName;
        this.profileImage = profileImage;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.agreeCondition = agreeCondition;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAgreeCondition() {
        return agreeCondition;
    }

    public void setAgreeCondition(boolean agreeCondition) {
        this.agreeCondition = agreeCondition;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "KakaoUser{" +
                "userId=" + userId +
                ", kakaoId='" + kakaoId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userName='" + userName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", agreeCondition=" + agreeCondition +
                ", createdAt=" + createdAt +
                '}';
    }
}
