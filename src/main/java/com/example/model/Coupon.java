package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double discountValue;

    @ManyToMany(mappedBy = "coupon",cascade = CascadeType.ALL)
    private List<RentalOrder> rentalOrders;
}
