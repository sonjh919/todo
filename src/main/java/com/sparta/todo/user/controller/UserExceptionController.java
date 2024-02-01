package com.sparta.todo.user.controller;

import com.sparta.todo.common.ExceptionDto;
import com.sparta.todo.user.exception.LoginException;
import com.sparta.todo.user.exception.SignupException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<ExceptionDto> SignupException(SignupException e) {
        return ResponseEntity.status(409)
            .body(new ExceptionDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT,
                e.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionDto> LoginException(LoginException e){
        return ResponseEntity.badRequest().body(
            new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }

}
