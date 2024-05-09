package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.web.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    void createCategory(User user, String name);
    void deleteCategory(User user, Long categoryId);
    List<CategoryResponseDto.getCategoryDTO> getCategory(User user);
}
