package com.sparta.todo.domain.user.model;

import com.sparta.todo.domain.todo.dto.GetTodoListResponseDto;
import com.sparta.todo.domain.todo.dto.GetTodoResponseDto;
import com.sparta.todo.domain.todo.entity.TodoEntity;
import com.sparta.todo.domain.todo.model.Todo;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.global.jwt.JwtUtil;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private String password;
    private List<TodoEntity> todoEntities;

    public static User from(final UserEntity userEntity){
        return new User(
            userEntity.getUserId(),
            userEntity.getUserName(),
            userEntity.getPassword(),
            userEntity.getTodoEntities()
        );
    }

    public UserEntity toEntity(){
        return new UserEntity(
            userId,
            userName,
            password,
            todoEntities
        );
    }

    public void validatePassword(String password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password, this.password)) {
            throw new BadCredentialsException("패스워드를 잘못 입력하였습니다.");
        }
    }

    public String createToken(JwtUtil jwtUtil) {
        return jwtUtil.createToken(userName);
    }

    public static GetTodoListResponseDto todoList(User user) {
        return new GetTodoListResponseDto(
            user.userName,
            user.todoEntities.stream()
            .sorted(Comparator.comparing(TodoEntity::getDateCreated).reversed())
            .map(GetTodoResponseDto::new)
            .toList()
        );
    }

    public Todo getTodoBy(Long id) {
        return Todo.from(todoEntities.stream()
            .filter(todos -> todos.getTodoId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("작성자만 삭제/수정할 수 있습니다.")));
    }

    public boolean validateByName(String userName) {
        return this.userName.equals(userName);
    }
}
