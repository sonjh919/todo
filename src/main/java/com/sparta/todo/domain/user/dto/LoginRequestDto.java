package com.sparta.todo.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    private String userName;
    private String password;
}