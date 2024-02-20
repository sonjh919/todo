package com.sparta.todo.domain.todo.service;

import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.dto.TodoRequestDto;
import com.sparta.todo.domain.todo.dto.TodoResponseDto;
import com.sparta.todo.domain.todo.entity.Todo;
import com.sparta.todo.domain.todo.repository.TodoRepository;
import com.sparta.todo.domain.user.entity.UserEntity;
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
    public TodoResponseDto createTodo(UserEntity userEntity, TodoRequestDto requestDto) {
        Todo todo = new Todo(requestDto, userEntity);
        return new TodoResponseDto(todoRepository.save(todo));
    }

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = validation.findTodoBy(id);
        return new GetTodoResponseDto(todo);
    }

    public List<GetTodoListResponseDto> getTodos(String title) {
        List<UserEntity> userEntities = userRepository.findAll();
        List<GetTodoListResponseDto> todoList = userEntities.stream().map(GetTodoListResponseDto::new)
            .toList();

        if (title == null) {
            return todoList;
        }
        return getTodosByTitle(todoList, title);
    }

    @Transactional
    public TodoResponseDto updateTodo(UserEntity userEntity, TodoRequestDto requestDto, Long id,
        Boolean isCompleted, Boolean isPrivate) {
        Todo todo = getTodoByAuthor(userEntity, id);

        todo = setTodoComplete(todo, isCompleted);
        todo = setTodoPrivate(todo, isPrivate);
        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(UserEntity userEntity, Long id) {
        Todo todo = getTodoByAuthor(userEntity, id);

        todoRepository.delete(todo);
    }

    private Todo getTodoByAuthor(UserEntity userEntity, Long id) {
        return userEntity.getTodos().stream()
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
