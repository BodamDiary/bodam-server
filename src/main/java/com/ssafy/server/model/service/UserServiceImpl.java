package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.daosc.SaltMapper;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.util.OpenCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SaltMapper saltMapper;

    private static final String PW_PATTERN =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";


    @Override
    public int registUser(User user) {
        String pw = user.getPassword();

        Pattern pattern = Pattern.compile(PW_PATTERN);
        boolean isPwValid = pattern.matcher(pw).matches();

        if (isPwValid) {
            String salt = OpenCrypt.encryptPw(user);
            int res = userMapper.insertUser(user);
            saltMapper.insertSecuInfo(user.getUserId(), salt);

            return res;
        } else {
            return -1;
        }
    }

    @Override
    public User getUser(int userId) {
        return userMapper.selectUser(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
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

    @Override
    public User loginUser(String email, String password) {
        User user = userMapper.selectUserByEmail(email);

        if (user == null) return null;

        String salt = saltMapper.selectSalt(user.getUserId());

        String hashedPassword = OpenCrypt.byteArrayToHex(OpenCrypt.getSHA256(password, salt));

        if (user.getPassword().equals(hashedPassword)) {
            user.setPassword("");
            return user;
        }

        return null;
    }

}
