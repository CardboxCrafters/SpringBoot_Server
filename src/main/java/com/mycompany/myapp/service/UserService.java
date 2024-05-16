package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.UserResponseDto;

public interface UserService {
    UserResponseDto.UserDto getUser(User user);
}
