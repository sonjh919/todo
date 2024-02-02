package com.sparta.todo.comment.controller;

import com.sparta.todo.common.ExceptionDto;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionController {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> AccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.badRequest()
            .body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }

}
