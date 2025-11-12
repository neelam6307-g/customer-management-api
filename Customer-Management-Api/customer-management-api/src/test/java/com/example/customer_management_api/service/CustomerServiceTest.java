package com.example.customer_management_api.service;
import com.example.customer_management_api.Service.CustomerService;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Neelam");
        customer.setEmail("neelam@example.com");
        customer.setAnnualSpend(new BigDecimal("5000"));
        customer.setLastPurchaseDate(OffsetDateTime.now().minusMonths(2));
    }

    @Test
    void testSaveCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer saved = customerService.saveCustomer(customer);
        assertNotNull(saved);
        assertEquals("Neelam", saved.getName());
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Optional<Customer> found = customerService.getCustomerById(customer.getId());
        assertTrue(found.isPresent());
        assertEquals("neelam@example.com", found.get().getEmail());
    }


    @Test
    void testUpdateCustomer() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedData = new Customer();
        updatedData.setName("Updated Neelam");
        updatedData.setEmail("updated@example.com");
        updatedData.setAnnualSpend(new BigDecimal("7000"));
        updatedData.setLastPurchaseDate(OffsetDateTime.now());

        Customer updated = customerService.updateCustomer(customer.getId(), updatedData);

        assertEquals("Updated Neelam", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
    }


    @Test
    void testDeleteCustomer() {
        UUID id = customer.getId();
        doNothing().when(customerRepository).deleteById(id);
        customerService.deleteCustomer(id);
        verify(customerRepository, times(1)).deleteById(id);
    }


    @Test
    void testTierGold() {
        customer.setAnnualSpend(new BigDecimal("2000"));
        customer.setLastPurchaseDate(OffsetDateTime.now().minusMonths(3));
        String tier = customerService.getCustomerTier(customer);
        assertEquals("Gold", tier);
    }

    @Test
    void testTierPlatinum() {
        customer.setAnnualSpend(new BigDecimal("15000"));
        customer.setLastPurchaseDate(OffsetDateTime.now().minusMonths(4));
        String tier = customerService.getCustomerTier(customer);
        assertEquals("Platinum", tier);
    }

    @Test
    void testTierSilver() {
        customer.setAnnualSpend(new BigDecimal("500"));
        customer.setLastPurchaseDate(OffsetDateTime.now().minusMonths(10));
        String tier = customerService.getCustomerTier(customer);
        assertEquals("Silver", tier);
    }


    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        List<Customer> list = customerService.getAllCustomers();
        assertEquals(1, list.size());
    }
}
