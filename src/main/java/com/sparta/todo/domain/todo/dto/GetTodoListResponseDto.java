package com.sparta.todo.domain.todo.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class GetTodoListResponseDto {

    private String author;
    private List<GetTodoResponseDto> todos;

    public GetTodoListResponseDto(String author, List<GetTodoResponseDto> todos) {
        this.author = author;
        this.todos = todos;
    }

}
