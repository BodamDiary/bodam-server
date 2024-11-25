package com.ssafy.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.server.model.dto.KakaoInfo;
import com.ssafy.server.model.dto.User;
import com.ssafy.server.model.service.OAuthService;
import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping(value="/kakao")
    public String kakaoConnect() {
        log.info("카카오 로그인 시작 - 인가 코드 요청");
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+clientId);
        url.append("&redirect_uri="+redirectUri);
        url.append("&response_type=code");
        url.append("&prompt=select_account");
        log.info("카카오 인증 URL 생성: {}", url.toString());
        return "redirect:" + url.toString();
    }

    @GetMapping("/kakao-login")
    public String kakaoCallback(String code, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);

        log.info("카카오 콜백 호출 - 인가 코드: {}", code);

        String accessToken = null;
        try {
            accessToken = oAuthService.getAccessToken(code);
            log.info("액세스 토큰 발급 성공: {}", accessToken);
        } catch (JsonProcessingException e) {
            log.error("액세스 토큰 발급 실패", e);
            throw new RuntimeException(e);
        }

        KakaoInfo kakaoInfo = null;
        try {
            kakaoInfo = oAuthService.getKakaoInfo(accessToken);
            log.info("카카오 사용자 정보 조회 성공 - 이메일: {}", kakaoInfo.getEmail());
        } catch (JsonProcessingException e) {
            log.error("카카오 사용자 정보 조회 실패", e);
            throw new RuntimeException(e);
        }

        User kakaoMember = oAuthService.ifNeedKakaoInfo(kakaoInfo);
        String email = kakaoInfo.getEmail();

        String prodUrl = "http://app.bodam.site/";

        if (kakaoMember == null) {
            String emailToken = jwtTokenProvider.generateJwt(email, 5);
            session.setAttribute("emailToken", emailToken);
            log.info("신규 사용자 확인 - 세션 ID: {}", session.getId());

            // 쿠키 설정
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true); // HTTPS 필수
            sessionCookie.setPath("/");
            sessionCookie.setDomain("bodam.site"); // 도메인 설정
            sessionCookie.setMaxAge(1800); // 30분
            response.addCookie(sessionCookie);

            return "redirect:"+prodUrl+"kakao-signup";
        }

        log.info("기존 사용자 확인 - 로그인 성공");

        String uToken = jwtTokenProvider.generateJwt(kakaoMember.getUserId(), 30);
        session.setAttribute("uToken", uToken);

        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true); // HTTPS 필수
        sessionCookie.setPath("/");
        sessionCookie.setDomain("bodam.site"); // 도메인 설정
        sessionCookie.setMaxAge(1800); // 30분
        response.addCookie(sessionCookie);

        return "redirect:"+prodUrl+"dashboard";
    }

    @PostMapping("/regist-kakao")
    public ResponseEntity<String> kakaoRegistUser(@RequestBody User user, HttpServletRequest request){
        log.info("카카오 회원가입 요청 시작");

        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.badRequest().body("no session");
        }
        String emailToken = (String)session.getAttribute("emailToken");

        if (emailToken == null) {
            return ResponseEntity.badRequest().body("no token");
        }

        boolean isValid = jwtTokenProvider.validToken(emailToken);
        log.info("토큰 유효성 검증 결과: {}", isValid);

        if (isValid) {
            String email = jwtTokenProvider.getEmailFromToken(emailToken);
            log.info("토큰에서 추출한 email: {}", email);

            if (email != null) {
                user.setEmail(email);
                int successUser = oAuthService.registUser(user);
                if (successUser > 0) {
                    log.info("회원가입 성공 - 사용자 email: {}", email);
                    return ResponseEntity.ok("Regist user successfully");
                }
                log.error("회원가입 실패 - 사용자 정보 저장 실패");
                return ResponseEntity.badRequest().body("Regist user failed");
            }
        }
        log.error("회원가입 실패 - 토큰 만료 또는 유효하지 않음");
        return ResponseEntity.badRequest().body("token expired");
    }
}