package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.UserNaturalId;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
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
class HibernateArquillianTest {

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
        return Deployments.hibernate();
    }

    @Test
    @DataSet(value = "/datasets/users_naturalid.yml")
    void testNaturalId() {
        UserNaturalId userId = em.find(UserNaturalId.class, 1L);
        UserNaturalId userNaturalId = em.unwrap(Session.class)
                .bySimpleNaturalId(UserNaturalId.class)
                .load(234);

        assertThat(userId.getId()).isEqualTo(userNaturalId.getId());
    }

}