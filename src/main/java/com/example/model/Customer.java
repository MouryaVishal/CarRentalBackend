package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private Boolean fistTime;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<RentalOrder> orders;

}
