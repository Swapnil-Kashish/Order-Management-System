package com.ordermanagement.controller;

import com.ordermanagement.entity.Customer;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.addCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        return updatedCustomer;
    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<List<String>> findCustomerByProductName(@PathVariable String productName) {
        List<String> customers = customerService.findCustomerByProductName(productName);
        return ResponseEntity.ok(customers);
    }
}

