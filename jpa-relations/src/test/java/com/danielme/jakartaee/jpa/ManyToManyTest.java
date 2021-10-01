package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Budget;
import com.danielme.jakartaee.jpa.entities.Category;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ManyToManyTest extends BaseRelationsTest {

    @Test
    void testFind() {
        Budget budget = em.find(Budget.class, Datasets.BUDGET_ID_AUGUST);

        assertThat(budget.getCategories()).hasSize(2);
    }

    @Test
    void testPersist() {
        Budget budget = new Budget();
        budget.setAmount(new BigDecimal("200"));
        budget.setDateFrom(LocalDate.now());
        budget.setName("test");
        Category categoryFuel = em.find(Category.class, Datasets.CATEGORY_ID_FUEL);
        Category categoryFood = em.find(Category.class, Datasets.CATEGORY_ID_FOOD);
        budget.setCategories(List.of(categoryFuel, categoryFood));
        categoryFuel.getBudgets().add(budget);
        categoryFood.getBudgets().add(budget);

        em.persist(budget);
        em.flush();
        em.detach(budget);

        assertThat(em.find(Budget.class, budget.getId()).getCategories()).hasSize(2);
    }

    @Test
    void testDeleteBudget() {
        Budget budget = em.find(Budget.class, Datasets.BUDGET_ID_AUGUST);

        em.remove(budget);
        em.flush();

        assertThat(em.find(Category.class, Datasets.CATEGORY_ID_FOOD)).isNotNull();
    }

    @Test
    void testDeleteBudgetCategory() {
        Budget budget = em.find(Budget.class, Datasets.BUDGET_ID_AUGUST);
        Category category = em.find(Category.class, Datasets.CATEGORY_ID_FOOD);
        budget.getCategories().remove(category);
        category.getBudgets().remove(budget);

        em.flush();
    }

}
