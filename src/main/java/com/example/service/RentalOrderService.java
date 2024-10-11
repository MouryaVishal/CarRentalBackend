package com.example.service;


import com.example.exception.CouponNotFoundException;
import com.example.exception.RentalOrderNotFoundException;
import com.example.model.*;
import com.example.repo.CarRepository;
import com.example.repo.CouponRepository;
import com.example.repo.CustomerRepository;
import com.example.repo.RentalOrderRepository;

import com.example.request.RequsetRentalOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class RentalOrderService {
    @Autowired
    private RentalOrderRepository rentalOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Transactional
    public ResponseEntity<Object> placeRentalOrder(RequsetRentalOrder request) {
        String customerName=request.getCustomerName();
        String carName=request.getCarName();
        List<String> couponNames=request.getCouponNames();
        int noOfDaysForRent=request.getNoOfDaysForRent();
        String carCategoryName=request.getCarCategoryName();

        Optional<Customer> findCustomer=customerRepository.findByName(customerName);

        // Fetch customer
        long customerId = request.getCustomer().getId();
        Optional<Customer> customer = customerRepository.findById(customerId);

        if(customer.isEmpty()) {
            return new ResponseEntity<>("Sorry! Customer not found...",HttpStatus.NOT_FOUND);
        }
        long carId = request.getCars().getId();
        Optional<Car> car = carRepository.findById(carId);
        System.out.println("temp "+car);
        if(car.isPresent() && !car.get().getIsAvailable()){
            return new ResponseEntity<>("Sorry! car is used by other customer...",HttpStatus.NOT_FOUND);
        }
        if(car.isEmpty()){
            return new ResponseEntity<>("temp,Sorry! car not found...",HttpStatus.NOT_FOUND);
        }
        List<Coupon> coupons = request.getCoupon();
        for(Coupon c:coupons){
            if(!request.getCustomer().getFistTime() && Objects.equals(c.getName(), "FIRSTTIME")){
                coupons.remove(c);
                continue;
            }
            Long couponId=c.getId();
            Coupon coupon=couponRepository.findById(couponId)
                    .orElseThrow(() -> new CouponNotFoundException());
        }

        // Validate rental days
        if (request.getRentalDays() <= 0 || request.getRentalDays() > 30) {
            return new ResponseEntity<>("Sorry! Rental Days can not be less then 0 and more the 30..."
                    ,HttpStatus.NOT_FOUND);
        }

        // Calculate total cost
        double totalCost = car.get().getPricePerDay() * request.getRentalDays();

        // Check if coupon is provided and apply ii
        totalCost = applyCouponDiscount(totalCost, coupons, request.getRentalDays());

        RentalOrder rentalOrder = new RentalOrder();
        car.get().setIsAvailable(false);
        customer.get().setFistTime(false);
        rentalOrder.setCustomer(customer.get());
        rentalOrder.setCars(car.get());
        rentalOrder.setRentalDays(request.getRentalDays());
        rentalOrder.setOrderTotal(totalCost);
        rentalOrder.setCoupon(request.getCoupon());

        rentalOrderRepository.save(rentalOrder);
        return new ResponseEntity<>(rentalOrder,HttpStatus.OK);
    }
    private double applyCouponDiscount(double totalCost, List<Coupon> coupons, int rentalDays) {
        System.out.println(coupons);
        double discount = 0;
        for (Coupon coupon : coupons){
            Optional<Coupon> currCoupon=couponRepository.findById(coupon.getId());
            String couponName="";
            if(currCoupon.isPresent()){
                couponName=currCoupon.get().getName();
            }
            System.out.println(currCoupon);
            switch (couponName) {
                case "FIRSTTIME":
                    discount = totalCost * 0.5;
                    break;
                case "Coupon10":
                    discount = totalCost * coupon.getDiscountValue() / 100;
                    break;
                case "Coupon30":
                    discount = totalCost * coupon.getDiscountValue() / 100;
                    break;
                case "Coupon20":
                    discount = totalCost * coupon.getDiscountValue() / 100;
                    break;
                case "Coupon50":
                    discount = totalCost * coupon.getDiscountValue() / 100;
                    break;
                case "CouponByDays":
                    if (rentalDays >= 5 && rentalDays < 10) {
                        discount = totalCost * 0.1;
                    } else if (rentalDays >= 10 && rentalDays < 30) {
                        discount = totalCost * 0.2;
                    } else if (rentalDays == 30) {
                        discount = totalCost * 0.3;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid coupon type");
            }
        }

        return totalCost - discount;
    }




    public Iterable<RentalOrder> allRentralOrder() {
        return rentalOrderRepository.findAll();
    }

    public String deleteById(Long id) {
        if (rentalOrderRepository.existsById(id)) {
            rentalOrderRepository.deleteById(id);
            return "Deleted SuccessFully!!";
        }
        ;
        return "Delete Fail. No Such id found!!";
    }

    public ResponseEntity<RentalOrder> updateById(Long id, RentalOrder rentalOrder) {
        Optional<RentalOrder> optionalCategory = rentalOrderRepository.findById(id);
        if (optionalCategory.isPresent()) {
//            Category category = optionalCategory.get();
//            category.setCategoryName(categoryDetails.getCategoryName());
            RentalOrder updatedCat = rentalOrderRepository.save(rentalOrder);
            return new ResponseEntity<>(updatedCat, HttpStatus.OK);
        } else {
            throw new RentalOrderNotFoundException();
        }
    }
}
