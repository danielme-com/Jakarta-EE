package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseJpqlDAO extends GenericDAO<Expense, Long> {

    List<Expense> findAll();

    List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo);

    List<Expense> findByDateRangeV2(LocalDate dateFrom, LocalDate dateTo);

    List<Expense> findByText(String text);

    Optional<Expense> findByCouponId(Long couponId);

    Optional<Expense> findByCouponIdV2(Long couponId);

    BigDecimal sumToday();

    List<Expense> findByCategoryIds(List<Long> ids);

    Page<Expense> findAll(int first, int max);

    void cloneForToday(Long id);

    Page<Expense> findByYear(int year, int first, int max);

    Optional<Integer> weekOfYearExpense(Long expenseId);

    boolean existsById(Long expenseId);

}
