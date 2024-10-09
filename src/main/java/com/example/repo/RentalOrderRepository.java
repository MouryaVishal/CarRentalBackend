package com.example.repo;

import com.example.model.RentalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalOrderRepository extends JpaRepository<RentalOrder,Long> {
}
