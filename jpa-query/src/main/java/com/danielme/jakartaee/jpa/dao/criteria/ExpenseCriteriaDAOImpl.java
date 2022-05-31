package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.dto.ExpenseDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.dto.ExpenseWeekDTO;
import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Category_;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Expense_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpenseCriteriaDAOImpl extends GenericDAOImpl<Expense, Long> implements ExpenseCriteriaDao {

    public ExpenseCriteriaDAOImpl() {
        super(Expense.class);
    }

    @Override
    public Page<Expense> findAll(int first, int max) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot)
                .orderBy(
                        cb.desc(expenseRoot.get("date")),
                        cb.asc(expenseRoot.get("concept")),
                        cb.asc(expenseRoot.get("amount")));

        List<Expense> expenses = em.createQuery(cq)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
        return new Page<>(expenses, count(), first, max);
    }

    @Override
    public List<String> getConcepts() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot.get(Expense_.concept));
        cq.orderBy(cb.asc(expenseRoot.get(Expense_.concept)));

        return em.createQuery(cq)
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryRaw() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.multiselect(
                expenseRoot.get(Expense_.concept),
                expenseRoot.get(Expense_.amount),
                expenseRoot.get(Expense_.date))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.amount)),
                        cb.desc(expenseRoot.get(Expense_.date)));

        return em.createQuery(cq)
                .getResultStream()
                .map(o -> new ExpenseSummaryDTO(
                        (String) o[0],
                        (BigDecimal) o[1],
                        (LocalDate) o[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryTuple() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.multiselect(
                expenseRoot.get(Expense_.concept).alias(Expense_.CONCEPT),
                expenseRoot.get(Expense_.amount).alias(Expense_.AMOUNT),
                expenseRoot.get(Expense_.date).alias(Expense_.DATE))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.amount)),
                        cb.desc(expenseRoot.get(Expense_.date)));

        return em.createQuery(cq)
                .getResultStream()
                .map(t -> new ExpenseSummaryDTO(
                        t.get(Expense_.CONCEPT, Expense_.concept.getJavaType()),
                        t.get(Expense_.AMOUNT, Expense_.amount.getJavaType()),
                        t.get(Expense_.DATE, Expense_.date.getJavaType())))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryConstructor() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExpenseSummaryDTO> cq = cb.createQuery(ExpenseSummaryDTO.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.multiselect(
                expenseRoot.get(Expense_.concept),
                expenseRoot.get(Expense_.amount),
                expenseRoot.get(Expense_.date))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.amount)),
                        cb.desc(expenseRoot.get(Expense_.date)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Expense> findAllByMax() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot)
                .where(cb.le(
                        expenseRoot.get(Expense_.amount), new BigDecimal("50.00")))
                .orderBy(cb.desc(expenseRoot.get(Expense_.amount)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Expense> findAllByMax(BigDecimal maxAmount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot)
                .where(cb.lessThanOrEqualTo(expenseRoot.get(Expense_.amount),
                        cb.parameter(BigDecimal.class, "amount")))
                .orderBy(cb.desc(expenseRoot.get(Expense_.amount)));

        return em.createQuery(cq)
                .setParameter("amount", maxAmount)
                .getResultList();
    }

    @Override
    public List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot);
        Predicate conjunction = cb.conjunction();
        if (dateFrom != null) {
            conjunction.getExpressions().add(cb.greaterThanOrEqualTo(expenseRoot.get(Expense_.date),
                    cb.parameter(LocalDate.class, "dateFrom")));
        }
        if (dateTo != null) {
            conjunction.getExpressions().add(cb.lessThanOrEqualTo(expenseRoot.get(Expense_.date),
                    cb.parameter(LocalDate.class, "dateTo")));
        }
        if (!conjunction.getExpressions().isEmpty()) {
            cq.where(conjunction);
        }
        cq.orderBy(cb.desc(expenseRoot.get(Expense_.date)), cb.asc(expenseRoot.get(Expense_.amount)));

        TypedQuery<Expense> query = em.createQuery(cq);
        if (dateFrom != null) {
            query.setParameter("dateFrom", dateFrom);
        }
        if (dateTo != null) {
            query.setParameter("dateTo", dateTo);
        }

        return query.getResultList();
    }

    @Override
    public List<Expense> findAboveAverage() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);

        Subquery<Double> sqAvg = cq.subquery(Double.class);
        Root<Expense> sqRoot = sqAvg.from(Expense.class);
        sqAvg.select(cb.avg(sqRoot.get(Expense_.amount)));

        Root<Expense> expenseRoot = cq.from(Expense.class);
        cq.select(expenseRoot)
                .where(cb.greaterThanOrEqualTo(
                        expenseRoot.get(Expense_.amount).as(Double.class), sqAvg))
                .orderBy(cb.desc(expenseRoot.get(Expense_.amount)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Expense> findByCategories(List<Long> ids) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.select(expenseRoot)
                .where(
                        expenseRoot.get(Expense_.category).get(Category_.id)
                                .in(ids))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.date)),
                        cb.asc(expenseRoot.get(Expense_.concept)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Expense> findByCategory(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);
        //INNER JOIN as CROSS JOIN
        Root<Category> categoryRoot = cq.from(Category.class);

        cq.select(expenseRoot)
                .where(
                        cb.equal(categoryRoot.get(Category_.id), expenseRoot.get(Expense_.category).get(Category_.id)),
                        cb.equal(categoryRoot.get(Category_.id), id)
                )
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.date)),
                        cb.asc(expenseRoot.get(Expense_.concept)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<ExpenseWeekDTO> getSummaryWithWeek() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExpenseWeekDTO> cq = cb.createQuery(ExpenseWeekDTO.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.multiselect(
                expenseRoot.get(Expense_.concept),
                expenseRoot.get(Expense_.amount),
                expenseRoot.get(Expense_.date),
                cb.function("WEEKOFYEAR", Integer.class, expenseRoot.get(Expense_.date)))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.date)),
                        cb.asc(expenseRoot.get(Expense_.amount)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<ExpenseDTO> findAllWithType() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExpenseDTO> cq = cb.createQuery(ExpenseDTO.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        cq.multiselect(
                expenseRoot.get(Expense_.id),
                expenseRoot.get(Expense_.concept),
                expenseRoot.get(Expense_.category).get(Category_.name),
                expenseRoot.get(Expense_.amount),
                expenseRoot.get(Expense_.date),
                expenseRoot.get(Expense_.comments),
                cb.selectCase()
                        .when(cb.ge(expenseRoot.get(Expense_.amount), 50),
                                ExpenseDTO.ExpenseType.EXPENSIVE.name())
                        .when(cb.ge(expenseRoot.get(Expense_.amount), 20),
                                ExpenseDTO.ExpenseType.STANDARD.name())
                        .otherwise(ExpenseDTO.ExpenseType.SMALL.name()))
                .orderBy(
                        cb.desc(expenseRoot.get(Expense_.date)),
                        cb.asc(expenseRoot.get(Expense_.concept)));

        return em.createQuery(cq).getResultList();
    }

}
