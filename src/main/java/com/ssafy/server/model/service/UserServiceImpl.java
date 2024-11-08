package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public int registUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User getUser(int userId) {
        return userMapper.selectUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int modifyUser(User user) {
        int isModifyUser = userMapper.updateUser(user);
        return isModifyUser;
    }

    @Override
    public int removeUser(int userId) {
        int isRemoveUser = userMapper.deleteUser(userId);
        return isRemoveUser;
    }

}
