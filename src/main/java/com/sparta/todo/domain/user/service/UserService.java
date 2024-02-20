package com.sparta.todo.domain.user.service;

import com.sparta.todo.domain.user.dto.LoginRequestDto;
import com.sparta.todo.domain.user.dto.SignupRequestDto;
import com.sparta.todo.domain.user.dto.SignupResponseDto;
import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.domain.user.model.User;
import com.sparta.todo.domain.user.repository.UserRepository;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.global.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final Validation validation;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUserName();
        String password = passwordEncoder.encode(requestDto.getPassword());
        userRepository.validateUserDuplicate(username);

        UserEntity userEntity = new UserEntity(username, password);
        userRepository.save(userEntity);

        return new SignupResponseDto(userEntity);
    }

    public String login(LoginRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        User user = validation.userBy(userName);
        user.validatePassword(password, passwordEncoder);

        return user.createToken(jwtUtil);
    }

}