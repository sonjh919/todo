package com.sparta.todo.todo.dto;

import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.user.entity.User;
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
        System.out.println("user.getTodos() = " + user.getTodos());
        this.todos = user.getTodos().stream()
            .map(GetTodoResponseDto::new)
            .toList();
    }
}
