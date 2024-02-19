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

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException e) {
        return createResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionDto> handleNoSuchElementException(NoSuchElementException e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(BadCredentialsException e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionDto> handleJwtException(JwtException e) {
        return createResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException e) {
        return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
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
