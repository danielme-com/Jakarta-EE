package com.danielme.jakartaee.jpa.entities.element;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
public class Product {

    @Column(nullable = false)
    private LocalDate added;

    @Column(nullable = false)
    private Integer quantity;
}
