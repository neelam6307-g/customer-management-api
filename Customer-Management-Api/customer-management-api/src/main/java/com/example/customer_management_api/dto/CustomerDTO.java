package com.example.customer_management_api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CustomerDTO {

    private UUID id;
    private String name;
    private String email;
    private BigDecimal annualSpend;
    private OffsetDateTime lastPurchaseDate;
    private String tier;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OffsetDateTime getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(OffsetDateTime lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(BigDecimal annualSpend) {
        this.annualSpend = annualSpend;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
}

