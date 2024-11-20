package com.ssafy.server.controller;

import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.UserService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/regist-user")
    public ResponseEntity<String> registUser(@RequestBody User user, HttpServletResponse response){

        int successUser = userService.registUser(user);
        if (successUser > 0) {
            Cookie cookie = new Cookie("email", user.getEmail());
            response.addCookie(cookie);

            return ResponseEntity.ok("Regist user successfully");
        }

        return ResponseEntity.badRequest().body("Regist user failed");

    }


    @GetMapping("/get-user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId){

        User user = userService.getUser(userId);
        System.out.println(user);

        if (user != null) {

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        if (users != null) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/update-user/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("userId") int userId){
        user.setUserId(userId);
        int isUpdated = userService.modifyUser(user);

        if (isUpdated > 0){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId){
        int isDeleted = userService.removeUser(userId);
        if (isDeleted > 0){
            return ResponseEntity.ok("Delete user successfully");
        }
        return ResponseEntity.badRequest().body("Delete user failed");
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loginUser = userService.loginUser(user.getEmail(), user.getPassword());

        if (loginUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(loginUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저가 없습니다.");
    }

}
