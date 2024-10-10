package com.example.controller;

import com.example.model.Coupon;
import com.example.model.Customer;
import com.example.model.RentalOrder;
import com.example.service.CustomerService;
import com.example.service.RentalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentalOrderService rentalOrderService;


//    For customer

    @PostMapping("/addcustomer")
    public ResponseEntity<Customer> addCoupon(@RequestBody Customer customer){
        Customer newCategory=customerService.addCustomer(customer);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("/allcustomer")
    public ResponseEntity<Iterable<Customer>> allcustomer(){
        Iterable<Customer> customers=customerService.allCustomer();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping ("/deletecustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        String responseStr=customerService.deleteById(id);
        return ResponseEntity.ok(responseStr);
    }
    @PutMapping("/updatecustomer/{id}")
    public ResponseEntity<Customer> updateCar(@PathVariable Long id,@RequestBody Customer customer){
        return customerService.updateById(id,customer);
    }


//    For RentralOrder;

    @PostMapping("/placeOrder")
    public ResponseEntity<RentalOrder> placeOrder(@RequestBody RentalOrder rentalOrder){
        RentalOrder newRentalOrder=rentalOrderService.placeRentalOrder(rentalOrder);
        return ResponseEntity.ok(newRentalOrder);
    }

    @GetMapping("/allrentalorder")
    public ResponseEntity<Iterable<RentalOrder>> allRentalOrder(){
        Iterable<RentalOrder> rentalOrders=rentalOrderService.allRentralOrder();
        return ResponseEntity.ok(rentalOrders);
    }

    @DeleteMapping ("/deleterentalorder/{id}")
    public ResponseEntity<String> deleteRentalOrder(@PathVariable Long id){
        String responseStr=rentalOrderService.deleteById(id);
        return ResponseEntity.ok(responseStr);
    }

    @PutMapping("/updaterentalorder/{id}")
    public ResponseEntity<RentalOrder> updateRentalOrder(@PathVariable Long id,@RequestBody RentalOrder rentalOrder){
        return rentalOrderService.updateById(id,rentalOrder);
    }
}
