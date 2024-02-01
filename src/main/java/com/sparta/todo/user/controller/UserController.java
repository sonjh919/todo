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

    @PostMapping("/v1/user/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return ResponseEntity.ok().body(new ResponseDto("회원가입 성공", userService.signup(requestDto)));
    }

    @GetMapping("/v1/user/test")
    @Operation(summary = "테스트 api")
    public String test() {
        return "test succeed";
    }

    @PostMapping("/v1/user/login")
    @Operation(summary = "로그인")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        System.out.println(requestDto.getUserName());
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, userService.login(requestDto))
            .body(new ResponseDto("로그인 성공"));
    }

}