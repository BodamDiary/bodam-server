package com.ssafy.server.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    @Value("${jwt.secretkey}")
    String secretKey;

    // JWT 발급 메서드
    public String generateJwt(int id, int expiration) {
        System.out.println("JWT 발급 시작");

        // JWT 생성 (아이디 포함)
        String jwtToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * expiration)) // 10분 후 만료
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println("JWTTOKEN: " + jwtToken);
        return jwtToken;
    }

    public String generateJwt(String email, int expiration) {
        System.out.println("JWT 발급 이메일로 시작");
        // JWT 생성 (이메일 포함)
        String jwtToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("email", email)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwtToken;
    }

    public boolean validToken(String token) {
        System.out.println("토큰 맞는지 확인시작함");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            System.out.println("토큰 유효성 검증 완료");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        System.out.println("getClaims 시작함");
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public int getIdFromToken(String token) {
        System.out.println("Id에서 토큰을 가져옴");
        Claims claims = getClaims(token);
        return claims.get("id", Integer.class); // "email" 키로 Claim 값을 가져옴
    }

    public String getEmailFromToken(String token) {
        System.out.println("이메일에서 토큰을 가져옴");
        Claims claims = getClaims(token);
        return claims.get("email", String.class); // "email" 키로 Claim 값을 가져옴
    }
}
