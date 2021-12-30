package com.danielme.jakartaee.jpa.dao;

import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

class DaoProducer {

    @PersistenceContext
    private EntityManager em;

    @Produces
    @ApplicationScoped
    @Named("expenseDaoGeneric")
    private GenericDAO<Expense, Long> produceExpenseDAO() {
        return new GenericDAOImpl<>(em, Expense.class);
    }

    @Produces
    @ApplicationScoped
    private GenericDAO<Category, Long> produceCategoryDAO() {
        return new GenericDAOImpl<>(em, Category.class);
    }

}
