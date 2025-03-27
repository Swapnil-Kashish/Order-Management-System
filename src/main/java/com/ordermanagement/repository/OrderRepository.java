package com.ordermanagement.repository;

import com.ordermanagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT o.customer.id AS customerId, COUNT(o) AS orderCount FROM Order o GROUP BY o.customer.id")
    List<Map<String, Object>> getTotalOrdersByCustomer();

    @Query("SELECT o.customer.id AS customerId, COUNT(o) AS orderCount FROM Order o GROUP BY o.customer.id ORDER BY COUNT(o) DESC LIMIT 5")
    List<Map<String, Object>> getTop5CustomersByOrders();
}
