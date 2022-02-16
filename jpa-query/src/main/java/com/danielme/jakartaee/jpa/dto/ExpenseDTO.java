package com.danielme.jakartaee.jpa.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ExpenseDTO {

    public enum ExpenseType {
        EXPENSIVE, STANDARD, SMALL
    }

    private final Long id;
    private final String concept;
    private final String category;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String comments;
    private final ExpenseType type;

    public ExpenseDTO(Long id, String concept, String category, BigDecimal amount, LocalDate date, String comments, String type) {
        this.id = id;
        this.concept = concept;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.comments = comments;
        this.type = ExpenseType.valueOf(type);
    }


}
