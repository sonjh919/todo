package com.sparta.todo.global.config;

import com.sparta.todo.global.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // LogInterceptor 등록
            .order(1)    // 적용할 필터 순서 설정
            .addPathPatterns("/**")
            .excludePathPatterns("/error"); // 인터셉터에서 제외할 패턴
    }
}
