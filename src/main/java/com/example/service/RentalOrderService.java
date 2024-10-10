package com.example.service;


import com.example.exception.CarNotFoundException;
import com.example.exception.CouponNotFoundException;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.RentalOrderNotFoundException;
import com.example.model.Car;
import com.example.model.Coupon;
import com.example.model.Customer;
import com.example.model.RentalOrder;
import com.example.repo.CarRepository;
import com.example.repo.CouponRepository;
import com.example.repo.CustomerRepository;
import com.example.repo.RentalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public RentalOrder placeRentalOrder(RentalOrder request) {
        // Fetch customer
        Customer customer = customerRepository.findById(request.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException());

        // Fetch car
        Car car = carRepository.findById(request.getCars().getId())
                .orElseThrow(() -> new CarNotFoundException());

        Coupon coupon=couponRepository.findById(request.getCoupon().getId())
                .orElseThrow(() -> new CouponNotFoundException());
        System.out.println("sd"+coupon);

        // Validate rental days
        if (request.getRentalDays() <= 0 || request.getRentalDays() > 30) {
            throw new IllegalArgumentException("Rental days must be between 1 and 30");
        }

        // Calculate total cost
        double totalCost = car.getPricePerDay() * request.getRentalDays();

        // Check if coupon is provided and apply ii
        totalCost = applyCouponDiscount(totalCost, coupon, request.getRentalDays());

        RentalOrder rentalOrder = new RentalOrder();
        rentalOrder.setCustomer(customer);
        rentalOrder.setCars(car);
        rentalOrder.setRentalDays(request.getRentalDays());
        rentalOrder.setOrderTotal(totalCost);
        rentalOrder.setCoupon(request.getCoupon());
        return rentalOrderRepository.save(rentalOrder);
    }
    private double applyCouponDiscount(double totalCost, Coupon coupon, int rentalDays) {
        System.out.println(coupon);
        double discount = 0;
        switch (coupon.getName()) {
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
                    discount = totalCost * 0.1;  // 10% for 5-day rental
                } else if (rentalDays >= 10 && rentalDays < 30) {
                    discount = totalCost * 0.2;  // 20% for 10-day rental
                } else if (rentalDays == 30) {
                    discount = totalCost * 0.3;  // 30% for 30-day rental
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid coupon type");
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
