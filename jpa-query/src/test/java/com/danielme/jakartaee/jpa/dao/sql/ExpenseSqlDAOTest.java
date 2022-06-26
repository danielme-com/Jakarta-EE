package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.ExpenseSummaryAssert;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseSqlDAOTest extends BaseDaoTest {

    @Inject
    ExpenseSqlDAO expenseSqlDAO;

    @Test
    void testSum() {
        BigDecimal total = expenseSqlDAO.sum();

        assertThat(total.toString()).isEqualTo("299.97");
    }

    @Test
    void testFindAllStandard() {
        List<Expense> expenses = expenseSqlDAO.findAllStandardPagination(0, 2);

        assertPage(expenses);
    }

    @Test
    void testFindAllNativePagination() {
        List<Expense> expenses = expenseSqlDAO.findAllNativePagination(0, 2);

        assertPage(expenses);
    }

    @Test
    void testGetSummaryRaw() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryRaw();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryNamedQuery() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryNamedQuery();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryTuple() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryTuple();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryConstructor() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryConstructor();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryCustomResultTransformer() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryCustomResultTransformer();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryAliasResultTransformer() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryAliasResultTransformer();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetSummaryConstructorResultTransformer() {
        List<ExpenseSummaryDTO> summaries = expenseSqlDAO.getSummaryConstructorResultTransformer();

        ExpenseSummaryAssert.assertSummary(summaries);
    }

    @Test
    void testGetFindByText() {
        List<Expense> expenses = expenseSqlDAO.findByText("menu");

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_1);
    }

    @Test
    void testGetFindByDateRange() {
        LocalDate from = LocalDate.of(2021, 6, 5);
        LocalDate to = LocalDate.of(2021, 7, 20);
        List<Expense> expenses = expenseSqlDAO.findByDateRange(from, to);

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
    }

    @Test
    void testCountExpensesWithProcedure() {
        long count = expenseSqlDAO.countExpensesWithProcedure("menu");

        assertThat(count).isEqualTo(1L);
    }

    @Test
    void testCountExpensesWithNamedProcedure() {
        long count = expenseSqlDAO.countExpensesWithNamedProcedure("menu");

        assertThat(count).isEqualTo(1L);
    }

    @Test
    void testFindCheapExpensesWithProcedure() {
        List<Expense> expenses = expenseSqlDAO.findCheapExpensesWithProcedure(new BigDecimal("5.0"));

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_5, Datasets.EXPENSE_ID_2);
    }

    @Test
    void testFindCheapExpensesWithNamedProcedure() {
        List<Expense> expenses = expenseSqlDAO.findCheapExpensesWithNamedProcedure(new BigDecimal("5.0"));

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_5, Datasets.EXPENSE_ID_2);
    }

    private void assertPage(List<Expense> expenses) {
        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_6, Datasets.EXPENSE_ID_5);
    }

}
