package com.mycompany.myapp.converter;

import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserConverter {

    public UserResponseDto.UserDto getUser(NameCard namecard){
        return UserResponseDto.UserDto.builder()
                .name(namecard.getName())
                .company(namecard.getCompany())
                .department(namecard.getDepartment())
                .position(namecard.getPosition())
                .mobile(namecard.getMobile())
                .email(namecard.getEmail())
                .tel(namecard.getTel())
                .fax(namecard.getFax())
                .homepage(namecard.getHomepage())
                .address(namecard.getAddress())
                .build();
    }
}
