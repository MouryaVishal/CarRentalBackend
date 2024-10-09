package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Currency;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rentalDays;
    private Double orderTotal;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @Autowired
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "rental_order_car",
            joinColumns = @JoinColumn(name = "rental_order_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
