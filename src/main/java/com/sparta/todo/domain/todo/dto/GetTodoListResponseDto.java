package com.sparta.todo.domain.todo.dto;

import com.sparta.todo.domain.todo.entity.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTodoListResponseDto {

    private String author;
    private List<GetTodoResponseDto> todos;


    public GetTodoListResponseDto(UserEntity userEntity) {
        this.author = userEntity.getUserName();
        this.todos = userEntity.getTodos().stream()
            .sorted(Comparator.comparing(Todo::getDateCreated).reversed())
            .map(GetTodoResponseDto::new)
            .toList();
    }

    public GetTodoListResponseDto(String author, List<GetTodoResponseDto> todos) {
        this.author = author;
        this.todos = todos.stream().toList();
    }

}
