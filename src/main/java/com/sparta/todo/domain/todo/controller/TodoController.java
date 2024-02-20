package com.sparta.todo.domain.todo.controller;

import static com.sparta.todo.global.message.TodoMessage.CREATE_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.CREATE_TODO_SUCCESS;
import static com.sparta.todo.global.message.TodoMessage.DELETE_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.GET_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.GET_TODO_SUCCESS;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_API;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_DESCRIPTION;
import static com.sparta.todo.global.message.TodoMessage.PATCH_TODO_SUCCESS;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_API;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_DESCRIPTION;
import static com.sparta.todo.global.message.TodoMessage.SEARCH_TODOS_SUCCESS;

import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.service.TodoService;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.global.commonDto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        @RequestAttribute("User") UserEntity userEntity,
        @RequestBody @Valid TodoRequestDto requestDto) {

        TodoResponseDto todoResponseDto = todoService.createTodo(userEntity, requestDto);

        return ResponseEntity.created(createUri(todoResponseDto.getTodoId()))
            .body(ResponseDto.<TodoResponseDto>builder()
                .message(CREATE_TODO_SUCCESS)
                .data(todoResponseDto)
                .build());
    }

    @GetMapping("v2/todos/{id}")
    @Operation(summary = GET_TODO_API)
    public ResponseEntity<ResponseDto<GetTodoResponseDto>> getTodoById(@PathVariable Long id) {

        return ResponseEntity.ok()
            .body(ResponseDto.<GetTodoResponseDto>builder()
                .message(GET_TODO_SUCCESS)
                .data(todoService.getTodoById(id))
                .build());
    }

    @GetMapping("v2/todos")
    @Operation(summary = SEARCH_TODOS_API, description = SEARCH_TODOS_DESCRIPTION)
    public ResponseEntity<ResponseDto<List<GetTodoListResponseDto>>> getTodos(@RequestParam(required = false) String title) {

        return ResponseEntity.ok()
            .body(ResponseDto.<List<GetTodoListResponseDto>>builder()
                .message(SEARCH_TODOS_SUCCESS)
                .data(todoService.getTodos(title))
                .build());
    }

    @PatchMapping("v1/todos/{id}")
    @Operation(summary = PATCH_TODO_API, description = PATCH_TODO_DESCRIPTION)
    public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
        @RequestAttribute("User") UserEntity userEntity,
        @RequestBody @Valid TodoRequestDto requestDto,
        @PathVariable Long id,
        @RequestParam(required = false) Boolean isCompleted,
        @RequestParam(required = false) Boolean isPrivate
    ) {

        TodoResponseDto todoResponseDto = todoService.updateTodo(userEntity, requestDto, id,
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
        @RequestAttribute("User") UserEntity userEntity,
        @PathVariable Long id
    ) {

        todoService.deleteTodo(userEntity, id);

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