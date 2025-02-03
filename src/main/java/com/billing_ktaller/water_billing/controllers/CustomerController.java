package com.billing_ktaller.water_billing.controllers;

import com.billing_ktaller.water_billing.models.Customers;
import com.billing_ktaller.water_billing.repositories.CustomerRepository;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    // POST method to create a new customer
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customers customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check for duplicate phone number and email before saving
        if (customerRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already exists.");
        }
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        customerRepository.save(customer);
        return ResponseEntity.ok("Customer created successfully");
    }

    // POST method to update a customer
    @PostMapping("/update")
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody Customers customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the customer exists before updating
        if (!customerRepository.existsById(customer.getId())) {
            return ResponseEntity.badRequest().body("Customer does not exist.");
        }

        customerRepository.save(customer);
        return ResponseEntity.ok("Customer updated successfully");
    }

    // POST method to delete a customer
    @PostMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@Valid @RequestBody Customers customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }

        // Check if the customer exists before deleting
        if (!customerRepository.existsById(customer.getId())) {
            return ResponseEntity.badRequest().body("Customer does not exist.");
        }

        customerRepository.delete(customer);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // POST method to get all customers
    @PostMapping("")
    public ResponseEntity<String> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll().toString());
    }

    // POST method to get all customers. ONLY id, names
    @PostMapping("-home")
    public ResponseEntity<String> getAllCustomersHome() {
        List<Customers> customers = customerRepository.findAll();
        List<Map<String, String>> customersHome = new ArrayList<>();

        for (Customers customer : customers) {
            Map<String, String> customerHome = Map.of(
                    "id", customer.getId().toString(),
                    "name", customer.getFirstName() + " " + customer.getLastName(),
                    "phone", customer.getPhoneNumber());
            customersHome.add(customerHome);
        }

        return ResponseEntity.ok(customersHome.toString());
    }
}
