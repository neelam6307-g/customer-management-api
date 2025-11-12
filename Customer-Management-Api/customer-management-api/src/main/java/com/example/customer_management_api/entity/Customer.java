package com.example.customer_management_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false,unique = true)
    private String email;

    private BigDecimal annualSpend;

    private OffsetDateTime lastPurchaseDate;

    public Customer() {}

    public Customer(String name, String email, BigDecimal annualSpend, OffsetDateTime lastPurchaseDate) {
        this.name = name;
        this.email = email;
        this.annualSpend = annualSpend;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(BigDecimal annualSpend) {
        this.annualSpend = annualSpend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(OffsetDateTime lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }
}
