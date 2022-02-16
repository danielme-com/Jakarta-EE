package com.danielme.jakartaee.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDTO {

    private Long id;
    private String name;
    private BigDecimal total;
    private Long expenses;

}

