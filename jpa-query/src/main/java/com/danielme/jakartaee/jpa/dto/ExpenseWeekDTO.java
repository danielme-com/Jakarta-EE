package com.danielme.jakartaee.jpa.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ExpenseWeekDTO extends ExpenseSummaryDTO {

    private final Integer week;

    public ExpenseWeekDTO(String concept, BigDecimal amount, LocalDate date, Integer week) {
        super(concept, amount, date);
        this.week = week;
    }

}
