package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Expense;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(DBUnitExtension.class)
class ClientDbUnitArquillianTest {

    ConnectionHolder buildConnectionHolder() {
        return () -> DriverManager.getConnection("jdbc:mysql://localhost:3308/personal_budget" +
                        "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
                , "budget"
                , "budget");
    }

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.asClient();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testFindExpense() throws SQLException {
        try (Connection connection = buildConnectionHolder().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id from expenses where id = ?");) {
            ps.setLong(1, 1L);
            ResultSet resultSet = ps.executeQuery();

            assertTrue(resultSet.next());
        }
    }

}