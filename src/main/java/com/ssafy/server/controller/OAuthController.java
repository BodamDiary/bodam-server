package com.ssafy.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.server.model.dto.KakaoInfo;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.OAuthService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    @Autowired
    OAuthService oAuthService;

    @Value("${kakao.client.id}")
    String clientId;
    @Value("${kakao.redirect.uri}")
    String redirectUri;


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("${server.address}")
    private String serverAddress;


    /**
     * 카카오 로그인 요청
     * @return
     */
    @GetMapping(value="/kakao")
    public String kakaoConnect() {
        System.out.println("kakao");
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+clientId);
        url.append("&redirect_uri="+redirectUri);
        url.append("&response_type=code");
        url.append("&prompt=select_account");
        return "redirect:" + url.toString();
    }

    /**
     * 카카오 로그인
     * @return
     */
    @GetMapping("/kakao-login")
    public String kakaoCallback(String code, HttpServletResponse response) {

        // SETP1 : 인가코드 받기
        // (카카오 인증 서버는 서비스 서버의 Redirect URI로 인가 코드를 전달합니다.)
        // System.out.println(code);

        // STEP2: 인가코드를 기반으로 토큰(Access Token) 발급
        String accessToken = null;
        try {
            accessToken = oAuthService.getAccessToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("엑세스 토큰  "+accessToken);

        // STEP3: 토큰를 통해 사용자 정보 조회
        KakaoInfo kakaoInfo = null;
        try {
            kakaoInfo = oAuthService.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("이메일 확인 "+kakaoInfo.getEmail());

        // STEP4: 카카오 사용자 정보 확인
        User kakaoMember = oAuthService.ifNeedKakaoInfo(kakaoInfo);
        String email = kakaoInfo.getEmail();

//        String token = jwtTokenProvider.generateJwt(email);
//        Cookie cookie = new Cookie("token", token);
//        Cookie cookie2 = new Cookie("email", email);

        ResponseCookie cookie3 = ResponseCookie.from("email", email)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true) // sameSite를 None으로 지정했다면 필수
                .build();

        response.addHeader("Set-Cookie", cookie3.toString());
//
//        response.addCookie(cookie);
//        response.addCookie(cookie2);
        // STEP5: 강제 로그인
        // 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if (kakaoMember == null) {
            System.out.println("redirect to kakao register");

            return "redirect:http://" + ("localhost".equals(serverAddress) ? "localhost:8080" : serverAddress)  +"/kakao-signup?email=" + kakaoInfo.getEmail();

        }

        return "redirect:http://localhost:3000";
    }



    /**
     * 카카오 로그아웃
     * @return
     */
    @GetMapping("/kakao-logout")
    public String kakaoLogout(HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoToken");

        if(accessToken != null && !"".equals(accessToken)){
            try {
                oAuthService.kakaoDisconnect(accessToken);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("accessToken is null");
        }

        System.out.println("카카오 로그아웃");

        return "redirect:/";
    }
}