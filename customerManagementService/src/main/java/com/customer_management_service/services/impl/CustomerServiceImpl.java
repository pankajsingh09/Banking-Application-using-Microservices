package com.customer_management_service.services.impl;

import com.customer_management_service.entites.Customer;
import com.customer_management_service.exceptions.ResourceNotFoundException;
import com.customer_management_service.repositories.CustomerRepository;
import com.customer_management_service.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer create(Customer customer) {

        String userId = UUID.randomUUID().toString();
        customer.setCustomerId(userId);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer get(String id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));

        return customer;
    }

    @Override
    public Customer update(String id, Customer customer) {
        Customer customer1 = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));
        customer1.setName(customer.getName());
        customer1.setPhone(customer.getPhone());
        customer1.setEmail(customer.getEmail());
        customer1.setAddress(customer.getAddress());


        return customerRepository.save(customer1);
    }

    @Override
    public void delete(String id) {


        Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found"));

        // Deleting Accounts from ACCOUNT-SERVICE
        // http://localhost:8083/account/user/d79beee9-de29-4633-91f7-6be276e6e3c4

        restTemplate.delete("http://ACCOUNT-SERVICE/account/user/" + customer.getCustomerId());

        this.customerRepository.delete(customer);
    }
}
