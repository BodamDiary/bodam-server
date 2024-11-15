package com.ssafy.server.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class Bodam {

    private int userId, bodamId, communicationAbility, cognitiveAbility, performanceAbility;
    private String bodamName, bodamGender;

    private Date birthday;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBodamId() {
        return bodamId;
    }

    public void setBodamId(int bodamId) {
        this.bodamId = bodamId;
    }

    public int getCommunicationAbility() {
        return communicationAbility;
    }

    public void setCommunicationAbility(int communicationAbility) {
        this.communicationAbility = communicationAbility;
    }

    public int getCognitiveAbility() {
        return cognitiveAbility;
    }

    public void setCognitiveAbility(int cognitiveAbility) {
        this.cognitiveAbility = cognitiveAbility;
    }

    public int getPerformanceAbility() {
        return performanceAbility;
    }

    public void setPerformanceAbility(int performanceAbility) {
        this.performanceAbility = performanceAbility;
    }

    public String getBodamName() {
        return bodamName;
    }

    public void setBodamName(String bodamName) {
        this.bodamName = bodamName;
    }

    public String getBodamGender() {
        return bodamGender;
    }

    public void setBodamGender(String bodamGender) {
        this.bodamGender = bodamGender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Bodam{" +
                "userId=" + userId +
                ", bodamId=" + bodamId +
                ", communicationAbility=" + communicationAbility +
                ", cognitiveAbility=" + cognitiveAbility +
                ", performanceAbility=" + performanceAbility +
                ", bodamName='" + bodamName + '\'' +
                ", bodamGender='" + bodamGender + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
