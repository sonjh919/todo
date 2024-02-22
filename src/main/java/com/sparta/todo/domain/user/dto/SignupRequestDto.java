package com.sparta.todo.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "아이디는 필수로 입력해야 합니다.")
    @Size(min = 4, max = 10, message = "아이디는 4자 이상 10자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 알파벳 소문자와 숫자로만 구성되야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "비밀번호는 알파벳 대,소문자와 숫자로만 구성되야 합니다.")
    private String password;
}