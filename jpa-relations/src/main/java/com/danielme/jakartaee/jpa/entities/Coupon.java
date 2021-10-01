package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Getter
@Setter
public class Coupon {

    private static final int NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =  NAME_LENGTH, nullable = false)
    private String name;

    private LocalDate expiration;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    // alternative mapping to allow lazy loading for coupon
    //@ManyToOne(fetch = FetchType.LAZY)
    private Expense expense;

}
