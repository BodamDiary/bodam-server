package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.service.BodamService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bodam")
public class BodamController {

    @Autowired
    BodamService bodamService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/regist-bodam")
    public ResponseEntity<String> registBodam(@RequestBody Bodam bodam, Authentication authentication, HttpServletResponse response) {
        int userId = Integer.parseInt(authentication.getName());
        bodam.setUserId(userId);

        int isRegistered = bodamService.registBodam(bodam);

        if (isRegistered > 0) {
            return ResponseEntity.ok().body("Bodam registered successfully");
        }

        return ResponseEntity.badRequest().body("Bodam register failed");
    }

    @GetMapping("/get-bodam")
    public ResponseEntity<Bodam> getBodam(Authentication authentication) {

        int userId = Integer.parseInt(authentication.getName());

        Bodam bodam = bodamService.getBodamByUser(userId);
        System.out.println(bodam);

        if (bodam != null) {
            return ResponseEntity.ok().body(bodam);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/update-bodam")
    public ResponseEntity<String> updateBodam() {

        return null;
    }
}
