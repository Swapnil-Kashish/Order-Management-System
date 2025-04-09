package com.ordermanagement.repository;

import com.ordermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT c.name FROM Customer c JOIN Order o ON c.id = o.customer.id " +
       "JOIN OrderItem oi ON o.id = oi.order.id " +
       "JOIN Product p ON oi.product.id = p.id " +
       "WHERE p.name LIKE %:productName%")
    List<String> findCustomerByProductName(@Param("productName") String productName);
}
