package com.danielme.jakartaee.jpa.dao;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public class GenericDAOImpl<T, K> implements GenericDAO<T, K> {

    private final EntityManager em;
    private final Class<T> entityClass;

    public GenericDAOImpl(EntityManager em, Class<T> entityClass) {
        this.em = em;
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
