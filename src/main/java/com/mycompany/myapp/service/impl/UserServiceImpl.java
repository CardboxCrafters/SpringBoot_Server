package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.UserConverter;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.controller.UserController;
import com.mycompany.myapp.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final NamecardRepository namecardRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponseDto.UserDto getUser(User user){
        NameCard namecard = namecardRepository.findByUserAndIsUserTrue(user);

        return userConverter.getUser(namecard);
    }
}
