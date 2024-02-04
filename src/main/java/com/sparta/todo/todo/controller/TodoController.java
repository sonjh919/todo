package com.sparta.todo.todo.controller;

import static com.sparta.todo.message.TodoMessage.CREATE_TODO_API;
import static com.sparta.todo.message.TodoMessage.CREATE_TODO_SUCCESS;
import static com.sparta.todo.message.TodoMessage.DELETE_TODO_API;
import static com.sparta.todo.message.TodoMessage.GET_TODO_API;
import static com.sparta.todo.message.TodoMessage.GET_TODO_SUCCESS;
import static com.sparta.todo.message.TodoMessage.PATCH_TODO_API;
import static com.sparta.todo.message.TodoMessage.PATCH_TODO_DESCRIPTION;
import static com.sparta.todo.message.TodoMessage.PATCH_TODO_SUCCESS;
import static com.sparta.todo.message.TodoMessage.SEARCH_TODOS_API;
import static com.sparta.todo.message.TodoMessage.SEARCH_TODOS_DESCRIPTION;
import static com.sparta.todo.message.TodoMessage.SEARCH_TODOS_SUCCESS;

import com.sparta.todo.commonDto.ResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("v1/todos")
    @Operation(summary = CREATE_TODO_API)
    public ResponseEntity<ResponseDto> createTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @RequestBody @Valid TodoRequestDto requestDto) {
        log.info(CREATE_TODO_API);

        TodoResponseDto todoResponseDto = todoService.createTodo(accessToken, requestDto);

        return ResponseEntity.created(createUri(todoResponseDto.getTodoId()))
            .body(new ResponseDto(CREATE_TODO_SUCCESS, todoResponseDto));
    }

    @GetMapping("v1/todos/{id}")
    @Operation(summary = GET_TODO_API)
    public ResponseEntity<ResponseDto> getTodoById(@PathVariable Long id) {
        log.info(GET_TODO_API);

        return ResponseEntity.ok()
            .body(new ResponseDto(GET_TODO_SUCCESS, todoService.getTodoById(id)));
    }

    @GetMapping("v1/todos")
    @Operation(summary = SEARCH_TODOS_API, description = SEARCH_TODOS_DESCRIPTION)
    public ResponseEntity<ResponseDto> getTodos(@RequestParam(required = false) String title) {
        log.info(SEARCH_TODOS_API);

        return ResponseEntity.ok()
            .body(new ResponseDto(SEARCH_TODOS_SUCCESS, todoService.getTodos(title)));
    }

    @PatchMapping("v1/todos/{id}")
    @Operation(summary = PATCH_TODO_API, description = PATCH_TODO_DESCRIPTION)
    public ResponseEntity<ResponseDto> updateTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @RequestBody @Valid TodoRequestDto requestDto,
        @PathVariable Long id,
        @RequestParam(required = false) Boolean isCompleted,
        @RequestParam(required = false) Boolean isPrivate
    ) {
        log.info(PATCH_TODO_API);

        return ResponseEntity.created(updateUri())
            .body(new ResponseDto(PATCH_TODO_SUCCESS, todoService.updateTodo(accessToken, requestDto, id,
                isCompleted, isPrivate)));
    }

    @DeleteMapping("v1/todos/{id}")
    @Operation(summary = DELETE_TODO_API)
    public ResponseEntity<ResponseDto> deleteTodo(
        @RequestHeader(value = "Authorization") String accessToken,
        @PathVariable Long id
    ) {
        log.info(DELETE_TODO_API);

        todoService.deleteTodo(accessToken, id);

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
