package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.ExpenseSummaryAssert;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dto.ExpenseDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseWeekDTO;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Expense_;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExpenseCriteriaDAOTest extends BaseDaoTest {

    @Inject
    ExpenseCriteriaDao expenseCriteriaDAO;

    @Test
    void testFindAll() {
        List<Expense> expenses = expenseCriteriaDAO.findAll(0, 6).getResults();

        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_6, Datasets.EXPENSE_ID_5,
                        Datasets.EXPENSE_ID_3, Datasets.EXPENSE_ID_4,
                        Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
    }

    @Test
    void testGetConcepts() {
        List<String> concepts = expenseCriteriaDAO.getConcepts();

        assertThat(concepts)
                .containsExactly("Full tank", "HBO",
                        "Lunch menu", "movies",
                        "Netflix", "vegetables");
    }

    @Test
    void testFindByCategory() {
        List<Expense> expenses = expenseCriteriaDAO.findByCategory(Datasets.CATEGORY_ID_FUEL);

        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_6);
    }

    @Test
    void testGetSummaryRaw() {
        List<ExpenseSummaryDTO> summaries = expenseCriteriaDAO.getSummaryRaw();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryTuple() {
        List<ExpenseSummaryDTO> summaries = expenseCriteriaDAO.getSummaryTuple();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryConstructor() {
        List<ExpenseSummaryDTO> summaries = expenseCriteriaDAO.getSummaryConstructor();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testFindAllByMaxFixed() {
        List<Expense> expenses = expenseCriteriaDAO.findAllByMax();

        assertExpensesByMax(expenses);
    }

    @Test
    void testFindAllByMaxParam() {
        List<Expense> expenses = expenseCriteriaDAO.findAllByMax(new BigDecimal("50.00"));

        assertExpensesByMax(expenses);
    }

    @Test
    void testFindByDateRangeAfter() {
        LocalDate from = LocalDate.of(2021, 10, 10);
        List<Expense> expenses = expenseCriteriaDAO.findByDateRange(from, null);

        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_6, Datasets.EXPENSE_ID_5);
    }


    @Test
    void testFindAboveAverage() {
        List<Expense> expenses = expenseCriteriaDAO.findAboveAverage();

        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_1,
                        Datasets.EXPENSE_ID_3, Datasets.EXPENSE_ID_6);
    }

    @Test
    void testFindByCategories() {
        List<Expense> expenses = expenseCriteriaDAO.findByCategories(List.of(Datasets.COUPON_ID_ACME));

        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
    }

    @Test
    void testFindAllWithType() {
        List<ExpenseDTO> expenseDTOS = expenseCriteriaDAO.findAllWithType();

        assertThat(expenseDTOS)
                .extracting("type")
                .containsExactly(
                        ExpenseDTO.ExpenseType.EXPENSIVE,
                        ExpenseDTO.ExpenseType.SMALL,
                        ExpenseDTO.ExpenseType.EXPENSIVE,
                        ExpenseDTO.ExpenseType.SMALL,
                        ExpenseDTO.ExpenseType.SMALL,
                        ExpenseDTO.ExpenseType.EXPENSIVE);
    }

    @Test
    void testGetSummaryWeek() {
        List<ExpenseWeekDTO> summaries = expenseCriteriaDAO.getSummaryWithWeek();

        assertThat(summaries)
                .extracting("week")
                .containsExactly(41, 40, 30, 30, 29, 22);
    }

    private void assertExpensesByMax(List<Expense> expenses) {
        assertThat(expenses)
                .extracting(Expense_.ID)
                .containsExactly(Datasets.EXPENSE_ID_4, Datasets.EXPENSE_ID_5,
                        Datasets.EXPENSE_ID_2);
    }

}
