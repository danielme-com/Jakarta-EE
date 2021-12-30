package com.danielme.jakartaee.jpa.dao;

import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpenseDAOImpl extends GenericDAOImpl2<Expense, Long> implements ExpenseDAO {

    public ExpenseDAOImpl() {
        super(Expense.class);
    }

    @Override
    public long countByCategoryId(Long categoryId) {
        return em.createQuery("SELECT count(e) FROM Expense e WHERE e.category.id=:categoryId", Long.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
    }

}