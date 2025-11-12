package com.example.customer_management_api.Service;


import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //Create new customer

    public Customer saveCustomer(Customer customer) {
        customer.setId(null); // DO NOT manually set UUID
        return customerRepository.save(customer);
    }

    //Get Customer BY id

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    //Get Customer By Name


    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }


    //Get Customer By Emails

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }


    //Get All Customers

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }


    public Customer updateCustomer(UUID id, Customer newData) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setName(newData.getName());
            customer.setEmail(newData.getEmail());
            customer.setAnnualSpend(newData.getAnnualSpend());
            customer.setLastPurchaseDate(newData.getLastPurchaseDate());

            return customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    // -------- Delete Customer --------
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    // -------- Calculate Tier --------

    public String getCustomerTier(Customer customer) {
        BigDecimal spend = customer.getAnnualSpend();
        OffsetDateTime lastPurchase = customer.getLastPurchaseDate();

        String tier = "Silver";

        if (spend == null) {
            return tier;
        }

        if (spend.compareTo(BigDecimal.valueOf(10000)) > 0) {
            if (lastPurchase != null && lastPurchase.isAfter(OffsetDateTime.now().minusMonths(6))) {
                tier = "Platinum";
            }
        } else if (spend.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            if (lastPurchase != null &&
                    lastPurchase.isAfter(OffsetDateTime.now().minusMonths(12))) {
                tier = "Gold";
            }
        }

        return tier;
    }

    public CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setAnnualSpend(customer.getAnnualSpend());
        dto.setLastPurchaseDate(customer.getLastPurchaseDate());
        dto.setTier(getCustomerTier(customer));
        return dto;
    }

}








