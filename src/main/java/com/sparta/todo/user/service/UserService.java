package com.sparta.todo.user.service;

import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.user.dto.LoginRequestDto;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.dto.SignupResponseDto;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.exception.LoginException;
import com.sparta.todo.user.exception.SignupException;
import com.sparta.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUserName();
        String password = passwordEncoder.encode(requestDto.getPassword());

        validateUserDuplicate(userRepository.findByUserName(username));

        User user = new User(username, password);
        userRepository.save(user);
        return new SignupResponseDto(user);
    }

    public String login(LoginRequestDto requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        User user = userRepository.findByUserName(userName).orElseThrow(
            () -> new LoginException("회원을 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginException("패스워드를 잘못 입력하였습니다.");
        }

        return jwtUtil.createToken(user.getUserName());
    }


    private void validateUserDuplicate(Optional<User> checkUsername) {
        if (checkUsername.isPresent()) {
            throw new SignupException("중복된 사용자가 존재합니다.");
        }
    }
}