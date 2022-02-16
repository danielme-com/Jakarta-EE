package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;

import java.util.List;

public interface CategoryJpqlDAO extends GenericDAO<Category, Long> {

    List<CategorySummaryDTO> getSummaryRaw();

    List<CategorySummaryDTO> getSummaryTuple();

    List<CategorySummaryDTO> getSummaryConstructor();

    List<CategorySummaryDTO> getSummaryResultTransformer();

    List<CategorySummaryDTO> getSummaryAliasResultTransformer();

    List<CategorySummaryDTO> getSummaryConstructorResultTransformer();

    List<Category> findByName(String name);

    List<Category> findAll();

    List<Category> findAllAsReadOnly();

    Category findByIdWithRelations(Long id);
}
