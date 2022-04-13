package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Category_;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CategoryCriteriaDAOTest extends BaseDaoTest {

    @Inject
    CategoryCriteriaDAO categoryCriteriaDAO;

    @Test
    void testFindCategoriesByMinExpenseAmount() {
        List<Category> categories = categoryCriteriaDAO.findCategoriesByMinExpenseAmount(new BigDecimal("100.00"));

        assertThat(categories)
                .extracting(Category_.ID)
                .containsExactly(Datasets.CATEGORY_ID_ENTERTAINMENT, Datasets.CATEGORY_ID_FOOD);
    }

    @Test
    void testFindAllCategoriesByBudgetMinAmount() {
        List<Category> categories = categoryCriteriaDAO.findAllCategoriesByBudgetMinAmount(new BigDecimal("300.00"));
        assertThat(categories)
                .extracting(Category_.ID)
                .containsExactly(Datasets.CATEGORY_ID_FOOD,
                        Datasets.CATEGORY_ID_FUEL);
    }

    @Test
    void testGetSummaryAverageAbove() {
        List<CategorySummaryDTO> summaries = categoryCriteriaDAO.getSummaryAverageAbove(new BigDecimal("50.00"));

        assertThat(summaries)
                .extracting("id", "expenses")
                .containsExactly(tuple(Datasets.CATEGORY_ID_FOOD, 2L),
                        tuple(Datasets.CATEGORY_ID_FUEL, 1L));
    }

}
