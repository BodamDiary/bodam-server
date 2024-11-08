package com.ssafy.server.model.service;

import com.ssafy.server.model.dao.UserMapper;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginTest {

    @Autowired
    UserService service;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("service빈 등록 확인")
    public void serviceBean() {
        Assertions.assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("DB1 작동 검사")
    void test(){
        // given
        String email = "string";

        // when
        User user = userMapper.selectUserByEmail(email);

        // then
        Assertions.assertThat(user.getUserName()).isEqualTo("string");
    }

    @Test
    @DisplayName("이메일 유효성 검사 실패")
    public void emailCheckFail() {
        String email1 = "syoon@";
        String email2 = "@gmail.com";
        String email3 = "syoon@gmail";

    }

    @Test
    @DisplayName("이메일 유효성 검사 성공")
    public void emailCheckSuccess() {
        String email = "syoon@gmail.com";
    }

    @Test
    @DisplayName("패스워드 유효성 검사 실패")
    public void passwordCheckFail() {

    }

    @Test
    @DisplayName("패스워드 유효성 검사 성공")
    public void passwordCheckSuccess() {

    }

    @Test
    @DisplayName("로그인 실패")
    public void loginFail() {

    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess() {

    }
}
