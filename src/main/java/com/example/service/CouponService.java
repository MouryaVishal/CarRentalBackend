package com.example.service;


import com.example.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

}
