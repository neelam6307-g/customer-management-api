package com.example.customer_management_api.controller;

import com.example.customer_management_api.Service.CustomerService;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.rest.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() throws Exception {
        Customer c1 = new Customer();
        c1.setId(UUID.randomUUID());
        c1.setName("Neelam");
        c1.setEmail("neelam@gmail.com");
        c1.setAnnualSpend(BigDecimal.valueOf(1200));
        c1.setLastPurchaseDate(OffsetDateTime.now());

        when(customerService.getAllCustomers()).thenReturn(List.of(c1));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Neelam"));
    }


    @Test
    public void testGetCustomerById() throws Exception {
        UUID id = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Anjali");
        customer.setEmail("anjali@gmail.com");

        when(customerService.getCustomerById(id)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anjali"));
    }


    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Riya");
        customer.setEmail("riya@gmail.com");
        customer.setAnnualSpend(BigDecimal.valueOf(1500));

        when(customerService.saveCustomer(org.mockito.ArgumentMatchers.any(Customer.class)))
                .thenReturn(customer);

        String requestBody = """
                {
                    "name": "Riya",
                    "email": "riya@gmail.com",
                    "annualSpend": 1500
                }
                """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Riya"));
    }


    @Test
    public void testDeleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/customers/" + id))
                .andExpect(status().isOk());
    }
}
