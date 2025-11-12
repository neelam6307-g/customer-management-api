package com.example.customer_management_api.rest;


import com.example.customer_management_api.Service.CustomerService;
import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody Customer customer) {
        Customer saved = customerService.saveCustomer(customer);
        return customerService.mapToDTO(saved);
    }


    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable UUID id) {
        Customer c = customerService.getCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));  // pass only id
        return customerService.mapToDTO(c);
    }

    @GetMapping(params = "name")
    public CustomerDTO getCustomerByName(@RequestParam String name) {
        Customer c = customerService.getCustomerByName(name)
                .orElseThrow(() -> new CustomerNotFoundException(name, true)); // pass name
        return customerService.mapToDTO(c);
    }

    @GetMapping(params = "email")
    public CustomerDTO getCustomerByEmail(@RequestParam String email) {
        Customer c = customerService.getCustomerByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email)); // pass email
        return customerService.mapToDTO(c);
    }


    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(customerService::mapToDTO) // simple method reference
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        Customer updated = customerService.updateCustomer(id, customer);
        return customerService.mapToDTO(updated);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return "Customer deleted with id: " + id;
    }
}





