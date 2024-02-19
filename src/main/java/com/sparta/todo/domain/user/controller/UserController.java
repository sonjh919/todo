package com.sparta.todo.domain.user.controller;

import static com.sparta.todo.global.message.UserMessage.LOGIN_API;
import static com.sparta.todo.global.message.UserMessage.LOGIN_SUCCESS;
import static com.sparta.todo.global.message.UserMessage.SIGN_UP_API;
import static com.sparta.todo.global.message.UserMessage.SIGN_UP_SUCCESS;

import com.sparta.todo.global.commonDto.ResponseDto;
import com.sparta.todo.domain.user.dto.SignupRequestDto;
import com.sparta.todo.domain.user.service.UserService;
import com.sparta.todo.domain.user.dto.LoginRequestDto;
import com.sparta.todo.domain.user.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/users/signup")
    @Operation(summary = SIGN_UP_API)
    public ResponseEntity<ResponseDto<SignupResponseDto>> signup(@RequestBody @Valid SignupRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<SignupResponseDto>builder()
                .message(SIGN_UP_SUCCESS)
                .data(userService.signup(requestDto))
                .build());
    }

    @PostMapping("/v1/users/login")
    @Operation(summary = LOGIN_API)
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody LoginRequestDto requestDto) {

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, userService.login(requestDto))
            .body(ResponseDto.<Void>builder()
                .message(LOGIN_SUCCESS)
                .build());
    }

}