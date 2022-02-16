package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public interface CategoryCriteriaDAO extends GenericDAO<Category, Long> {

    List<Category> findCategoriesByMinExpenseAmount(BigDecimal minAmount);

    List<Category> findAllCategoriesByBudgetMinAmount(BigDecimal minAmount);

    List<CategorySummaryDTO> getSummaryAverageAbove(BigDecimal maxAverage);

}
