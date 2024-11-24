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


                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure())

                // 세션 관리 설정 추가
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                // HTTPS 설정
//                .requiresChannel(channel -> channel
//                        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
//                        .requiresSecure()
//                )

                // Frame Options 설정
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000))
                )

                // 로그아웃 비활성화
                .logout(logout -> logout.disable());

        return http.build();
    }
}
