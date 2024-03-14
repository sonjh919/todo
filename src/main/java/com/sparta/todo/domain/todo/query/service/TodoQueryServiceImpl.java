package com.sparta.todo.domain.todo.query.service;

import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.model.TodoEntity;
import com.sparta.todo.domain.todo.query.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.query.repository.TodoQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoQueryServiceImpl implements TodoService {
    private final TodoQueryRepository todoQueryRepository;

    @Override
    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = todoQueryRepository.findTodoBy(id);
        return todo.ResponseDto();
    }

    @Override
    public List<GetTodoResponseDto> getTodos(String title) {
        List<TodoEntity> todoEntities = todoQueryRepository.findByTitle(title);
        return todoEntities.stream().map(GetTodoResponseDto::new).toList();
    }
}
