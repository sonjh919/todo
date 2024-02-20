package com.sparta.todo.domain.user.dto;

import com.sparta.todo.domain.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    private final Long id;
    private final String userName;

    public SignupResponseDto(UserEntity userEntity) {
        this.id = userEntity.getUserId();
        this.userName = userEntity.getUserName();
    }
}
