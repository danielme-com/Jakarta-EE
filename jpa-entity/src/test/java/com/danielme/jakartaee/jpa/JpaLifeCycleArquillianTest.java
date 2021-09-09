package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Language;
import com.danielme.jakartaee.jpa.extensions.ArquillianDBUnitExtension;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(ArquillianExtension.class)
@ExtendWith(ArquillianDBUnitExtension.class)
@Transactional
public class JpaLifeCycleArquillianTest {

    @PersistenceContext
    private EntityManager em;

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
        return Deployments.jpaLifecycle();
    }

    @Test
    void testGetReference() {
        Expense expenseReference = em.getReference(Expense.class, System.currentTimeMillis());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(expenseReference::getConcept);
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testRemoveReference() {
        Expense reference = em.getReference(Expense.class, Datasets.EXPENSE_ID);
        System.out.println("llamo al remove");
        em.remove(reference);
        em.flush();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testByMultipleIds() {
        Session session = em.unwrap(Session.class);
        List<Expense> expenses = session.byMultipleIds(Expense.class).multiLoad(Datasets.EXPENSE_ID,
                System.currentTimeMillis());
        assertThat(expenses).hasSize(2);
        assertThat(expenses.get(0)).isNotNull();
        assertThat(expenses.get(1)).isNull();
    }

    @Test
    void testPersist() {
        //transient
        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("9.99"));
        expense.setConcept("Lifecycle test");
        expense.setDate(LocalDate.now());

        //managed
        em.persist(expense);
        //expense = em.merge(expense);
        assertThat(expense.getId()).isNotNull();

        Expense expenseInContext = em.find(Expense.class, expense.getId());
        assertThat(expenseInContext).isEqualTo(expense);

        expense.setConcept("entity is managed");
        em.flush();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testDetach() {
        Expense expenseDetached = em.find(Expense.class, Datasets.EXPENSE_ID);
        expenseDetached.setConcept("detached!!");
        em.detach(expenseDetached);
        em.flush();

        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID);
        assertThat(expenseDetached).isNotEqualTo(expense);
        assertThat(expense.getConcept()).isEqualTo("Lunch menu");
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testRemove() {
        Expense expenseRemove = em.find(Expense.class, Datasets.EXPENSE_ID);
        em.remove(expenseRemove);
        assertThat(em.find(Expense.class, Datasets.EXPENSE_ID)).isNull();
        em.flush();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testMergeDetached() {
        Expense expenseDetached = em.find(Expense.class, Datasets.EXPENSE_ID);
        em.detach(expenseDetached);

        expenseDetached.setConcept("merged!!");
        Expense expenseManaged = em.merge(expenseDetached);
        assertThat(expenseManaged).isNotEqualTo(expenseDetached);
        assertThat(expenseManaged.getConcept()).isEqualTo(expenseManaged.getConcept());
        em.flush();
    }

    @Test
    @DataSet(value = "/datasets/expenses.yml")
    void testMerge() {
        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID);
        Expense expenseDetached = new Expense();
        expenseDetached.setId(Datasets.EXPENSE_ID);

        Expense expenseManaged = em.merge(expenseDetached);

        assertThat(expense).isEqualTo(expenseManaged);
    }

    @Test
    void testMergeNew() {
        Expense expenseNew = new Expense();
        expenseNew.setAmount(new BigDecimal("9.99"));
        expenseNew.setConcept("Lifecycle test");
        expenseNew.setDate(LocalDate.now());

        Expense expenseManaged = em.merge(expenseNew);

        assertThat(expenseManaged).isNotEqualTo(expenseNew);
    }

    @Test
    void testMergeNewWithId() {
        Language language = new Language();
        language.setId(System.currentTimeMillis());

        //check the log
        em.merge(language);
        em.flush();
    }

}