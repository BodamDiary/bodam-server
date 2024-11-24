package com.ssafy.server.config;

import com.ssafy.server.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * Production 환경에서만 적용되는 보안 설정을 포함
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Security 필터 체인 구성
     * - CORS 설정
     * - CSRF 비활성화
     * - JWT 인증 필터 추가
     * - HTTPS 리다이렉션
     * - Frame Options 설정
     * - 로그아웃 비활성화
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 활성화
                .cors(cors -> cors.configure(http))

                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // JWT 인증 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 요청 URL별 인증 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/content/**",
                                "/users/login-user",
                                "/users/regist-user",
                                "/kakao",
                                "/regist-kakao",
                                "/kakao-login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**").permitAll()  // 인증 없이 접근 가능한 경로
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )

                // 세션 관리 설정 추가
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // HTTPS 설정 수정
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure())  // 모든 요청에 대해 HTTPS 요구

                // Frame Options 설정
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // 로그아웃 비활성화
                .logout(logout -> logout.disable());

        return http.build();
    }
}
