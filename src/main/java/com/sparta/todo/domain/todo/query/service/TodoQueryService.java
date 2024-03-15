package com.sparta.todo.domain.todo.query.service;

import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TodoQueryService {

    public GetTodoResponseDto getTodoById(Long id);

    public List<GetTodoResponseDto> getTodos(String title, Pageable pageable);

}