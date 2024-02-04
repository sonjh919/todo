package com.sparta.todo.user.controller;

import com.sparta.todo.common.ExceptionDto;
import java.util.NoSuchElementException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> ValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
            new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> DuplicateKeyException(DuplicateKeyException e) {
        return ResponseEntity.status(409)
            .body(new ExceptionDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT,
                e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionDto> NoSuchElementException(NoSuchElementException e){
        return ResponseEntity.badRequest().body(
            new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> BadCredentialsException(BadCredentialsException e){
        return ResponseEntity.badRequest().body(
            new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }

}
