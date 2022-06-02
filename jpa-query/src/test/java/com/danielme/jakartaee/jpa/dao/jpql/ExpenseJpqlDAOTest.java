package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ExpenseJpqlDAOTest extends BaseDaoTest {

    @Inject
    ExpenseJpqlDAO expenseJpqlDAO;

    @Test
    void testFindByCouponEmpty() {
        Optional<Expense> expense = expenseJpqlDAO.findByCouponId(Datasets.RANDOM_ID);

        assertThat(expense)
                .isNotPresent();
    }

    @Test
    void testFindByCoupon() {
        Optional<Expense> expense = expenseJpqlDAO.findByCouponId(Datasets.COUPON_ID_SUPER);

        assertThat(expense)
                .isPresent();
    }

    @Test
    void testFindByTextNull() {
        List<Expense> expenses = expenseJpqlDAO.findByText(null);

        assertThat(expenses)
                .hasSize(Datasets.TOTAL_EXPENSES.intValue());
    }

    @Test
    void testFindByTextNotNull() {
        List<Expense> expenses = expenseJpqlDAO.findByText("menu");

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_1);
    }

    @Test
    void testNoSumToday() {
        BigDecimal sum = expenseJpqlDAO.sumToday();

        assertThat(sum)
                .isNull();
    }

    @Test
    void testFindByCategoryIds() {
        List<Expense> expenses = expenseJpqlDAO.findByCategoryIds(
                List.of(Datasets.CATEGORY_ID_FOOD, Datasets.CATEGORY_ID_FUEL));

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_6, Datasets.EXPENSE_ID_1, Datasets.EXPENSE_ID_2);
    }

    @Test
    void testFindByDateRangeBothNull() {
        List<Expense> expenses = expenseJpqlDAO.findByDateRange(null, null);

        assertThat(expenses)
                .hasSize(Datasets.TOTAL_EXPENSES.intValue());
    }

    @Test
    void testFindByDateRangeBefore() {
        LocalDate to = LocalDate.of(2021, 7, 20);
        List<Expense> expenses = expenseJpqlDAO.findByDateRange(null, to);

        assertThat(expenses).extracting("id").containsExactly(Datasets.EXPENSE_ID_2,
                Datasets.EXPENSE_ID_1);
    }

    @Test
    void testFindByDateRangeAfter() {
        LocalDate from = LocalDate.of(2021, 8, 1);
        List<Expense> expenses = expenseJpqlDAO.findByDateRange(from, null);

        assertThat(expenses).extracting("id").containsExactly(Datasets.EXPENSE_ID_6,
                Datasets.EXPENSE_ID_5, Datasets.EXPENSE_ID_3, Datasets.EXPENSE_ID_4);
    }

    @Test
    void testFindByDateRangeInside() {
        LocalDate from = LocalDate.of(2021, 6, 5);
        LocalDate to = LocalDate.of(2021, 7, 20);
        List<Expense> expenses = expenseJpqlDAO.findByDateRange(from, to);

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
    }

    @Test
    void testFindAllPaginated() {
        int pageSize = 4;

        Page<Expense> page1 = expenseJpqlDAO.findAll(0, pageSize);
        Page<Expense> page2 = expenseJpqlDAO.findAll(4, pageSize);

        assertThat(page1.getResults())
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_6, Datasets.EXPENSE_ID_5,
                        Datasets.EXPENSE_ID_3, Datasets.EXPENSE_ID_4);
        assertThat(page1.getNumPages())
                .isEqualTo(2);
        assertThat(page1.getTotal())
                .isEqualTo(Datasets.TOTAL_EXPENSES);
        assertThat(page1.getNumber())
                .isEqualTo(1);
        assertThat(page1.hasNext())
                .isTrue();

        assertThat(page2.getResults())
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
        assertThat(page2.getNumber())
                .isEqualTo(2);
        assertThat(page2.hasNext())
                .isFalse();
    }

    @Test
    void testCloneExpenseForToday() {
        expenseJpqlDAO.cloneForToday(Datasets.EXPENSE_ID_1);

        Optional<Expense> expense = expenseJpqlDAO.findById(Datasets.EXPENSE_ID_1);

        assertThat(expense).isNotEmpty();
        assertThat(expenseJpqlDAO.sumToday())
                .isEqualTo(expense.get().getAmount());
    }

    @Test
    void testFindByYear() {
        Page<Expense> expenses = expenseJpqlDAO.findByYear(2021, 0, Integer.MAX_VALUE);

        assertThat(expenses.getTotal())
                .isEqualTo(Datasets.TOTAL_EXPENSES);
    }

    @Test
    void testWeekOfYearExpense() {
        Optional<Integer> week = expenseJpqlDAO.weekOfYearExpense(Datasets.EXPENSE_ID_1);

        assertThat(week)
                .contains(22);
    }

    @Test
    void testExpenseNotExists() {
       assertThat(expenseJpqlDAO.existsById(45L)).isFalse();
    }

}
