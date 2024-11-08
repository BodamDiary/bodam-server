package com.ssafy.server.controller;

import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/users/regist-user")
    public Map registUser(@RequestBody User user){
        Map<String, Object> retMap = new HashMap<>();

        int successUser = userService.registUser(user);

        retMap.put("userId", user.getUserId());
        retMap.put("email", user.getEmail());
        retMap.put("kakaoId", user.getKakaoId());
        retMap.put("nickname", user.getNickname());
        retMap.put("userName", user.getUserName());
        retMap.put("password", user.getPassword());
        retMap.put("profileImage", user.getProfileImage());
        retMap.put("address", user.getAddress());
        retMap.put("phoneNumber", user.getPhoneNumber());
        retMap.put("agreeCondition", user.isAgreeCondition());
        retMap.put("createdAt", user.getCreatedAt());
        System.out.println("regist-user retMap ::" + retMap);
        return retMap;
    }
}
