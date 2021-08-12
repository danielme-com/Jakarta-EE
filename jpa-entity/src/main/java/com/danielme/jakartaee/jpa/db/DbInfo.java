package com.danielme.jakartaee.jpa.db;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class DbInfo {

    @Resource(lookup = "java:/jdbc/personalBudgetDS")
    DataSource dataSource;

    public String getVersion() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT VERSION()");) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }
}
