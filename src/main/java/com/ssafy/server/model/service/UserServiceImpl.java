package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.dto.KakaoUser;
import com.ssafy.server.model.dto.User;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService{

    private UserMapper userDao;

    @Override
    public int registUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int registKakaoUser(KakaoUser kakaoUser) {
        return userDao.insertKakaoUser(kakaoUser);
    }
}
