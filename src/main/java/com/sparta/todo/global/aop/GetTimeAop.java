package com.sparta.todo.global.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
@RequiredArgsConstructor
public class GetTimeAop {

    @Pointcut("execution(* com.sparta.todo.domain.*.query.controller.*.get*(..))")
    private void getMethods() {}

    @Around("getMethods()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;

            log.info("[API Use Time] Total Time: " + runTime + " ms");
        }
    }
}