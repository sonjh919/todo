package com.sparta.todo.domain.user.repository;

import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

public interface UserRepository {
    User userBy(String userName);
    void save(UserEntity userEntity);
    void validateUserDuplicate(String username);
    List<User> findAll();
}
