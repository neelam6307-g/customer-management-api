package com.example.customer_management_api.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {


    public CustomerNotFoundException(UUID id) {
        super("Customer not found with id:" + id);
    }


    public CustomerNotFoundException(String name, boolean isName) {
        super("Customer not found with name:" + name);
    }


    public CustomerNotFoundException(String email) {
        super("Email not formatted:"+ email);
    }
}
