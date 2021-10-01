package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ManyToOneTest extends BaseRelationsTest {

    @Test
    void testFind() {
        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID_1);

        assertThat(expense.getCategory().getId())
                .isEqualTo(Datasets.CATEGORY_ID_FOOD);
    }

    @Test
    void testPersistNoCategory() {
        Expense expense = createExpense();

        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> em.persist(expense));
    }

    @Test
    void testPersistGetReference() {
        Expense expense = createExpense();
        expense.setCategory(em.getReference(Category.class, Datasets.CATEGORY_ID_FUEL));

        em.persist(expense);
    }

    @Test
    void testPersistCreateCategory() {
        Expense expense = createExpense();
        Category category = new Category();
        category.setId(Datasets.CATEGORY_ID_FOOD);
        expense.setCategory(category);

        em.persist(expense);
    }

    @Test
    void testUpdateCategory() {
        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID_1);
        expense.setCategory(em.getReference(Category.class, Datasets.CATEGORY_ID_FUEL));

        em.flush();
        em.detach(expense);

        Category currentCategory = em.find(Expense.class, Datasets.EXPENSE_ID_1).getCategory();
        assertThat(currentCategory.getId())
                .isEqualTo(Datasets.CATEGORY_ID_FUEL);
    }

    private Expense createExpense() {
        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("9.99"));
        expense.setConcept("test");
        expense.setDate(LocalDate.now());
        return expense;
    }

}
