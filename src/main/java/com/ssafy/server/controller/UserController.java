package com.ssafy.server.controller;

import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.UserService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

            return ResponseEntity.ok("Regist user successfully");
        }

        return ResponseEntity.badRequest().body("Regist user failed");

    }


    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("session null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String uToken = (String)session.getAttribute("uToken");
        if (uToken == null || !jwtTokenProvider.validToken(uToken)) {
            System.out.println("token invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int userId = jwtTokenProvider.getIdFromToken(uToken);
        System.out.println("userId="+userId);
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
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {

        String email = user.getEmail();
        String pw = user.getPassword();
        if (email == null || email.isEmpty() || pw == null || pw.isEmpty()) {
            System.out.println("null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User loginUser = userService.loginUser(email, pw);

        if (loginUser != null) {
            System.out.println("loginUser found");
            String uToken = jwtTokenProvider.generateJwt(loginUser.getUserId());
            HttpSession session = request.getSession();
            session.setAttribute("uToken", uToken);

            return ResponseEntity.status(HttpStatus.OK).body(uToken);
        }
        System.out.println("loginUser Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
