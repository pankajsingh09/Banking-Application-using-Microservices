package com.customer_management_service.controllers;


import com.customer_management_service.entites.Customer;
import com.customer_management_service.payloads.ApiResponse;
import com.customer_management_service.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create
    @PostMapping
    public ResponseEntity<Customer> createUser(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
    }


    // Get all

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customerService.getAll());
    }


    // Get one
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId){

        return ResponseEntity.status(HttpStatus.OK).body(customerService.get(customerId));

    }

    //delete
    @DeleteMapping("/{customerId}")
    public ApiResponse deleteCustomer(@PathVariable String customerId)
    {

        this.customerService.delete(customerId);
        return new ApiResponse(" Customer is Successfully Deleted !!", true);
    }

    //update

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String customerId)
    {

        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(customerId, customer));

    }

}
