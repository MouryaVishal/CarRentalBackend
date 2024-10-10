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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int rentalDays;
    private Double orderTotal;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "rental_order_id")
    private Car cars;


    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
