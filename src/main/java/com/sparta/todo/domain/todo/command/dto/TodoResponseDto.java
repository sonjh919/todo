package com.sparta.todo.domain.todo.command.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long todoId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;

}
