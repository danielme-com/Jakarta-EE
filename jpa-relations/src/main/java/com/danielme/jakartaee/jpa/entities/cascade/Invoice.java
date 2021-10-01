package com.danielme.jakartaee.jpa.entities.cascade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
public class Invoice {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items;

    /*@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey
    private Map<Long, InvoiceItem> items;*/

}

