package com.sparta.todo.todo.dto;

import com.sparta.todo.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoResponseDto {

    private Long todoId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;

    public CreateTodoResponseDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getAuthor();
        this.dateCreated = todo.getDateCreated();
        this.isCompleted = todo.isCompleted();
        this.isPrivate = todo.isPrivate();
    }
}
