package com.example.service;


import com.example.exception.RentalOrderNotFoundException;
import com.example.model.Customer;
import com.example.model.RentalOrder;
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

    public RentalOrder placeRentalOrder(RentalOrder rentalOrder) {
        return rentalOrderRepository.save(rentalOrder);
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
