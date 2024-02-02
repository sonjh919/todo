package com.sparta.todo.todo.dto;

import com.sparta.todo.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponseDto {

    private Long todoId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;

    public TodoResponseDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getUser().getUserName();
        this.dateCreated = todo.getDateCreated();
        this.isCompleted = todo.isCompleted();
        this.isPrivate = todo.isPrivate();
    }
}
