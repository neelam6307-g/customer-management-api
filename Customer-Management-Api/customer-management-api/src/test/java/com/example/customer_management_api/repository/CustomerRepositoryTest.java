package com.example.customer_management_api.repository;
import com.example.customer_management_api.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

    @DataJpaTest
    public class CustomerRepositoryTest {

        @Autowired
        private CustomerRepository customerRepository;

        @Test
        void testSaveCustomer() {
            Customer c = new Customer();
            c.setId(UUID.randomUUID());
            c.setName("Test");
            c.setEmail("test@example.com");
            Customer saved = customerRepository.save(c);
            assertNotNull(saved);
        }
    }


