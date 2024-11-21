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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bodam")
public class BodamController {

    @Autowired
    BodamService bodamService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/regist-bodam")
    public ResponseEntity<String> registBodam(@RequestBody Bodam bodam, HttpServletRequest request, HttpServletResponse response) {
        String uToken = null;

        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("uToken")) {
                uToken = c.getValue();
                break;
            }
        }

        if (uToken == null) {
            return ResponseEntity.badRequest().body("Bodam register failed");
        }

        boolean isValid = jwtTokenProvider.validToken(uToken);
        if (!isValid) {
            return ResponseEntity.badRequest().body("Bodam register failed");
        }

        int isRegistered = bodamService.registBodam(bodam);

        if (isRegistered > 0) {
            return ResponseEntity.ok().body("Bodam registered successfully");
        }

        return ResponseEntity.badRequest().body("Bodam register failed");
    }

    @GetMapping("/get-bodam")
    public ResponseEntity<Bodam> getBodam(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("session null");
            return ResponseEntity.badRequest().build();
        }

        String uToken = (String)session.getAttribute("uToken");
        if (uToken == null || !jwtTokenProvider.validToken(uToken)) {
            System.out.println("token invalid");
            return ResponseEntity.badRequest().build();
        }

        int userId = jwtTokenProvider.getIdFromToken(uToken);

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
