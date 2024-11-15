package com.ssafy.server.controller;

import com.ssafy.server.model.dto.Bodam;
import com.ssafy.server.model.service.BodamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bodam")
public class BodamController {

    @Autowired
    BodamService bodamService;

    @PostMapping("/regist-bodam")
    public ResponseEntity<String> registBodam(@RequestBody Bodam bodam) {
        int isRegistered = bodamService.registBodam(bodam);

        if (isRegistered > 0) {
            return ResponseEntity.ok().body("Bodam registered successfully");
        }

        return ResponseEntity.badRequest().body("Bodam register failed");
    }

}
