package com.sparta.todo.todo.dto;

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

}
