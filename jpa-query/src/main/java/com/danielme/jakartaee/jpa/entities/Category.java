package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.QueryHints;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NamedQuery(name = "Category.findByName",
        query = "SELECT c FROM Category c WHERE c.name LIKE :name ORDER BY c.name",
        hints = {@QueryHint(name = QueryHints.READ_ONLY, value = "true")})
@NamedQuery(name = "Category.summary",
        query = "SELECT c.id AS id, c.name AS name, SUM (e.amount) AS total, COUNT(e) AS expenses " +
                "FROM Category c LEFT JOIN c.expenses e " +
                "GROUP BY c.id ORDER BY c.name")
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

    @ManyToMany(mappedBy = "categories")
    private List<Budget> budgets;

    // Never load all the expenses, use a query instead.
    // This relation exists only to be used in JPQL and Criteria API OUTER JOIN
    @OneToMany(mappedBy = "category")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Expense> expenses;

}
