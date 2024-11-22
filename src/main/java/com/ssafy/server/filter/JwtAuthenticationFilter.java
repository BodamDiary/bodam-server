package com.ssafy.server.filter;

import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    // 필터를 적용하지 않을 URL 패턴들
    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/content",
            "/users/login-user",
            "/users/regist-user",
            "/kakao",
            "/regist-kakao",
            "kakao-login"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(url -> path.startsWith(url));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new UnauthorizedException("세션이 존재하지 않습니다.");
            }

            String uToken = (String) session.getAttribute("uToken");
            if (uToken == null || !jwtTokenProvider.validToken(uToken)) {
                throw new UnauthorizedException("유효하지 않은 토큰입니다.");
            }

            int userId = jwtTokenProvider.getIdFromToken(uToken);
            UserDetails userDetails = User.builder()
                    .username(String.valueOf(userId))
                    .password("")
                    .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                    .build();

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("서버 오류가 발생했습니다.");
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}