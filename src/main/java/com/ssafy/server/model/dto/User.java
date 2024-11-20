package com.ssafy.server.model.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String email;
    private String kakaoId;
    private String nickname;
    private String userName;
    private String password;
    private String address;
    private String phoneNumber;
    private String profileImage;
    private boolean agreeCondition;
    private boolean isOauth;
    private LocalDateTime createdAt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isAgreeCondition() {
        return agreeCondition;
    }

    public void setAgreeCondition(boolean agreeCondition) {
        this.agreeCondition = agreeCondition;
    }

    public boolean isOauth() {
        return isOauth;
    }

    public void setOauth(boolean oauth) {
        isOauth = oauth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", kakaoId='" + kakaoId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", agreeCondition=" + agreeCondition +
                ", isOauth=" + isOauth +
                ", createdAt=" + createdAt +
                '}';
    }
}