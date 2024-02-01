package com.sparta.todo.todo.service;


import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.todo.dto.GetTodoResponseDto;
import com.sparta.todo.todo.dto.TodoRequestDto;
import com.sparta.todo.todo.dto.CreateTodoResponseDto;
import com.sparta.todo.todo.entity.Todo;
import com.sparta.todo.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CreateTodoResponseDto createTodo(String accessToken, TodoRequestDto requestDto){
        String author = jwtUtil.getUserInfoFromToken(accessToken);
        Todo todo = new Todo(requestDto, author);

        return new CreateTodoResponseDto(todoRepository.save(todo));
    };

    public GetTodoResponseDto getTodoById(Long id) {
        Todo todo = findTodo(id);
        return new GetTodoResponseDto(todo.getTitle(), todo.getContent(), todo.getAuthor(), todo.getDateCreated());
    }

    private Todo findTodo(Long id) {
        return todoRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("[ERROR] 선택한 일정은 존재하지 않습니다.")
        );
    }
}
