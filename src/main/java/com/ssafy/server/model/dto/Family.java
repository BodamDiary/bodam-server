package com.ssafy.server.model.dto;

public class Family {

    private int userId, bodamId;

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

    @Override
    public String toString() {
        return "Family{" +
                "userId=" + userId +
                ", bodamId=" + bodamId +
                '}';
    }
}
