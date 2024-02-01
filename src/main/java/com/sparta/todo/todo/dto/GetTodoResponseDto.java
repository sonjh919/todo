package com.sparta.todo.todo.dto;

import com.sparta.todo.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTodoResponseDto {
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;

    public GetTodoResponseDto(Todo todo){
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.author = todo.getUser().getUserName();
        this.dateCreated = todo.getDateCreated();
    }

}
