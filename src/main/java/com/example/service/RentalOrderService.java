package com.example.service;


import com.example.repo.RentalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalOrderService {
    @Autowired
    private RentalOrderRepository rentalOrderRepository;
}
