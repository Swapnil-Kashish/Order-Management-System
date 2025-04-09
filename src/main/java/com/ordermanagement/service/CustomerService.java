package com.ordermanagement.service;

import com.ordermanagement.entity.Customer;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }
    public List<String> findCustomerByProductName(String productName) {
        List<String> customerNames = customerRepository.findCustomerByProductName(productName);
        if (customerNames.isEmpty()) {
            throw new ResourceNotFoundException("No customers found for product: " + productName);
        }
        return customerNames;
    }
}
