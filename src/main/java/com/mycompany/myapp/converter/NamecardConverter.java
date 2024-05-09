package com.mycompany.myapp.converter;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NamecardConverter {

    public Category createCategory(User user, String name){
        return Category.builder()
                .user(user)
                .name(name)
                .build();
    }

    public NameCard createNamecard(User user, NamecardRequestDto.CreateNamecardDto request, Category category){
        return NameCard.builder()
                .user(user)
                .mobile(request.getMobile())
                .email(request.getEmail())
                .company(request.getCompany())
                .department(request.getDepartment())
                .position(request.getPosition())
                .tel(request.getTel())
                .fax(request.getFax())
                .homepage(request.getHomepage())
                .address(request.getAddress())
                .category(category)
                .build();
    }
}
