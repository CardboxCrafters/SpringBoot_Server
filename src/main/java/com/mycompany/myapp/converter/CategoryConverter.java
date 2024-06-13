package com.mycompany.myapp.converter;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryConverter {

    public Category createCategory(User user, String name){
        return Category.builder()
                .user(user)
                .name(name)
                .build();
    }

    public CategoryResponseDto.getCategoryDTO toCategory(Long id, String name){
        return CategoryResponseDto.getCategoryDTO.builder()
                .id(id)
                .name(name)
                .build();
    }

    public Category registerUser(User user){
        return Category.builder()
                .name("all")
                .user(user)
                .build();
    }
}
