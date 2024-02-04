package com.sparta.todo.todo.service;


import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.dto.TodoResponseDto;
import com.sparta.todo.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.todo.dto.GetTodoResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TodoResponseDto createTodo(String accessToken, TodoRequestDto requestDto) {
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        User user = userRepository.findByUserName(author).orElseThrow();
        Todo todo = new Todo(requestDto, user);

        return new TodoResponseDto(todoRepository.save(todo));
    }

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = findTodo(id);
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
    public TodoResponseDto updateTodo(String accessToken, TodoRequestDto requestDto, Long id,
        Boolean isCompleted, Boolean isPrivate) {
        Todo todo = getTodoByTokenAndId(accessToken, id);

        todo = setTodoComplete(todo, isCompleted);
        todo = setTodoPrivate(todo, isPrivate);
        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(String accessToken, Long id) {
        Todo todo = getTodoByTokenAndId(accessToken, id);

        todoRepository.delete(todo);
    }

    private Todo getTodoByTokenAndId(String accessToken, Long id) {
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        User user = validateUser(author);
        return getTodoByAuthor(user, id);
    }

    private Todo getTodoByAuthor(User user, Long id) {
        return user.getTodos().stream()
            .filter(todos -> todos.getTodoId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("작성자만 삭제/수정할 수 있습니다."));
    }

    private Todo findTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("선택한 일정은 존재하지 않습니다.")
        );
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

    private User validateUser(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
            () -> new NoSuchElementException("회원을 찾을 수 없습니다.")
        );
    }
}
