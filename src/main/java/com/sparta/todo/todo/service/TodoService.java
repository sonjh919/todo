package com.sparta.todo.todo.service;


import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.todo.dto.GetTodoResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.CreateTodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
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
    public CreateTodoResponseDto createTodo(String accessToken, TodoRequestDto requestDto){
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        User user = userRepository.findByUserName(author).orElseThrow();
        Todo todo = new Todo(requestDto, user);

        return new CreateTodoResponseDto(todoRepository.save(todo));
    };

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = findTodo(id);
        return new GetTodoResponseDto(todo);
    }

    private Todo findTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("선택한 일정은 존재하지 않습니다.")
        );
    }

    public List<GetTodoListResponseDto> getTodos() {
        List<User> users = userRepository.findAll();
        return users.stream().map(GetTodoListResponseDto::new).collect(Collectors.toList());
    }
}
