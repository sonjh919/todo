package com.sparta.todo.domain.user.repository;

import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String username);
}
