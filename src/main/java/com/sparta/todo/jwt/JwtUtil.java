package com.sparta.todo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    private final String INVALID_JWT_SIGNATURE = "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
    private final String EXPIRED_JWT_TOKEN = "Expired JWT token, 만료된 JWT token 입니다.";
    private final String UNSUPPORTED_JWT_TOKEN = "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
    private final String EMPTY_JWT_CLAIMS = "JWT claims is empty, 잘못된 JWT 토큰 입니다.";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(username) // 사용자 식별자값(ID)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                .setIssuedAt(date) // 발급일
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    public String getUserInfoFromToken(String bearerToken) {
        String token = getJwtFromHeader(bearerToken);
        if(validateToken((token))){
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        }
        throw new IllegalArgumentException("오류");
    }

    private String getJwtFromHeader(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error(INVALID_JWT_SIGNATURE);
            throw new JwtException(INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error(EXPIRED_JWT_TOKEN);
            throw new JwtException(EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error(UNSUPPORTED_JWT_TOKEN);
            throw new JwtException(UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error(EMPTY_JWT_CLAIMS);
            throw new JwtException(EMPTY_JWT_CLAIMS);
        }
    }
}
