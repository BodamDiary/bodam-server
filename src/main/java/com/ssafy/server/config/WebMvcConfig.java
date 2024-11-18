package com.ssafy.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                .allowedOrigins("https://bodam-client.vercel.app", "http://localhost:3000") // 허용할 Origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials") // 클라이언트에서 노출할 헤더
                .allowCredentials(true) // 쿠키, 인증 정보 허용
                .maxAge(3600); // Preflight 요청 결과 캐싱 시간 (초 단위)
    }
}