package com.ssafy.server.controller;

import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.S3Uploader;
import com.ssafy.server.model.service.UserService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    S3Uploader s3Uploader;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/regist-user")
    public ResponseEntity<String> registUser(@RequestBody User user, HttpServletResponse response){

        int successUser = userService.registUser(user);
        System.out.println("회원가입 성공한 유저 cnt ::: " + successUser);
        if (successUser > 0) {

            return ResponseEntity.ok("Regist user successfully");
        }

        return ResponseEntity.badRequest().body("Regist user failed");

    }


    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(Authentication authentication){

        int userId = Integer.parseInt(authentication.getName());
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

    @GetMapping("/delete-user")
    public ResponseEntity<String> deleteUser(Authentication authentication, HttpSession session){
        int userId = Integer.parseInt(authentication.getName());
        int isDeleted = userService.removeUser(userId);

        if (isDeleted > 0){
            session.invalidate();
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
            String uToken = jwtTokenProvider.generateJwt(loginUser.getUserId(), 30);
            HttpSession session = request.getSession();
            session.setAttribute("uToken", uToken);

            return ResponseEntity.status(HttpStatus.OK).body(uToken);
        }
        System.out.println("loginUser Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/update-user/profile-image/upload")
    public ResponseEntity<Map<String, String>> updateUserProfileImage(
            @RequestParam("profileImage") MultipartFile file,
            Authentication authentication) {
        try {
            int userId = Integer.parseInt(authentication.getName());
            User user = userService.getUser(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "사용자를 찾을 수 없습니다."));
            }

            // 이미지 URL을 반환받도록 수정
            String imageUrl = userService.uploadProfileImage(file, user);
            Map<String, String> response = new HashMap<>();

            response.put("message", "프로필 이미지가 성공적으로 업로드되었습니다.");
            response.put("imageUrl", imageUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "프로필 업로드에 실패했습니다."));
        }
    }

    @PostMapping("/update-user/profile-image/delete")
    public ResponseEntity<Map<String, String>> deleteUserProfileImage(
            Authentication authentication) {
        try {
            int userId = Integer.parseInt(authentication.getName());
            User user = userService.getUser(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "사용자를 찾을 수 없습니다."));
            }

            userService.deleteProfileImage(user);

            return ResponseEntity.ok(Map.of("message", "프로필 이미지가 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "프로필 이미지 삭제에 실패했습니다."));
        }
    }



    @GetMapping("/logout-user")
    public ResponseEntity<String> logoutUser(Authentication authentication, HttpSession session) {

        System.out.println("로그아웃");
        session.invalidate();

        return null;
    }

}
