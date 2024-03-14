package com.sparta.todo.domain.todo.query.dto;

import com.sparta.todo.domain.todo.model.TodoEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTodoResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;

    public GetTodoResponseDto(TodoEntity todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getUserEntity().getUserName();
        this.dateCreated = todo.getDateCreated();
        this.isCompleted = todo.isCompleted();
        this.isPrivate = todo.isPrivate();
    }

}
