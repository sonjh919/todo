package com.sparta.todo.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.domain.fixture.TestUser;
import com.sparta.todo.domain.user.dto.LoginRequestDto;
import com.sparta.todo.domain.user.dto.SignupRequestDto;
import com.sparta.todo.domain.user.dto.SignupResponseDto;
import com.sparta.todo.domain.user.repository.UserRepository;
import com.sparta.todo.global.jwt.JwtUtil;
import com.sparta.todo.mock.MockPasswordEncoder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    JwtUtil jwtUtil;
    PasswordEncoder passwordEncoder = new MockPasswordEncoder();
    private static final String USERNAME = "sjh";
    private static final String PASSWORD = "12345678";

    @Test
    void signup_회원가입_성공() {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        ReflectionTestUtils.setField(signupRequestDto, "userName", USERNAME);
        ReflectionTestUtils.setField(signupRequestDto, "password", PASSWORD);

        UserService userService = new UserService(userRepository, passwordEncoder, jwtUtil);

        //when
        SignupResponseDto signupResponseDto = userService.signup(signupRequestDto);

        //then
        assertEquals(USERNAME, signupResponseDto.getUserName());
        assertEquals(PASSWORD, signupRequestDto.getPassword());
    }

    @Test
    void login_로그인_성공() {
        //given
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        ReflectionTestUtils.setField(loginRequestDto, "userName", USERNAME);
        ReflectionTestUtils.setField(loginRequestDto, "password", PASSWORD);

        TestUser user = new TestUser();
        given(userRepository.userBy(USERNAME)).willReturn(user);
        UserService userService = new UserService(userRepository, passwordEncoder, jwtUtil);

        //when
        String token = userService.login(loginRequestDto);

        //then
        assertEquals(user.createToken(jwtUtil), token);
    }
}
