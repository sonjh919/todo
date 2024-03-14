package com.sparta.todo.domain.todo.command.controller;

import static com.sparta.todo.global.message.TodoMessage.CREATE_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.CREATE_TODO_SUCCESS;
import static com.sparta.todo.global.message.TodoMessage.DELETE_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_DESCRIPTION;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_SUCCESS;

import com.sparta.todo.domain.todo.command.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.command.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.command.TodoService;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.global.commonDto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("v1/todos")
    @Operation(summary = CREATE_TODO_API)
    public ResponseEntity<ResponseDto<TodoResponseDto>> createTodo(
        @RequestAttribute("User") User user,
        @RequestBody @Valid TodoRequestDto requestDto) {

        TodoResponseDto todoResponseDto = todoService.createTodo(user, requestDto);

        return ResponseEntity.created(createUri(todoResponseDto.getTodoId()))
            .body(ResponseDto.<TodoResponseDto>builder()
                .message(CREATE_TODO_SUCCESS)
                .data(todoResponseDto)
                .build());
    }

    @PatchMapping("v1/todos/{id}")
    @Operation(summary = PATCH_TODO_API, description = PATCH_TODO_DESCRIPTION)
    public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
        @RequestAttribute("User") User user,
        @RequestBody @Valid TodoRequestDto requestDto,
        @PathVariable Long id,
        @RequestParam(required = false) Boolean isCompleted,
        @RequestParam(required = false) Boolean isPrivate
    ) {

        TodoResponseDto todoResponseDto = todoService.updateTodo(user, requestDto, id,
            isCompleted, isPrivate);

        return ResponseEntity.created(updateUri())
            .body(ResponseDto.<TodoResponseDto>builder()
                .message(PATCH_TODO_SUCCESS)
                .data(todoResponseDto)
                .build());
    }

    @DeleteMapping("v1/todos/{id}")
    @Operation(summary = DELETE_TODO_API)
    public ResponseEntity<Void> deleteTodo(
        @RequestAttribute("User") User user,
        @PathVariable Long id
    ) {

        todoService.deleteTodo(user, id);

        return ResponseEntity.noContent().build();
    }


    private URI createUri(Long todoId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(todoId)
            .toUri();
    }

    private URI updateUri() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .replaceQueryParam("isCompleted")
            .replaceQueryParam("isPrivate")
            .build()
            .toUri();
    }

}
