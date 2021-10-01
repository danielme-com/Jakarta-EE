package com.danielme.jakartaee.jpa.entities.element;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@ElementCollection
    @CollectionTable(name = "carts_products",
            joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "reference")
    private Map<String, Product> products;*/

    @ElementCollection
    @CollectionTable(name = "carts_products",
            joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_reference")
    @Column(name = "price")
    private Map<String, BigDecimal> products;

}
