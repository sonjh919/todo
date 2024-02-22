package com.sparta.todo.mock;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.domain.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.test.util.ReflectionTestUtils;

public class MockUserRepository implements UserRepository {
    private Long autoGeneratedId = 0L;
    private final Map<Long, UserEntity> store = new HashMap<>();


    @Override
    public User userBy(String userName) {
        return store.values().stream()
            .filter(userEntity -> userEntity.getUserName().equals(userName))
            .map(User::from)
            .findAny()
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(UserEntity userEntity) {
        store.put(++autoGeneratedId, userEntity);
        ReflectionTestUtils.setField(userEntity, "userId", autoGeneratedId);
    }

    @Override
    public void validateUserDuplicate(String username){
        Optional<User> user = store.values().stream()
            .filter(userEntity -> userEntity.getUserName().equals(username))
            .map(User::from)
            .findAny();

        if(user.isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }
    }

    @Override
    public List<User> findAll() {
        return store.values().stream()
            .map(User::from)
            .toList();
    }
}
