package com.sparta.todo.domain.todo.dto;

import com.sparta.todo.domain.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTodoResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;

    public GetTodoResponseDto(Todo todo) {
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getUserEntity().getUserName();
        this.dateCreated = todo.getDateCreated();
        this.isCompleted = todo.isCompleted();
        this.isPrivate = todo.isPrivate();
    }

}
