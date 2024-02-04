package com.sparta.todo.user.controller;

import com.sparta.todo.common.ResponseDto;
import com.sparta.todo.user.dto.LoginRequestDto;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final String SIGN_UP_API = "회원 가입 API";
    private static final String SIGN_UP_SUCCESS = "회원 가입 성공";


    @PostMapping("/v1/user/signup")
    @Operation(summary = SIGN_UP_API)
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        log.info(SIGN_UP_API);
        return ResponseEntity.ok().body(new ResponseDto(SIGN_UP_SUCCESS, userService.signup(requestDto)));
    }

    @PostMapping("/v1/user/login")
    @Operation(summary = "로그인")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        log.info("로그인 API");
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, userService.login(requestDto))
            .body(new ResponseDto("로그인 성공"));
    }

}