package com.sparta.todo.domain.todo.query.service;

import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.model.TodoEntity;
import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.query.repository.TodoQueryRepository;
import com.sparta.todo.domain.todo.query.service.TodoQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoQueryServiceImpl implements TodoQueryService {
    private final TodoQueryRepository todoQueryRepository;

    @Override
    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = todoQueryRepository.findTodoBy(id);
        return todo.ResponseDto();
    }

    @Override
    public List<GetTodoResponseDto> getTodos(String title, Pageable pageable) {
        Page<TodoEntity> todoEntities = todoQueryRepository.findByTitle(title, pageable);
        return todoEntities.stream().map(GetTodoResponseDto::new).toList();
    }
}
