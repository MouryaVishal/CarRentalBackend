package com.example.service;


import com.example.exception.CategoryNotFoundException;
import com.example.exception.CouponNotFoundException;
import com.example.model.Category;
import com.example.model.Coupon;
import com.example.model.Customer;
import com.example.model.RentalOrder;
import com.example.repo.CategoryRepository;
import com.example.repo.CouponRepository;
import com.example.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    public List<Coupon> findCouponForCustomer(Long id, int days ){
        List<Coupon> coupons = new ArrayList<>();
        if(days>50)return coupons;
        Optional<Customer> customer = customerRepository.findById(id);
//        System.out.println("wq");
        if(customer.isEmpty()){
            Coupon co=new Coupon();
            co.setName("FIRSTTIME");
            co.setId(1L);
            co.setDiscountValue(50D);
            coupons.add(co);
        }
        if (days>=30){
            Coupon co=new Coupon();
            co.setName("Coupon30");
            co.setId(2L);
            co.setDiscountValue(30D);
            coupons.add(co);
        }else if (days>=10){
            Coupon co=new Coupon();
            co.setName("Coupon20");
            co.setId(5L);
            co.setDiscountValue(20D);
            coupons.add(co);
        }else {
            Coupon co=new Coupon();
            co.setName("Coupon10");
            co.setId(4L);
            co.setDiscountValue(10D);
            coupons.add(co);
        }
        System.out.println(coupons);
        return coupons;
    }
}
