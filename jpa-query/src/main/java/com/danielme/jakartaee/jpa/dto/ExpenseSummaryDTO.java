package com.danielme.jakartaee.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSummaryDTO {

    private String concept;
    private BigDecimal amount;
    private LocalDate date;

    public ExpenseSummaryDTO(String concept, BigDecimal amount, java.sql.Date date) {
        this(concept, amount, date.toLocalDate());
    }

    public void setDate(java.sql.Date date) {
        this.date = date.toLocalDate();
    }

}
