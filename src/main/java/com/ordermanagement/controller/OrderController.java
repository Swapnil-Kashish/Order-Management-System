package com.ordermanagement.controller;

import com.ordermanagement.entity.Order;
import com.ordermanagement.entity.OrderItem;
import com.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/{customerId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long customerId, @RequestBody List<OrderItem> items) {
        return ResponseEntity.ok(orderService.placeOrder(customerId, items));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/customer/{customerId}/total-amount")
    public ResponseEntity<BigDecimal> getTotalOrderAmountByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getTotalOrderAmountByCustomerId(customerId));
    }

    @GetMapping("/total-orders")
    public ResponseEntity<List<Map<String, Object>>> getTotalOrdersByCustomer() {
        return ResponseEntity.ok(orderService.getTotalOrdersByCustomer());
    }

    @GetMapping("/top-customers")
    public ResponseEntity<List<Map<String, Object>>> getTop5CustomersByOrders() {
        return ResponseEntity.ok(orderService.getTop5CustomersByOrders());
    }
}

