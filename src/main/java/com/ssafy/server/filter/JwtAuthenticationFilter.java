package com.ssafy.server.filter;

import com.ssafy.server.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
            "/kakao-login",
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        log.info("filter를 거치지 않는 API입니다.");
        String path = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(url -> path.startsWith(url));
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        log.info("do filter에 들어옴");
        try {
            log.info("try에 들어옴");
            HttpSession session = request.getSession(false);
            log.info("request.getSession 지나감");
            log.info("session :::: " + session);
            log.info("session.id ::" + session.getId());
            if (session == null) {
                log.info("세션이 존재하지 않습니다.");
                throw new UnauthorizedException("세션이 존재하지 않습니다.");
            }

            String uToken = (String) session.getAttribute("uToken");
            if (uToken == null || !jwtTokenProvider.validToken(uToken)) {
                log.info("유효하지 않은 토큰입니다.");
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
            log.info("사용자를 찾을 수 없는 오류");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            log.info("서버오류 발생");
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