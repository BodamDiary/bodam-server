package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.dto.Family;
import com.ssafy.server.model.service.BodamService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bodam")
public class BodamController {

    @Autowired
    BodamService bodamService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @PostMapping("/regist-bodam")
    public ResponseEntity<String> registBodam(@RequestBody Bodam bodam, Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());
        System.out.println("컨트롤러 들어옴 UserId :: " + userId);
        Family family = new Family();
        System.out.println("family ::" + family.toString());
        family.setUserId(userId);
        int isRegistered = bodamService.registBodam(bodam, family);

        System.out.println("isRegistered ::" + isRegistered);
        if (isRegistered > 0) {
            System.out.println("registered successfully");
            return ResponseEntity.ok().body("Bodam registered successfully");
        }

        System.out.println("register failed");
        return ResponseEntity.badRequest().body("Bodam register failed");
    }


    @GetMapping("/get-bodam")
    public ResponseEntity<Bodam> getBodam(Authentication authentication) {

        int userId = Integer.parseInt(authentication.getName());
        System.out.println("이거 찍혔니 ? userId = " + userId);
        Bodam bodam = bodamService.getBodamByUser(userId);
        System.out.println(bodam);

        if (bodam != null) {
            return ResponseEntity.ok().body(bodam);
        }

        System.out.println("bodam not found");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @PostMapping("/update-bodam")
    public ResponseEntity<String> updateBodam(Authentication authentication, @RequestBody Bodam bodam) {
        int bodamId = bodam.getBodamId();
        int userId = Integer.parseInt(authentication.getName());
        int registedUserId = bodamService.getUserIdByBodam(bodamId);

        if (userId != registedUserId){
            System.out.println("보담 보호자 정보와 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        int isUpdated = bodamService.modifyBodam(bodamId);
        if (isUpdated > 0) {
            return ResponseEntity.ok().body("Bodam updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
