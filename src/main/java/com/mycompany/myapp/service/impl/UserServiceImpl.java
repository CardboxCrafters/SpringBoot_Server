package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.NamecardConverter;
import com.mycompany.myapp.converter.UserConverter;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.NamecardRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.controller.UserController;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import com.mycompany.myapp.web.dto.UserRequestDto;
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
    private final UserRepository userRepository;

    @Override
    public UserResponseDto.UserDto getUser(User user){
        NameCard namecard = namecardRepository.findByUserAndIsUserTrue(user);

        return userConverter.getUser(namecard);
    }

    @Override
    @Transactional
    public void updateUser(User user, UserRequestDto.UpdateUserDto request){
        NameCard namecard = namecardRepository.findByUserAndIsUserTrue(user);
        namecardRepository.updateNamecard(request.getName(), request.getCompany(), request.getDepartment(), request.getPosition(), request.getMobile(), request.getEmail(), request.getTel(), request.getFax(), request.getHomepage(), request.getAddress());
    }

    @Override
    @Transactional
    public void withdrawUser(User user){
        user.withdrawUser();
        userRepository.save(user);
    }
}
