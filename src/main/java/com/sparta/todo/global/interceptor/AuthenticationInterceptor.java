package com.sparta.todo.global.interceptor;

import com.sparta.todo.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        if (isSwaggerRequest(request)) {
            return true;
        }

        String token = jwtUtil.getJwtFromHeader2(request);
        jwtUtil.validateToken(token);

        return true;
    }

    private boolean isSwaggerRequest(final HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("swagger")
            || uri.contains("api-docs")
            || uri.contains("webjars");
    }
}
