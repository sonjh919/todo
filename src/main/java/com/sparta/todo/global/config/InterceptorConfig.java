package com.sparta.todo.global.config;

import com.sparta.todo.global.interceptor.AuthenticationInterceptor;
import com.sparta.todo.global.interceptor.LogInterceptor;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.global.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final Validation validation;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
            .order(1)    // 적용할 필터 순서 설정
            .addPathPatterns("/**")
            .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new AuthenticationInterceptor(jwtUtil, validation))
            .order(2)    // 적용할 필터 순서 설정
            .addPathPatterns("/**")
            .excludePathPatterns("/v1/users/**", "/swagger-ui/**", "/v3/api-docs/**"); // 인터셉터에서 제외할 패턴

    }
}
