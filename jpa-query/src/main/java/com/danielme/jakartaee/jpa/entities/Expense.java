package com.danielme.jakartaee.jpa.entities;

import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@SqlResultSetMapping(
        name = "ExpenseSummaryMapping",
        classes = @ConstructorResult(
                targetClass = ExpenseSummaryDTO.class,
                columns = {
                        @ColumnResult(name = "concept"),
                        @ColumnResult(name = "amount"),
                        @ColumnResult(name = "date", type = LocalDate.class)
                }))
@NamedNativeQuery(name = "Expense.findByText", query = "SELECT * FROM expenses " +
        "WHERE UPPER(concept) like UPPER(:text) ORDER BY concept",
        resultClass = Expense.class)
@NamedNativeQuery(name = "Expense.Summary", query = "SELECT concept, amount, date " +
        "FROM expenses ORDER BY amount desc, date desc",
        resultSetMapping = "ExpenseSummaryMapping")
@NamedStoredProcedureQuery(
        name = "ExpenseSpCountExpenses",
        procedureName = "sp_count_expenses",
        parameters = {
                @StoredProcedureParameter(name = "concept", type = String.class,
                        mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "total", type = Long.class,
                        mode = ParameterMode.OUT)
        })
@NamedStoredProcedureQuery(
        name = "ExpenseSpCheapExpenses",
        procedureName = "sp_cheap_expenses",
        resultClasses = Expense.class,
        parameters = {
                @StoredProcedureParameter(name = "maxAmount", type = BigDecimal.class,
                        mode = ParameterMode.IN)
        })
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
    @JoinColumn(name = "category_id")
    private Category category;


}