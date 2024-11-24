package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public int registUser(User user);

    public User getUser(int userId);

    public User getUserByEmail(String email);

    public List<User> getAllUsers();

    public int modifyUser(User user);

    public int removeUser(int userId);

    User loginUser(String email, String password);

    public String uploadProfileImage(MultipartFile file, User user);

    public String deleteProfileImage(User user);
}
