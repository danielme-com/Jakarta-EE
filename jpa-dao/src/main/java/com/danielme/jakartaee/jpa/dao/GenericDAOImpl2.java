package com.danielme.jakartaee.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

public class GenericDAOImpl2<T, K> implements GenericDAO<T, K> {

    @PersistenceContext
    protected EntityManager em;
    protected final Class<T> entityClass;

    public GenericDAOImpl2(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public T save(T entity) {
        return em.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(entityClass, id));
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

}
