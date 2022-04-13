package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CategoryJpqlDAOTest extends BaseDaoTest {

    @Inject
    CategoryJpqlDAO categoryJpqlDAO;


    @Test
    void testGetSummaryRaw() {
        assertSummaries(categoryJpqlDAO.getSummaryRaw());
    }

    @Test
    void testSummaryTuple() {
        assertSummaries(categoryJpqlDAO.getSummaryTuple());
    }

    @Test
    void testSummaryConstructor() {
        assertSummaries(categoryJpqlDAO.getSummaryConstructor());
    }

    @Test
    void testSummaryResultTransformer() {
        assertSummaries(categoryJpqlDAO.getSummaryResultTransformer());
    }

    @Test
    void testSummaryConstructorResultTransformer() {
        assertSummaries(categoryJpqlDAO.getSummaryConstructorResultTransformer());
    }

    @Test
    void testSummaryAliasResultTransformer() {
        assertSummaries(categoryJpqlDAO.getSummaryAliasResultTransformer());
    }

    @Test
    void testFindByName() {
        List<Category> categories = categoryJpqlDAO.findByName("Fuel");

        assertThat(categories)
                .extracting("id")
                .containsExactly(Datasets.CATEGORY_ID_FUEL);
    }

    @Test
    void testFindByIdWithRelations() {
        assertThatThrownBy(() -> categoryJpqlDAO.findByIdWithRelations(Datasets.CATEGORY_ID_ENTERTAINMENT))
                .hasStackTraceContaining("MultipleBagFetchException");
    }

    @Test
    void testFindAll() {
        List<Category> categories = categoryJpqlDAO.findAll();

        assertThat(categories)
                .extracting("id")
                .containsExactly(Datasets.CATEGORY_ID_ENTERTAINMENT,
                        Datasets.CATEGORY_ID_FOOD,
                        Datasets.CATEGORY_ID_FUEL,
                        Datasets.CATEGORY_ID_PET);
    }

    @Test
    void testFindAllReadOnly() {
        Category categoryReadOnly = categoryJpqlDAO.findAllAsReadOnly().get(0);

        categoryReadOnly.setName("new name");
        em.flush();

        em.detach(categoryReadOnly);
        Category categoryDb = categoryJpqlDAO.findById(categoryReadOnly.getId()).get();
        assertThat(categoryDb.getName())
                .isNotEqualTo(categoryReadOnly.getName());
    }

    @Test
    void testDynamicNamedQuery() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.id = :id");
        em.getEntityManagerFactory().addNamedQuery("Category.findById", query);

        @SuppressWarnings("JpaQueryApiInspection")
        Category category = em.createNamedQuery("Category.findById", Category.class)
                              .setParameter("id", Datasets.CATEGORY_ID_FOOD)
                              .getSingleResult();

        assertThat(category.getId())
                .isEqualTo(Datasets.CATEGORY_ID_FOOD);
    }

    private void assertSummaries(List<CategorySummaryDTO> summaries) {
        assertThat(summaries)
                .extracting("id", "expenses")
                .containsExactly(tuple(Datasets.CATEGORY_ID_ENTERTAINMENT, 3L),
                        tuple(Datasets.CATEGORY_ID_FOOD, 2L),
                        tuple(Datasets.CATEGORY_ID_FUEL, 1L),
                        tuple(Datasets.CATEGORY_ID_PET, 0L));
    }

}
