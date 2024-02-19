package com.sparta.todo.global.interceptor;

import com.sparta.todo.domain.user.entity.User;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.global.validation.Validation;
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
    private final Validation validation;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {

        String tokenValue = jwtUtil.getJwtFromRequest(request);
        String userInfo = jwtUtil.getUserInfoFromToken(tokenValue);

        request.setAttribute("User", validation.userBy(userInfo));

        return true;
    }

}
