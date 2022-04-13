package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Budget;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BudgetJpqlDAOTest extends BaseDaoTest {

    @Inject
    BudgetJpqlDAO budgetJpqlDAO;

    @Test
    void testFetchWithCategories() {
        Page<Budget> page = budgetJpqlDAO.fetchWithCategoriesMemoryPagination(0, 2);

        assertFetchPagination(page);
    }

    @Test
    void testFetchWithCategoriesTwoQueries() {
        Page<Budget> page = budgetJpqlDAO.fetchWithCategoriesTwoQueries(0, 2);

        assertFetchPagination(page);
    }

    private void assertFetchPagination(Page<Budget> page) {
        assertThat(page.getNumPages())
                .isEqualTo(2);
        assertThat(page.getResults())
                .extracting("id")
                .containsExactly(Datasets.BUDGET_AUGUST, Datasets.BUDGET_JULY);
        assertThat(page.getResults().get(0).getCategories())
                .hasSize(2);
    }

}
