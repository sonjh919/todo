package com.sparta.todo.domain.todo.model;

import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.user.entity.UserEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Todo {

    private Long todoId;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private boolean isCompleted;
    private boolean isPrivate;
    private UserEntity userEntity;

    public static Todo from(final TodoEntity todoEntity) {
        return new Todo(
            todoEntity.getTodoId(),
            todoEntity.getTitle(),
            todoEntity.getContent(),
            todoEntity.getDateCreated(),
            todoEntity.isCompleted(),
            todoEntity.isCompleted(),
            todoEntity.getUserEntity()
        );
    }

    public TodoEntity toEntity() {
        return new TodoEntity(
            todoId,
            title,
            content,
            dateCreated,
            isCompleted,
            isPrivate,
            userEntity
        );
    }

    public void update(Boolean isCompleted, Boolean isPrivate) {
        if (isCompleted != null) {
            this.isCompleted = isCompleted;
        }
        if (isPrivate != null) {
            this.isPrivate = isPrivate;
        }
    }

    public GetTodoResponseDto ResponseDto() {
        return new GetTodoResponseDto(
            todoId,
            title,
            content,
            userEntity.getUserName(),
            dateCreated,
            isCompleted,
            isPrivate
        );
    }

    public TodoResponseDto responseDto() {
        return new TodoResponseDto(
            todoId,
            title,
            content,
            userEntity.getUserName(),
            dateCreated,
            isCompleted,
            isPrivate
        );
    }
}
