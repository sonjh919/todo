package com.sparta.todo.todo.dto;

import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.user.entity.User;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTodoListResponseDto {

    private String author;
    private List<GetTodoResponseDto> todos;


    public GetTodoListResponseDto(User user) {
        this.author = user.getUserName();
        this.todos = user.getTodos().stream()
            .sorted(Comparator.comparing(Todo::getDateCreated).reversed())
            .map(GetTodoResponseDto::new)
            .toList();
    }

    public GetTodoListResponseDto(String author, List<GetTodoResponseDto> todos) {
        this.author = author;
        this.todos = todos.stream().toList();
    }

}
