package com.sparta.todo.domain.todo.service;

import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.domain.user.repository.UserRepository;
import com.sparta.todo.global.validation.Validation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final Validation validation;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponseDto createTodo(User user, TodoRequestDto requestDto) {
        TodoEntity todoEntity = new TodoEntity(requestDto, user.toEntity());
        todoRepository.save(todoEntity);
        return Todo.from(todoEntity).responseDto();
    }

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = validation.findTodoBy(id);
        return todo.ResponseDto();
    }

    public List<GetTodoListResponseDto> getTodos(String title) {
        List<User> users = userRepository.findAll();
        List<GetTodoListResponseDto> todos = users.stream().map(User::todoList).toList();

        if (title == null) {
            return todos;
        }
        return getTodosByTitle(todos, title);
    }

    @Transactional
    public TodoResponseDto updateTodo(User user, TodoRequestDto requestDto, Long id,
        Boolean isCompleted, Boolean isPrivate) {
        Todo todo = user.getTodoBy(id);
        todo.update(isCompleted, isPrivate);
        todoRepository.update(todo);

        return todo.responseDto();
    }

    @Transactional
    public void deleteTodo(User user, Long id) {
        Todo todo = user.getTodoBy(id);

        todoRepository.delete(todo);
    }

    private List<GetTodoListResponseDto> getTodosByTitle(List<GetTodoListResponseDto> Usertodos,
        String title) {
        return Usertodos.stream()
            .map(todos -> {
                List<GetTodoResponseDto> filteredTodos = todos.getTodos().stream()
                    .filter(todo -> todo.getTitle().equals(title))
                    .toList();
                return new GetTodoListResponseDto(todos.getAuthor(), filteredTodos);
            })
            .toList();
    }

}
