package com.sparta.todo.domain.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.sparta.todo.domain.user.dto.SignupRequestDto;
import com.sparta.todo.domain.user.dto.SignupResponseDto;
import com.sparta.todo.domain.user.repository.UserJpaRepository;
import com.sparta.todo.domain.user.repository.UserRepository;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.global.validation.Validation;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserEntityServiceTest {
    @Mock
    Validation validation;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;

    @Test
    void 회원가입_성공_테스트() {
        //given
        String username = "sjh";
        String password = "12345678";
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUserName(username);
        signupRequestDto.setPassword(password);

        UserService userService = new UserService(validation, userRepository, passwordEncoder, jwtUtil);
//        given(userRepository.validateUserDuplicate(username));

        //when
        SignupResponseDto signupResponseDto = userService.signup(signupRequestDto);

        //then
        assertEquals(username, signupResponseDto.getUserName());
        assertEquals(password, signupRequestDto.getPassword());
    }



    @Test
    void login() {
    }
}
