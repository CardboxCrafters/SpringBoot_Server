package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.converter.CategoryConverter;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryConverter categoryConverter;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createCategory(User user, String name){
        Category category = categoryConverter.createCategory(user,name);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(User user, Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        categoryRepository.delete(category);
    }
}
