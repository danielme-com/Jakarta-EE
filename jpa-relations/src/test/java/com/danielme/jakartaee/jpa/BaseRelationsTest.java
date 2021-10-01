package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.sql.Connection;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(ArquillianDBUnitExtension.class)
@Transactional
@DataSet(value = {"/datasets/categories.yml", "/datasets/expenses.yml",
        "/datasets/coupons.yml", "/datasets/budgets.yml",
        "/datasets/invoices.yml", "/datasets/users.yml"})
public class BaseRelationsTest {

    @PersistenceContext
    protected EntityManager em;

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

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.relations();
    }
}
