package com.sparta.todo.user.service;

import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.user.dto.LoginRequestDto;
import com.sparta.todo.user.dto.SignupRequestDto;
import com.sparta.todo.user.dto.SignupResponseDto;
import com.sparta.todo.user.entity.User;
import com.sparta.todo.user.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

        User user = validateUser(userName);
        validatePassword(user, password);

        return jwtUtil.createToken(user.getUserName());
    }

    private void validatePassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("패스워드를 잘못 입력하였습니다.");
        }
    }

    private User validateUser(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
            () -> new NoSuchElementException("회원을 찾을 수 없습니다.")
        );
    }
    
    private void validateUserDuplicate(Optional<User> checkUsername) {
        if (checkUsername.isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }
    }
}