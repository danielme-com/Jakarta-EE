package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ExpenseJpqlDAOImpl extends GenericDAOImpl<Expense, Long> implements ExpenseJpqlDAO {

    public ExpenseJpqlDAOImpl() {
        super(Expense.class);
    }

    @Override
    public List<Expense> findAll() {
        return em.createQuery(
                "SELECT e " +
                        "FROM Expense e " +
                        "ORDER BY " +
                        "       e.date DESC, " +
                        "       e.concept",
                Expense.class)
                .getResultList();
    }

    @Override
    public List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo) {
        return em.createQuery("SELECT e FROM Expense e " +
                        "WHERE (?1 IS NULL OR e.date >= ?1) AND (?2 IS NULL OR e.date <= ?2) ORDER BY e.date DESC, e.amount DESC",
                Expense.class)
                .setParameter(1, dateFrom)
                .setParameter(2, dateTo)
                .getResultList();
    }

    @Override
    public List<Expense> findByDateRangeV2(LocalDate dateFrom, LocalDate dateTo) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM Expense e  WHERE 1 = 1 ");
        if (dateFrom != null) {
            jpql.append(" AND e.date >= ?1 ");
        }
        if (dateTo != null) {
            jpql.append(" AND e.date <= ?2 ");
        }
        jpql.append("ORDER BY e.date DESC, e.amount DESC");

        TypedQuery<Expense> query = em.createQuery(jpql.toString(), Expense.class);
        if (dateFrom != null) {
            query.setParameter(1, dateFrom);
        }
        if (dateTo != null) {
            query.setParameter(2, dateTo);
        }
        return query.getResultList();
    }

    @Override
    public List<Expense> findByText(String text) {
        return em.createQuery("SELECT e FROM Expense e WHERE " +
                "UPPER(e.concept) LIKE UPPER (:text) " +
                "OR UPPER(e.comments) LIKE UPPER(:text)", Expense.class)
                .setParameter("text", text == null ? "%" : "%" + text + "%")
                .getResultList();
    }

    @Override
    public Optional<Expense> findByCouponId(Long couponId) {
        try {
            Expense expense = em.createQuery("SELECT c.expense FROM Coupon c WHERE c.id =:id",
                    Expense.class)
                    .setParameter("id", couponId)
                    .getSingleResult();
            return Optional.of(expense);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Expense> findByCouponIdV2(Long couponId) {
        return em.createQuery("SELECT c.expense FROM Coupon c WHERE c.id =:id",
                Expense.class)
                .setParameter("id", couponId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public BigDecimal sumToday() {
        return em.createQuery("SELECT SUM(e.amount) FROM Expense e WHERE e.date = CURRENT_DATE",
                BigDecimal.class)
                .getSingleResult();
    }

    @Override
    public List<Expense> findByCategoryIds(List<Long> ids) {
        return em.createQuery("SELECT e FROM Expense e WHERE e.category.id IN :ids ORDER BY e.concept",
                Expense.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public Page<Expense> findByYear(int year, int first, int max) {
        Long count = em.createQuery("SELECT count(e) FROM Expense e WHERE EXTRACT(YEAR FROM e.date) = :year", Long.class)
                .setParameter("year", year)
                .getSingleResult();
        List<Expense> expenses = em.createQuery("SELECT e FROM Expense e WHERE EXTRACT(YEAR FROM e.date) = :year ORDER BY e.date",
                Expense.class)
                .setParameter("year", year)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
        return new Page<>(expenses, count, first, max);
    }

    @Override
    public Optional<Integer> weekOfYearExpense(Long expenseId) {
        return em.createQuery("SELECT WEEK(e.date) FROM Expense e WHERE e.id = :expenseId", Integer.class)
                .setParameter("expenseId", expenseId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public boolean existsById(Long expenseId) {
        return em.createQuery("SELECT " +
                "   CASE " +
                "      WHEN (COUNT(e) > 0)  THEN true " +
                "      ELSE false " +
                "END FROM Expense e " +
                "WHERE e.id = :expenseId", Boolean.class)
                .setParameter("expenseId", expenseId)
                .getSingleResult();
    }

    @Override
    public Page<Expense> findAll(int first, int max) {
        List<Expense> expenses = em.createQuery("SELECT e FROM Expense e " +
                "ORDER BY e.date DESC, e.concept, e.amount", Expense.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
        return new Page<>(expenses, count(), first, max);
    }

    @Override
    public void cloneForToday(Long id) {
        em.createQuery("INSERT INTO Expense(amount, concept, date, category) " +
                "    SELECT e.amount, e.concept, :date, e.category " +
                "    FROM Expense e " +
                "    WHERE e.id = :id")
                .setParameter("date", LocalDate.now())
                .setParameter("id", id)
                .executeUpdate();
    }


}
