package com.sparta.todo.domain.todo.command;

import com.sparta.todo.domain.todo.command.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.command.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.model.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.command.repository.TodoRepository;
import com.sparta.todo.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(User user, TodoRequestDto requestDto) {
        TodoEntity todoEntity = new TodoEntity(requestDto, user.toEntity());
        todoRepository.save(todoEntity);
        return Todo.from(todoEntity).responseDto();
    }

    public TodoResponseDto updateTodo(User user, TodoRequestDto requestDto, Long id,
        Boolean isCompleted, Boolean isPrivate) {
        Todo todo = user.getTodoBy(id);

        todo.update(requestDto, isCompleted, isPrivate);
        todoRepository.update(todo);

        return todo.responseDto();
    }

    public void deleteTodo(User user, Long id) {
        Todo todo = user.getTodoBy(id);

        todoRepository.delete(todo);
    }

}
