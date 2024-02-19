package com.sparta.todo.global.commonDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionDto {

    private int statusCode;
    private HttpStatus state;
    private String message;

}

