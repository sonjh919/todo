package com.sparta.todo.global.exception;

import com.sparta.todo.global.commonDto.ExceptionDto;
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
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return createResponse(HttpStatus.BAD_REQUEST,
            e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler({
        NoSuchElementException.class,
        BadCredentialsException.class,
        EntityNotFoundException.class,
        JwtException.class,
        AccessDeniedException.class
    })
    public ResponseEntity<ExceptionDto> handleBadRequestException(Exception e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException e) {
        return createResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDto> handleJwtException(JwtException e) {
        return createResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status.value())
            .body(ExceptionDto.builder()
                .statusCode(status.value())
                .state(status)
                .message(message)
                .build());
    }

}
