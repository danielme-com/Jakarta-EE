package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.entities.Budget;
import com.danielme.jakartaee.jpa.entities.Budget_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BudgetCriteriaDAOImpl extends GenericDAOImpl<Budget, Long> implements BudgetCriteriaDAO {

    public BudgetCriteriaDAOImpl() {
        super(Budget.class);
    }

    @Override
    public List<Budget> findAvailableByDateAndAmount(BigDecimal minAmount, LocalDate minDate, LocalDate maxDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Budget> cq = cb.createQuery(Budget.class);
        Root<Budget> budgetRoot = cq.from(Budget.class);

        /*Predicate disjunction = cb.disjunction();
        disjunction.getExpressions().add(cb.isNull(budgetRoot.get(Budget_.dateTo)));
        disjunction.getExpressions().add(cb.greaterThanOrEqualTo(budgetRoot.get(Budget_.dateTo),
                cb.parameter(LocalDate.class, "minDate")));

        Predicate conjunction = cb.conjunction();
        conjunction.getExpressions().add(cb.greaterThanOrEqualTo(budgetRoot.get(Budget_.amount),
                cb.parameter(BigDecimal.class, "minAmount")));
        conjunction.getExpressions().add(cb.lessThanOrEqualTo(budgetRoot.get(Budget_.dateFrom),
                cb.parameter(LocalDate.class, "maxDate")));

        conjunction.getExpressions().add(disjunction);

        cq.select(budgetRoot)
                .where(conjunction)
                .orderBy(cb.asc(budgetRoot.get(Budget_.name)));*/

        cq.select(budgetRoot)
          .where(
                  cb.ge(budgetRoot.get(Budget_.amount),
                          cb.parameter(BigDecimal.class, "minAmount")),
                  cb.lessThanOrEqualTo(budgetRoot.get(Budget_.dateFrom),
                          cb.parameter(LocalDate.class, "maxDate")),
                  cb.or(
                          cb.isNull(budgetRoot.get(Budget_.dateTo)),
                          cb.greaterThanOrEqualTo(budgetRoot.get(Budget_.dateTo),
                                  cb.parameter(LocalDate.class, "minDate"))))
          .orderBy(cb.asc(budgetRoot.get(Budget_.name)));

        return em.createQuery(cq)
                .setParameter("minAmount", minAmount)
                .setParameter("minDate", minDate)
                .setParameter("maxDate", maxDate)
                .getResultList();
    }

}
