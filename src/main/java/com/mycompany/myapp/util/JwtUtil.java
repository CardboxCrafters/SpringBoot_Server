package com.mycompany.myapp.util;

import com.mycompany.myapp.dao.CustomUserDetails;
import com.mycompany.myapp.domain.RefreshToken;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${spring.jwt.secretKey}")
    private String secret;

    @Value("${spring.jwt.access-expiration-time}")
    private long accessExpTime;

    @Value("${spring.jwt.refresh-expiration-time}")
    private long refreshExpTime;

    private final RefreshTokenRepository refreshTokenRepository;

    // jwt토큰의 claim에서 subject로 지정되는 User의 id 추출
    public Long extractId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject));
    }

    // jwt토큰의 claim에서 Expiration으로 지정되는 만료 시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //jwt 토큰에서 특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // token이 만료되었는지 확인
    // token의 만료 시간을 가져온 후, 현재 시간과 비교해 토큰의 만료 여부를 return
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // accessToken 발급 함수
    // subject로 지정되는 사용자 이름은 휴대폰 번호
    // tokenType은 access, 지금 시간으로 발급, accessExpTime에 만료됨.
    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("tokenType", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //refreshToken 발급 함수
    // subject로 지정되는 사용자 이름은 User의 id
    //tokenType은 refresh, 지금 시간으로 발급, refreshExpTime에 만료됨.
    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("tokenType", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 토큰이 유효한지 검사
    public Boolean validateToken(String token, User user) {
        final Long userId = extractId(token);
        return (userId.equals(user.getId()) && !isTokenExpired(token));
    }

    // token들의 만료 시간을 현재 시간으로 설정해서 토큰 무효화
    public void invalidateRefreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.get("tokenType").equals("refresh")) {
            claims.setExpiration(new Date());
        }
    }

    public Date calculateRefreshExpirationTime() {
        long currentMillis = System.currentTimeMillis();
        return new Date(currentMillis + refreshExpTime);
    }

    public CustomUserDetails getUserDetailsFromRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);

        if (refreshToken == null || isTokenExpired(token)) {
            throw new CustomExceptions.RefreshTokenInvalidException("Refresh Token이 유효하지 않습니다.");
        }

        User user = refreshToken.getUser();
        return buildCustomUserDetails(user);
    }

    public String generateAccessTokenFromRefreshToken(String refreshToken) {
        CustomUserDetails userDetails = getUserDetailsFromRefreshToken(refreshToken);
        Long id = extractId(refreshToken);

        if (userDetails == null || isTokenExpired(refreshToken)) {
            throw new CustomExceptions.RefreshTokenInvalidException("Refresh Token이 유효하지 않습니다.");
        }
        return generateAccessToken(id);
    }

    // User 엔티티를 CustomUserDetails로 바꿔주는 함수
    public CustomUserDetails buildCustomUserDetails(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .build();
    }

    public Collection<GrantedAuthority> getAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if (isAdmin) {
            authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorityList.add(new SimpleGrantedAuthority("USER"));
        }

        return authorityList;
    }
}
