package com.mycompany.myapp.security;

import com.mycompany.myapp.dao.CustomUserDetails;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.exception.CustomExceptions;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //private static final List<String> PERMITTED_ENDPOINTS = Arrays.asList("/name", "/sms-certification/send","/sms-certification/confirm");
    private static final List<String> PERMITTED_ENDPOINTS = new ArrayList<>();
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();

        // 주어진 Token의 유효성 확인 -> 사용자 정보 가져옴
        CustomUserDetails userDetails = jwtUtil.getUserDetailsFromRefreshToken(token);

        Long userId = (Long)authentication.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (user != null && userDetails == jwtUtil.buildCustomUserDetails(user)) {
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }

        throw new CustomExceptions.Exception("User not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private boolean isPermittedEndpoint(String requestURI) {
        return PERMITTED_ENDPOINTS.contains(requestURI);
    }

}
