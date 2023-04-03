package com.snc.sncuserservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document
public class Transaction {
    private String userId;
    private Instant dateTime;
    private BigDecimal amount;

    public String getUserId() {
        return userId;
    }

    public Transaction setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public Transaction setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
