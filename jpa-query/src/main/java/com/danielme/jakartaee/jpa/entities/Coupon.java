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
@SqlResultSetMapping(
        name = "CouponWithCategory",
        entities = {
                @EntityResult(entityClass = Coupon.class),
                @EntityResult(entityClass = Expense.class, fields = {
                        @FieldResult(name = "id", column = "exp_id"),
                        @FieldResult(name = "amount", column = "exp_amount"),
                        @FieldResult(name = "category", column = "category_id"),
                        @FieldResult(name = "concept", column = "concept"),
                        @FieldResult(name = "comments", column = "comments"),
                        @FieldResult(name = "date", column = "date")}
                )
        }
)
public class Coupon {

    public static final String USED_PREFIX = "[USED] ";
    private static final int NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = NAME_LENGTH, nullable = false)
    private String name;

    private LocalDate expiration;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Expense expense;

}
