package com.sparta.todo.todo.service;


import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TodoResponseDto createTodo(String accessToken, TodoRequestDto requestDto){
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        Todo todo = new Todo(requestDto, author);

        return new TodoResponseDto(todoRepository.save(todo));
    };
}
