package com.example.service;

import com.example.exception.CustomerExceptionController;
import com.example.exception.CustomerNotFoundException;
import com.example.model.Coupon;
import com.example.model.Customer;
import com.example.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Customer addCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Iterable<Customer> allCustomer(){
        return customerRepository.findAll();
    }

    public String deleteById(Long id){
        if(customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return "Deleted SuccessFully!!";
        };
        return "Delete Fail. No Such id found!!";
    }

    public ResponseEntity<Customer> updateById(Long id, Customer customer) {
        Optional<Customer> optionalCategory = customerRepository.findById(id);
        if (optionalCategory.isPresent()) {
//            Category category = optionalCategory.get();
//            category.setCategoryName(categoryDetails.getCategoryName());
            Customer updatedCat = customerRepository.save(customer);
            return new ResponseEntity<>(updatedCat, HttpStatus.OK);
        } else {
            throw new CustomerNotFoundException();
        }
    }
}
