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
    public String generateJwt(int id) {

        // JWT 생성 (아이디 포함)
        String jwtToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10분 후 만료
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwtToken;
    }

    public String generateJwt(String email) {

        // JWT 생성 (이메일 포함)
        String jwtToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("email", email)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분 후 만료
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return jwtToken;
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public int getIdFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Integer.class); // "email" 키로 Claim 값을 가져옴
    }

    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class); // "email" 키로 Claim 값을 가져옴
    }
}
