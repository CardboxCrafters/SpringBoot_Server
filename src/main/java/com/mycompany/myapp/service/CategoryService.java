package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;

public interface CategoryService {
    void createCategory(User user, String name);
    void deleteCategory(User user, Long categoryId);
}
