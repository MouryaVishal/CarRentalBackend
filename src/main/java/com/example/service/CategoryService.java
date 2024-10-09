package com.example.service;

import com.example.model.Category;
import com.example.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
}
