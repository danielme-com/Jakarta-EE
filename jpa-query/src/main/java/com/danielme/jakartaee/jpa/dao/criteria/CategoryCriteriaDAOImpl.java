package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CategoryCriteriaDAOImpl extends GenericDAOImpl<Category, Long> implements CategoryCriteriaDAO {

    public CategoryCriteriaDAOImpl() {
        super(Category.class);
    }

    @Override
    public List<Category> findCategoriesByMinExpenseAmount(BigDecimal minAmount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> categoryRoot = cq.from(Category.class);

        ListJoin<Category, Expense> expenses = categoryRoot.join(Category_.expenses);

        cq.select(categoryRoot)
          .where(cb.ge(expenses.get(Expense_.amount), cb.parameter(BigDecimal.class, "minAmount")))
          .orderBy(cb.asc(categoryRoot.get(Category_.name)));

        return em.createQuery(cq)
                 .setParameter("minAmount", minAmount)
                 .getResultList();
    }

    @Override
    public List<Category> findAllCategoriesByBudgetMinAmount(BigDecimal minAmount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> categoryRoot = cq.from(Category.class);

        Subquery<Integer> sqSub = cq.subquery(Integer.class);
        Root<Budget> sqRoot = sqSub.from(Budget.class);
        ListJoin<Budget, Category> join = sqRoot.join(Budget_.categories);

        sqSub.select(cb.literal(1))
             .where(
                     cb.equal(join.get(Category_.ID),
                             categoryRoot.get(Category_.ID)),
                     cb.ge(sqRoot.get(Budget_.amount),
                             cb.parameter(BigDecimal.class, "minAmount"))
             );

        cq.select(categoryRoot)
          .where(cb.exists(sqSub))
          .orderBy(cb.asc(categoryRoot.get(Category_.name)));

        return em.createQuery(cq)
                 .setParameter("minAmount", minAmount)
                 .getResultList();
    }

    @Override
    public List<CategorySummaryDTO> getSummaryAverageAbove(BigDecimal maxAverage) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategorySummaryDTO> cq = cb.createQuery(CategorySummaryDTO.class);
        Root<Expense> expenseRoot = cq.from(Expense.class);

        Path<Category> categoryPath = expenseRoot.get(Expense_.category);
        Path<String> categoryNamePath = categoryPath.get(Category_.name);

        cq.multiselect(
                categoryPath.get(Category_.id),
                categoryNamePath,
                cb.sum(expenseRoot.get(Expense_.amount)),
                cb.count(expenseRoot))
          .groupBy(categoryNamePath)
          .having(cb.greaterThanOrEqualTo(
                  cb.avg(expenseRoot.get(Expense_.amount)).as(BigDecimal.class),
                  maxAverage))
          .orderBy(cb.asc(categoryNamePath));

        return em.createQuery(cq)
                 .getResultList();
    }


}
