package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.dao.ExpenseDAO;
import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(ArquillianDBUnitExtension.class)
@DataSet(value = {"/datasets/categories.yml", "/datasets/expenses.yml"})
public class ExpenseDAOTest {

    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    private DataSource dataSource;

    private Connection connection;

    ConnectionHolder buildConnectionHolder() {
        return () -> {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
            return connection;
        };
    }

    private static final Long EXPENSE_ID_1 = 1L;
    private static final Long CATEGORY_ID_FOOD = 1L;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.deployment();
    }

    @Inject
    @Named("expenseDaoGeneric")
    GenericDAO<Expense, Long> expenseGenericDAO;
    @Inject
    GenericDAO<Category, Long> categoryDAO;
    @Inject
    ExpenseDAO expenseDAO;


    @Test
    void testExpenseGenericFindById() {
        assertThat(expenseGenericDAO.findById(EXPENSE_ID_1)).isPresent();
    }

    @Test
    void testCategoryGenericFindById() {
        assertThat(categoryDAO.findById(CATEGORY_ID_FOOD)).isPresent();
    }

    @Test
    void testCountByCategory() {
        long count = expenseDAO.countByCategoryId(CATEGORY_ID_FOOD);

        assertThat(count).isEqualTo(2);
    }

}
