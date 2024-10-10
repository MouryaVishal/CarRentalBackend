package com.example.service.serviceInterface;

import com.example.model.Category;

public interface CategoryServiceInterface {
    public Category addCategory(Category category);
    public Iterable<Category> allCategory();
}
