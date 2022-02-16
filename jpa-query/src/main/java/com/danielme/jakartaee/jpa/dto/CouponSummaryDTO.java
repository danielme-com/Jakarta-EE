package com.danielme.jakartaee.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CouponSummaryDTO {

    private final Long id;
    private final String name;
    private final String concept;
    private final LocalDate date;

}
