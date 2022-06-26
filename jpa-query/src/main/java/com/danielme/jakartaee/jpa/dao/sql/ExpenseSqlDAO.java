package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseSqlDAO extends GenericDAO<Expense, Long> {

    BigDecimal sum();

    List<Expense> findAllNativePagination(int first, int max);

    List<Expense> findAllStandardPagination(int first, int max);

    List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo);

    List<Expense> findByText(String text);

    List<ExpenseSummaryDTO> getSummaryNamedQuery();

    List<ExpenseSummaryDTO> getSummaryRaw();

    List<ExpenseSummaryDTO> getSummaryTuple();

    List<ExpenseSummaryDTO> getSummaryConstructor();

    List<ExpenseSummaryDTO> getSummaryCustomResultTransformer();

    List<ExpenseSummaryDTO> getSummaryAliasResultTransformer();

    List<ExpenseSummaryDTO> getSummaryConstructorResultTransformer();

    long countExpensesWithProcedure(String concept);

    long countExpensesWithNamedProcedure(String concept);

    List<Expense> findCheapExpensesWithProcedure(BigDecimal maxAmount);

    List<Expense> findCheapExpensesWithNamedProcedure(BigDecimal maxAmount);
}
