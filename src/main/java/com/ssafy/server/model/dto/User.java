package com.ssafy.server.model.dto;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
public class User {
    private int userId;
    private String email, kakaoId, nickname, userName, password, address, phoneNumber;
    private boolean agreeCondition, isOauth;
    private LocalDateTime createdAt;
    private MultipartFile profileImage;

    public User() {
        super();
    }

    public User(int userId, String email, String kakaoId, String nickname, String userName, String password, String address, String phoneNumber, boolean agreeCondition, boolean isOauth, LocalDateTime createdAt, MultipartFile profileImage) {
        this.userId = userId;
        this.email = email;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.agreeCondition = agreeCondition;
        this.isOauth = isOauth;
        this.createdAt = createdAt;
        this.profileImage = profileImage;
    }

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

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", kakaoId='" + kakaoId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userName='" + userName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", agreeCondition=" + agreeCondition +
                ", isOauth=" + isOauth +
                ", createdAt=" + createdAt +
                '}';
    }
}
