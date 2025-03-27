package com.ordermanagement.service;

import com.ordermanagement.entity.Customer;
import com.ordermanagement.entity.Order;
import com.ordermanagement.entity.OrderItem;
import com.ordermanagement.entity.Product;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.repository.CustomerRepository;
import com.ordermanagement.repository.OrderRepository;
import com.ordermanagement.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Order placeOrder(Long customerId, List<OrderItem> items) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Deduct stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            item.setProduct(product);
            item.setOrder(order);

            // Calculate item price
            BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemPrice);
        }

        order.setItems(items);
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public BigDecimal getTotalOrderAmountByCustomerId(Long customerId) {
        List<Order> orders = getOrdersByCustomerId(customerId);

        // Sum up the total amounts from all orders
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Map<String, Object>> getTotalOrdersByCustomer() {
        return orderRepository.getTotalOrdersByCustomer();
    }

    public List<Map<String, Object>> getTop5CustomersByOrders() {
        return orderRepository.getTop5CustomersByOrders();
    }
}
