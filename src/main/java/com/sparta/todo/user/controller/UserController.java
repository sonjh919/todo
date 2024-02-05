package com.sparta.todo.user.controller;

import static com.sparta.todo.message.UserMessage.LOGIN_API;
import static com.sparta.todo.message.UserMessage.LOGIN_SUCCESS;
import static com.sparta.todo.message.UserMessage.SIGN_UP_API;
import static com.sparta.todo.message.UserMessage.SIGN_UP_SUCCESS;

import com.sparta.todo.commonDto.ResponseDto;
import com.sparta.todo.user.dto.LoginRequestDto;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.dto.SignupResponseDto;
import com.sparta.todo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/user/signup")
    @Operation(summary = SIGN_UP_API)
    public ResponseEntity<ResponseDto<SignupResponseDto>> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        log.info(SIGN_UP_API);

        return ResponseEntity.ok()
            .body(ResponseDto.<SignupResponseDto>builder()
                .message(SIGN_UP_SUCCESS)
                .data(userService.signup(requestDto))
                .build());
    }

    @PostMapping("/v1/user/login")
    @Operation(summary = LOGIN_API)
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody LoginRequestDto requestDto) {
        log.info(LOGIN_API);

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, userService.login(requestDto))
            .body(ResponseDto.<Void>builder()
                .message(LOGIN_SUCCESS)
                .build());
    }

}