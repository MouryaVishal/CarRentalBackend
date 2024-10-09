package com.example.model;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double pricePerDay;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @Autowired
    private Category category;


    @ManyToMany(mappedBy = "cars")
    private List<RentalOrder> resntalOrders;
}
