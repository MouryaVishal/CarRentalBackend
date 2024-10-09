package com.example.controller;

import com.example.model.Category;
import com.example.service.CarService;
import com.example.service.CategoryService;
import com.example.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private CarService carService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addcategory")
    private ResponseEntity<Category> addCategory(@RequestBody Category category){
        Category newCategory=categoryService.addCategory(category);
        return ResponseEntity.ok(newCategory);
    }
}
