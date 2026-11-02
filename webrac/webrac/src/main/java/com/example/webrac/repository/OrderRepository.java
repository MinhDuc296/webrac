package com.example.webrac.repository;

import com.example.webrac.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Sắp xếp đơn mới nhất lên đầu
    java.util.List<Order> findAllByOrderByOrderDateDesc();
}