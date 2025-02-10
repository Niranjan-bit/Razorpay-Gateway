package com.example.demoRazorpayIntegration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demoRazorpayIntegration.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByRazorpayOrderId(String razorpayOrderId);
}