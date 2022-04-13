package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.entities.Budget;
import com.danielme.jakartaee.jpa.entities.Budget_;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BudgetCriteriaDAOTest extends BaseDaoTest {

    @Inject
    BudgetCriteriaDAO budgetCriteriaDAO;

    @Test
    void testFindOpen() {
        LocalDate minDate = LocalDate.of(2021, 8, 31);
        LocalDate maxDate = LocalDate.of(2021, 11, 20);
        List<Budget> budgets = budgetCriteriaDAO
                .findAvailableByDateAndAmount(new BigDecimal("200.00"), minDate, maxDate);

        assertThat(budgets)
                .extracting(Budget_.ID)
                .containsExactly(Datasets.BUDGET_AUGUST,
                        Datasets.BUDGET_VIDEOGAMES_2021);
    }

}
