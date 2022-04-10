package com.danielme.jakartaee.jpa.dao;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.ExpenseSummaryAssert;
import com.danielme.jakartaee.jpa.dto.CategorySummaryDTO;
import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.danielme.jakartaee.jpa.hibernate.CustomFunctionsMySQLMetadataBuilderContributor;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(ArquillianDBUnitExtension.class)
@Transactional
@DataSet(value = {"/datasets/categories.yml", "/datasets/expenses.yml", "/datasets/coupons.yml", "/datasets/budgets.yml"})
public class BaseDaoTest {

    private static final String ARTIFACT_NAME = "jpa-query.war";

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
        return ShrinkWrap.create(WebArchive.class, ARTIFACT_NAME)
                         .addPackages(true, Page.class.getPackage())
                         .addPackages(true, CategorySummaryDTO.class.getPackage())
                         .addPackages(true, Category.class.getPackage())
                         .addClass(ArquillianDBUnitExtension.class)
                         .addClass(CustomFunctionsMySQLMetadataBuilderContributor.class)
                         .addClass(ExpenseSummaryAssert.class)
                         .addClass(BaseDaoTest.class)
                         .addClass(Datasets.class)
                         .addAsWebInfResource(new File("src/test/resources/datasets/expenses.yml"), "classes/datasets/expenses.yml")
                         .addAsWebInfResource(new File("src/test/resources/datasets/coupons.yml"), "classes/datasets/coupons.yml")
                         .addAsWebInfResource(new File("src/test/resources/datasets/categories.yml"), "classes/datasets/categories.yml")
                         .addAsWebInfResource(new File("src/test/resources/datasets/budgets.yml"), "classes/datasets/budgets.yml")
                         .addAsWebInfResource(new File("src/test/resources/dbunit.yml"), "classes/dbunit.yml")
                         .addAsResource(new FileAsset(new File("src/main/resources/META-INF/persistence.xml")),
                                 "/META-INF/persistence.xml")
                         .addAsResource(new FileAsset(new File("src/main/resources/META-INF/orm.xml")),
                                 "/META-INF/orm.xml")
                         .addAsLibraries(testLibraries())
                         .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static File[] testLibraries() {
        return Maven.resolver()
                    .loadPomFromFile("pom.xml")
                    .resolve("org.assertj:assertj-core", "com.github.database-rider:rider-junit5")
                    .withTransitivity()
                    .asFile();
    }
}
