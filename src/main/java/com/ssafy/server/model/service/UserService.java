package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.KakaoUser;
import com.ssafy.server.model.dto.User;

import java.time.LocalDateTime;

public interface UserService {
    public int registUser(User user);
    public int registKakaoUser(KakaoUser kakaoUser);
}
