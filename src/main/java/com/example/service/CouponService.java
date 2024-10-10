package com.example.service;


import com.example.exception.CategoryNotFoundException;
import com.example.exception.CouponNotFoundException;
import com.example.model.Category;
import com.example.model.Coupon;
import com.example.repo.CategoryRepository;
import com.example.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Coupon addCoupon(Coupon coupon){
        return couponRepository.save(coupon);
    }

    public Iterable<Coupon> allCoupon(){
        return couponRepository.findAll();
    }

    public String deleteById(Long id){
        if(couponRepository.existsById(id)) {
            couponRepository.deleteById(id);
            return "Deleted SuccessFully!!";
        };
        return "Delete Fail. No Such id found!!";
    }

    public ResponseEntity<Coupon> updateById(Long id, Coupon coupon) {
        Optional<Coupon> optionalCategory = couponRepository.findById(id);
        if (optionalCategory.isPresent()) {
//            Category category = optionalCategory.get();
//            category.setCategoryName(categoryDetails.getCategoryName());
            Coupon updatedCat = couponRepository.save(coupon);
            return new ResponseEntity<Coupon>(updatedCat, HttpStatus.OK);
        } else {
            throw new CouponNotFoundException();
        }
    }
}
