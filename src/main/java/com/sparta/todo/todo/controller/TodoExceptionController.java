package com.sparta.todo.todo.controller;

import com.sparta.todo.common.ExceptionDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoExceptionController {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDto> JwtException(JwtException e) {
        return ResponseEntity.status(403)
            .body(new ExceptionDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
                e.getMessage()));
    }
}
