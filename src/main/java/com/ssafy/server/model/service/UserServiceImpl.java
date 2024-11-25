package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.daosc.SaltMapper;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.util.OpenCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SaltMapper saltMapper;

    @Autowired
    private S3Uploader s3Uploader;

    @Autowired
    private S3Validator s3Validator;

    private static final String PW_PATTERN =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";



    @Override
    public int registUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getUserName() == null || user.getUserName().isEmpty() || user.getNickname() == null || user.getNickname().isEmpty()) {
            System.out.println("user getEmail" + user.getEmail());
            System.out.println("user getPassword" + user.getPassword());
            System.out.println("user getUserName" + user.getUserName());
            System.out.println("user getNickname" + user.getNickname());
            return -1;
        }

        System.out.println("1번 위치에서 regist User fail");
        String pw = user.getPassword();

        Pattern pattern = Pattern.compile(PW_PATTERN);
        boolean isPwValid = pattern.matcher(pw).matches();
        System.out.println("pw가 유효한가::: "+ isPwValid);
        if (isPwValid) {
            System.out.println("유효했다고 판단");
            String salt = OpenCrypt.encryptPw(user);
            int res = userMapper.insertUser(user);
            saltMapper.insertSecuInfo(user.getUserId(), salt);

            return res;
        } else {
            System.out.println("유효하지 않다고 판단");
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

    @Override
    public String uploadProfileImage(MultipartFile file, User user) {
        try {
            s3Validator.validateImageFile(file);

            // 기존 S3 이미지가 있다면 삭제
            String currentProfileImage = user.getProfileImage();
            if (currentProfileImage != null && currentProfileImage.contains("amazonaws.com")) {
                String oldImageKey = s3Validator.extractKeyFromUrl(currentProfileImage);
                s3Uploader.deleteFile(oldImageKey);
            }
            System.out.println("업로드 프로필 이미지 실행 완료");
            // 새 이미지 업로드하고 URL 받기
            String imageUrl = s3Uploader.uploadFile(file, "profile");

            // 사용자 정보 업데이트
            user.setProfileImage(imageUrl);
            int result = userMapper.updateUser(user);

            System.out.println("result::::" + result);
            if (result > 0) {
                System.out.println("result는 0이상");
//                user.setProfileImage(imageUrl);
//                int updateProfileCnt = userMapper.updateProfileImage(user);
//                System.out.println(updateProfileCnt);
                return imageUrl;  // S3 URL 반환
            } else {
                throw new RuntimeException("프로필 이미지 업데이트에 실패했습니다.");
            }

        } catch (Exception e) {
            throw new RuntimeException("파일 업로드에 실패했습니다.", e);
        }
    }

    @Override
    public String deleteProfileImage(User user) {
        try {
            String currentProfileImage = user.getProfileImage();
            if (currentProfileImage != null && currentProfileImage.contains("amazonaws.com")) {
                String imageKey = s3Validator.extractKeyFromUrl(currentProfileImage);
                s3Uploader.deleteFile(imageKey);
            }

            // 프로필 이미지 필드를 null로 설정
            user.setProfileImage(null);
            int result = userMapper.updateUser(user);

            if (result > 0) {
                return "프로필 이미지가 성공적으로 삭제되었습니다.";
            } else {
                throw new RuntimeException("프로필 이미지 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 삭제에 실패했습니다.", e);
        }
    }

}
