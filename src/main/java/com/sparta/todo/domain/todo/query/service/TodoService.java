package com.sparta.todo.domain.todo.query.service;

import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import java.util.List;

public interface TodoService {

    public GetTodoResponseDto getTodoById(Long id);

    public List<GetTodoResponseDto> getTodos(String title);

}