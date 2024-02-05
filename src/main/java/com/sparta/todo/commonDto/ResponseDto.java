package com.sparta.todo.commonDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<T> {

    private String message;
    private T data;

}