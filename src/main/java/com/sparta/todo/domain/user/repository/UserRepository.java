package com.sparta.todo.domain.user.repository;

import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;
    
    public void save(UserEntity userEntity) {
        userJpaRepository.save(userEntity);
    }

    public void validateUserDuplicate(String username) {
        if (userJpaRepository.findByUserName(username).isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }
    }

    public List<User> findAll() {
        return userJpaRepository.findAll().stream().map(User::from).toList();
    }
}
