package com.sparta.todo.domain.todo.service;

import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.entity.Todo;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.user.entity.User;
import com.sparta.todo.domain.user.repository.UserRepository;
import com.sparta.todo.global.validation.Validation;
import java.util.List;
import java.util.NoSuchElementException;
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
        Todo todo = new Todo(requestDto, user);
        return new TodoResponseDto(todoRepository.save(todo));
    }

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = validation.findTodoBy(id);
        return new GetTodoResponseDto(todo);
    }

    public List<GetTodoListResponseDto> getTodos(String title) {
        List<User> users = userRepository.findAll();
        List<GetTodoListResponseDto> todoList = users.stream().map(GetTodoListResponseDto::new)
            .toList();

        if (title == null) {
            return todoList;
        }
        return getTodosByTitle(todoList, title);
    }

    @Transactional
    public TodoResponseDto updateTodo(User user, TodoRequestDto requestDto, Long id,
        Boolean isCompleted, Boolean isPrivate) {
        Todo todo = getTodoByAuthor(user, id);

        todo = setTodoComplete(todo, isCompleted);
        todo = setTodoPrivate(todo, isPrivate);
        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(User user, Long id) {
        Todo todo = getTodoByAuthor(user, id);

        todoRepository.delete(todo);
    }

    private Todo getTodoByAuthor(User user, Long id) {
        return user.getTodos().stream()
            .filter(todos -> todos.getTodoId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("작성자만 삭제/수정할 수 있습니다."));
    }

    private List<GetTodoListResponseDto> getTodosByTitle(List<GetTodoListResponseDto> todoList,
        String title) {
        return todoList.stream()
            .map(todos -> {
                List<GetTodoResponseDto> filteredTodos = todos.getTodos().stream()
                    .filter(todo -> todo.getTitle().equals(title))
                    .toList();
                return new GetTodoListResponseDto(todos.getAuthor(), filteredTodos);
            })
            .toList();
    }

    private Todo setTodoPrivate(Todo todo, Boolean isPrivate) {
        if (isPrivate != null) {
            todo.setPrivate(isPrivate);
        }
        return todo;
    }

    private Todo setTodoComplete(Todo todo, Boolean isCompleted) {
        if (isCompleted != null) {
            todo.setCompleted(isCompleted);
        }
        return todo;
    }

}
