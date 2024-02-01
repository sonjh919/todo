package com.sparta.todo.todo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.sparta.todo.common.ResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.CreateTodoResponseDto;
import com.sparta.todo.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    @PostMapping("v1/todos")
    @Operation(summary = "할일카드 작성")
    public ResponseEntity<ResponseDto> createTodo(@RequestHeader(value="Authorization") String accessToken, @RequestBody @Valid TodoRequestDto requestDto){
        log.info("할일카드 작성 API");

        CreateTodoResponseDto todoResponseDto = todoService.createTodo(accessToken, requestDto);
        URI createdUri = linkTo(
            methodOn(TodoController.class).createTodo(accessToken, requestDto)).slash(
            todoResponseDto.getTodoId()).toUri();

        return ResponseEntity.created(createdUri).body(new ResponseDto("할일카드 작성 성공", todoResponseDto));
    }

    @GetMapping("v1/todos/{id}")
    @Operation(summary = "할일카드 조회")
    public ResponseEntity<ResponseDto> getTodoById(@PathVariable Long id){
        return ResponseEntity.ok().body(new ResponseDto("할일카드 조회 성공", todoService.getTodoById(id)));
    }


}
