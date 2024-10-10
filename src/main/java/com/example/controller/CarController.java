package com.example.controller;

import com.example.model.Car;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;
    //    view cars as per category
    @GetMapping("/category/{category}")
    public ResponseEntity<Iterable<Car>> allCar(@PathVariable String category){
        Iterable<Car> cars=carService.allCarsByCategory(category);
        return ResponseEntity.ok(cars);
    }

    //search a specific car
    @GetMapping("/{name}")
    public ResponseEntity<Iterable<Car>> searchByCarName(@PathVariable String name){
        Iterable<Car> cars=carService.searchByCarName(name);
        return ResponseEntity.ok(cars);
    }
}
