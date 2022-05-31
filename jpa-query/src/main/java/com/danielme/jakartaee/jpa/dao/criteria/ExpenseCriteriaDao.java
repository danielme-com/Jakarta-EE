package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.dto.ExpenseDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseWeekDTO;
import com.danielme.jakartaee.jpa.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseCriteriaDao extends GenericDAO<Expense, Long> {

    Page<Expense> findAll(int first, int max);

    List<String> getConcepts();

    List<ExpenseSummaryDTO> getSummaryRaw();

    List<ExpenseSummaryDTO> getSummaryTuple();

    List<ExpenseSummaryDTO> getSummaryConstructor();

    List<Expense> findAllByMax();

    List<Expense> findAllByMax(BigDecimal maxAmount);

    List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo);

    List<Expense> findAboveAverage();

    List<Expense> findByCategories(List<Long> ids);

    List<Expense> findByCategory(Long id);

    List<ExpenseWeekDTO> getSummaryWithWeek();

    List<ExpenseDTO> findAllWithType();

}
