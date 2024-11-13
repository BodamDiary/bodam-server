package com.ssafy.server.model.service;

import com.ssafy.server.model.dto.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@naver.com")
                .kakaoId("kakao_test123")
                .nickname("kim")
                .userName("김테스트")
                .password("test1234!")
                .profileImage("profile.jpg")
                .address("seoul")
                .phoneNumber("010-1234-5678")
                .agreeCondition(true)  // 약관 동의
                .isOauth(true)         // 일반 회원가입
                .build();
    }

    @DisplayName("일반 회원가입 성공")
    @Test
    void normalSignupTest(){
        // given

        // when

        // then
    }

    @DisplayName("카카오 회원가입 성공")
    @Test
    void kakaoSignupTest(){
        // given

        // when

        // then
    }

    @DisplayName("이메일 중복 체크")
    @Test
    void duplicateEmailTest(){
        // given

        // when

        // then
    }


    @DisplayName("카카오 ID 중복 체크")
    @Test
    void duplicateKakaoIdTest(){
        // given

        // when

        // then
    }

    @DisplayName("필수 입력값 검증")
    @Test
    void requiredFieldsTest(){
        // given

        // when

        // then
    }

    @DisplayName("약관 동의 검증")
    @Test
    void agreeConditionTest(){
        // given

        // when

        // then
    }

    @DisplayName("이메일 형식 검증")
    @Test
    void emailFormatTest(){
        // given

        // when

        // then
    }

    @DisplayName("비밀번호 복잡도 검증 영문자 없어서 실패")
    @Test
    void passwordComplexityTestNoAlphabet(){
        // given
        user.setPassword("1234123@@!!!");
        // when
        int res = userService.registUser(user);
        // then
        Assertions.assertThat(res).isEqualTo(-1);
    }

    @Test
    @DisplayName("비밀번호 복잡도 검증 숫자 없어서 실패")
    void passwordComplexityTestNoNumber(){
        // given
        user.setPassword("syoon!!@@");
        // when
        int res = userService.registUser(user);
        // then
        Assertions.assertThat(res).isEqualTo(-1);
    }

    @Test
    @DisplayName("비밀번호 복잡도 검증 특수문자 없어서 실패")
    void passwordComplexityTestNoSpecialCharacter(){
        // given
        user.setPassword("syoon4486");
        // when
        int res = userService.registUser(user);
        // then
        Assertions.assertThat(res).isEqualTo(-1);
    }

    @Test
    @DisplayName("비밀번호 복잡도 검증 성공")
    void passwordComplexitySuccess(){
        // given
        user.setPassword("syoon4486!");
        // when
        int res = userService.registUser(user);
        // then
        Assertions.assertThat(res).isEqualTo(0);
    }
}