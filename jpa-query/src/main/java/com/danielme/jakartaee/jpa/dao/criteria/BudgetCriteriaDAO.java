package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.entities.Budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BudgetCriteriaDAO extends GenericDAO<Budget, Long> {

    List<Budget> findAvailableByDateAndAmount(BigDecimal minAmount, LocalDate minDate, LocalDate maxDate);
}
