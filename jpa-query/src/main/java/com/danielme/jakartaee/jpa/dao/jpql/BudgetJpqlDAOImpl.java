package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dao.Page;
import com.danielme.jakartaee.jpa.entities.Budget;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BudgetJpqlDAOImpl extends GenericDAOImpl<Budget, Long> implements BudgetJpqlDAO {

    public BudgetJpqlDAOImpl() {
        super(Budget.class);
    }

    @Override
    public Page<Budget> fetchWithCategoriesMemoryPagination(int first, int max) {
        List<Budget> budgets = em.createQuery("SELECT b FROM Budget b JOIN FETCH b.categories  " +
                        "ORDER BY b.name", Budget.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
        return new Page<>(budgets, count(), first, max);
    }

    @Override
    public Page<Budget> fetchWithCategoriesTwoQueries(int first, int max) {
        List<Long> budgetPageIds = em.createQuery("SELECT b.id FROM Budget b ORDER BY b.name",
                        Long.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();

        List<Budget> budgets = em.createQuery("SELECT DISTINCT b FROM Budget b JOIN FETCH b.categories " +
                        "WHERE b.id IN :ids ORDER BY b.name", Budget.class)
                .setParameter("ids", budgetPageIds)
                .getResultList();

        return new Page<>(budgets, count(), first, max);
    }

}
