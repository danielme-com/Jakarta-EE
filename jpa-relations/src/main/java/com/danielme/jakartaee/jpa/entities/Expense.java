package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String concept;

    @Column(length = 512)
    private String comments;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //@JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "expense", fetch = FetchType.LAZY)
    //@LazyToOne(LazyToOneOption.NO_PROXY) //bytecode enhancement
    private Coupon coupon;

    // alternative mapping to allow lazy loading for coupon
    /*
     @Getter(AccessLevel.NONE)
     @Setter(AccessLevel.NONE)
     @OneToMany(mappedBy = "expense")
     private List<Coupon> coupons = new ArrayList<>(1);

     public void setCoupon(Coupon coupon) {
     if (coupon == null) {
     coupons.clear();
     } else {
     coupons.set(0, coupon);
     }
     }

     public Coupon getCoupon() {
     return coupons.isEmpty() ? null : coupons.get(0);
     }  */

}