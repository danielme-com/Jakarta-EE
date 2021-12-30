package com.danielme.jakartaee.jpa.dao;

import com.danielme.jakartaee.jpa.entities.Expense;

public interface ExpenseDAO extends GenericDAO<Expense, Long> {

    long countByCategoryId(Long categoryId);

}
