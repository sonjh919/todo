package com.sparta.todo.exception;

import com.sparta.todo.commonDto.ExceptionDto;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
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

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDto> JwtException(JwtException e) {
        return ResponseEntity.status(403)
            .body(new ExceptionDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
                e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> EntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.badRequest()
            .body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> AccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.badRequest()
            .body(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                e.getMessage()));
    }


}
