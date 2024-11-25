package com.ssafy.server.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Bodam {

    private int bodamId, communicationAbility, cognitiveAbility, performanceAbility;
    private String bodamName;

    private LocalDate birthday;

    public String getStringBirthday() {
        return stringBirthday;
    }

    public void setStringBirthday(String stringBirthday) {
        this.stringBirthday = stringBirthday;
    }

    private String stringBirthday;

    private Gender bodamGender;

    public int getBodamId() {
        return bodamId;
    }

    public void setBodamId(int bodamId) {
        this.bodamId = bodamId;
    }

    public int getCommunivationAbility() {
        return communicationAbility;
    }

    public void setCommunivationAbility(int communivationAbility) {
        this.communicationAbility = communivationAbility;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getBodamGender() {
        return bodamGender;
    }

    public void setBodamGender(Gender bodamGender) {
        this.bodamGender = bodamGender;
    }

    @Override
    public String toString() {
        return "Bodam{" +
                "bodamId=" + bodamId +
                ", communicationAbility=" + communicationAbility +
                ", cognitiveAbility=" + cognitiveAbility +
                ", performanceAbility=" + performanceAbility +
                ", bodamName='" + bodamName + '\'' +
                ", birthday=" + birthday +
                ", bodamGender=" + bodamGender +
                '}';
    }
}
