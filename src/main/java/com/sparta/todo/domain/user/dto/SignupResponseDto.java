package com.sparta.todo.domain.user.dto;

import com.sparta.todo.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {

    private Long id;
    private String userName;

    public SignupResponseDto(User user) {
        this.id = user.getUserId();
        this.userName = user.getUserName();
    }
}
