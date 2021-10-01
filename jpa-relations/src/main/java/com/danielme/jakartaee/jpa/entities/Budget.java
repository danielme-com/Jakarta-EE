package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class Budget {

    private static final int NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NAME_LENGTH)
    private String name;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, name="date_from")
    private LocalDate dateFrom;

    @Column(name="date_to")
    private LocalDate dateTo;

    @ManyToMany
    @JoinTable(name = "budgets_categories",
            joinColumns = @JoinColumn(name = "budget_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OrderBy("name DESC")
    private List<Category> categories;

}
