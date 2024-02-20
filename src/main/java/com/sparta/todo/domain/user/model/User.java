package com.sparta.todo.domain.user.model;

import com.sparta.todo.domain.user.entity.UserEntity;
import com.sparta.todo.global.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class User {

    private String userName;
    private String password;

    public static User from(final UserEntity userEntity){
        return new User(
            userEntity.getUserName(),
            userEntity.getPassword()
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
}
