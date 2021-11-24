package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.*;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class JpaArquillianTest {

    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    private Connection connection;

    ConnectionHolder buildConnectionHolder() {
        return () -> {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
            return connection;
        };
    }

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.jpa();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testFindExpense() {
        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID);

        assertThat(expense).isNotNull();
    }

    @Test
    @DataSet(value = "/datasets/users.yml")
    void testBooleanConverter() {
        assertThat(em.find(User.class, Datasets.USER_ID_1).getPreferences().getNotifications())
                .isTrue();
        assertThat(em.find(User.class, Datasets.USER_ID_2).getPreferences().getNotifications())
                .isFalse();
        assertThat(em.find(User.class, Datasets.USER_ID_3).getPreferences())
                .isNull();
    }

    @Test
    @DataSet(value = "/datasets/geometries.yml")
    void testShapeConverter() {
        assertThat(em.find(Geometry.class, Datasets.SHAPE_CIRCLE_ID).getShape())
                .isEqualTo(Geometry.Shape.CIRCLE);
        assertThat(em.find(Geometry.class, Datasets.SHAPE_RECTANGLE_ID).getShape())
                .isEqualTo(Geometry.Shape.RECTANGLE);
        assertThat(em.find(Geometry.class, Datasets.SHAPE_TRIANGLE_ID).getShape())
                .isEqualTo(Geometry.Shape.TRIANGLE);
    }

    @Test
    @DataSet(value = "/datasets/cities.yml")
    void testEmbeddableId() {
        CityPK cityPK = new CityPK(Datasets.CITY_NAME, Datasets.CITY_CODE);

        City city = em.find(City.class, cityPK);

        assertThat(city).isNotNull();
        assertThat(city.getCityPk()).isEqualTo(cityPK);
    }

}