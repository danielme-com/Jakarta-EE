package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {

    private static final int NAME_LENGTH = 50;
    private static final int COLOR_LENGTH = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NAME_LENGTH, unique = true)
    private String name;

    @Column(length = COLOR_LENGTH, name = "color_hex")
    private String colorHex;

    @OneToMany(mappedBy = "category")
    @OrderBy("date DESC, amount DESC")
    private List<Expense> expenses;

}
