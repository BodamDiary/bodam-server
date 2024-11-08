package com.ssafy.server.model.service;

import com.ssafy.server.model.daosc.SaltMapper;
import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.util.OpenCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SaltMapper saltMapper;

    @Override
    public int registUser(User user) {

        String salt = OpenCrypt.encryptPw(user);

        userMapper.insertUser(user);

        saltMapper.insertSecuInfo(user.getUserId(), salt);

        return 0;
    }

    @Override
    public User loginUser(String email, String password) {

        User user = userMapper.selectUser(email);
        String salt = saltMapper.selectSalt(user.getUserId());

        String hashedPassword = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(password, salt));

        if (user.getPassword().equals(hashedPassword)) {
            user.setPassword("");
            return user;
        }

        return null;
    }

}
