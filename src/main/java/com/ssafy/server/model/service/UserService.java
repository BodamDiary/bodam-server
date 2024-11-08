package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.User;

public interface UserService {
    int registUser(User user);

    User loginUser(String email, String password);
}
