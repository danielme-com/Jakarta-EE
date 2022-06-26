package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryJpqlDAOImpl extends GenericDAOImpl<Category, Long> implements CategoryJpqlDAO {

    public CategoryJpqlDAOImpl() {
        super(Category.class);
    }


    @Override
    public List<CategorySummaryDTO> getSummaryRaw() {
        return em.createNamedQuery("Category.summary", Object[].class)
                .getResultStream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategorySummaryDTO> getSummaryTuple() {
        return em.createNamedQuery("Category.summary", Tuple.class)
                .getResultStream()
                .map(this::mapIndex)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public List<CategorySummaryDTO> getSummaryConstructor() {
        return em.createQuery("SELECT NEW " + CategorySummaryDTO.class.getName() +
                "(c.id, c.name , SUM (e.amount) , COUNT(e))  " +
                "FROM Category c LEFT JOIN c.expenses e  " +
                "GROUP BY c.id ORDER BY c.name", CategorySummaryDTO.class)
                .getResultList();
    }

    @Override
    public List<CategorySummaryDTO> getSummaryResultTransformer() {
        return em.unwrap(Session.class)
                .createNamedQuery("Category.summary")
                .setResultTransformer(new CategorySummaryTransformer())
                .getResultList();
    }

    @Override
    public List<CategorySummaryDTO> getSummaryAliasResultTransformer() {
        return em.unwrap(Session.class)
                .createNamedQuery("Category.summary")
                .setResultTransformer(new AliasToBeanResultTransformer(CategorySummaryDTO.class))
                .getResultList();
    }

    @Override
    public List<CategorySummaryDTO> getSummaryConstructorResultTransformer() {
        Constructor<CategorySummaryDTO> constructor;
        try {
            constructor = CategorySummaryDTO.class.getConstructor(Long.class, String.class, BigDecimal.class, Long.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("constructor for " + CategorySummaryDTO.class + " not found !!!");
        }
        return em.unwrap(Session.class)
                .createNamedQuery("Category.summary")
                .setResultTransformer(new AliasToBeanConstructorResultTransformer(constructor))
                .getResultList();
    }

    @Override
    public List<Category> findByName(String name) {
        return em.createNamedQuery("Category.findByName", Category.class)
                .setParameter("name", name == null ? "%" : "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Category> findAll() {
        return em.createNamedQuery("Category.findAll", Category.class)
                .getResultList();
    }

    @Override
    public List<Category> findAllAsReadOnly() {
        return em.createQuery("SELECT c FROM Category c", Category.class)
                .setHint(QueryHints.READ_ONLY, true)
                .getResultList();
    }

    @Override
    public Category findByIdWithRelations(Long id) {
        return em.createQuery("SELECT c FROM Category c " +
                "JOIN FETCH c.budgets JOIN FETCH c.expenses WHERE c.id=:id", Category.class)
                .setParameter("id", id)
                .getResultList()
                .get(0);
    }

    private CategorySummaryDTO map(Object[] columns) {
        return new CategorySummaryDTO(
                (Long) columns[0],
                (String) columns[1],
                (BigDecimal) columns[2],
                (Long) columns[3]);
    }

    private CategorySummaryDTO mapAlias(Tuple tuple) {
        return new CategorySummaryDTO(
                tuple.get("id", Long.class),
                tuple.get("name", String.class),
                tuple.get("total", BigDecimal.class),
                tuple.get("expenses", Long.class));
    }

    private CategorySummaryDTO mapIndex(Tuple tuple) {
        return new CategorySummaryDTO(
                tuple.get(0, Long.class),
                tuple.get(1, String.class),
                tuple.get(2, BigDecimal.class),
                tuple.get(3, Long.class));
    }

    private class CategorySummaryTransformer implements ResultTransformer {

        private static final long serialVersionUID = 1L;

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {
            return CategoryJpqlDAOImpl.this.map(tuple);
        }

        @Override
        public List transformList(List collection) {
            return collection;
        }

    }

}
