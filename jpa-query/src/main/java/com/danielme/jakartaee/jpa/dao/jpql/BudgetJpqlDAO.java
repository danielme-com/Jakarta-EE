package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Budget;

public interface BudgetJpqlDAO extends GenericDAO<Budget, Long> {

    Page<Budget> fetchWithCategoriesMemoryPagination(int first, int max);

    Page<Budget> fetchWithCategoriesTwoQueries(int first, int max);
}
