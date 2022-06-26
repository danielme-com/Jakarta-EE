package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dto.ExpenseSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Expense;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.Tuple;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExpenseSqlDAOImpl extends GenericDAOImpl<Expense, Long> implements ExpenseSqlDAO {

    public ExpenseSqlDAOImpl() {
        super(Expense.class);
    }

    @Override
    public BigDecimal sum() {
        return (BigDecimal) em.createNativeQuery("SELECT SUM(amount) FROM expenses").getSingleResult();
    }

    @Override
    public List<Expense> findAllNativePagination(int first, int max) {
        return em.createNativeQuery("SELECT * FROM expenses ORDER BY concept LIMIT :first, :max", Expense.class)
                .setParameter("first", first)
                .setParameter("max", max)
                .getResultList();
    }

    @Override
    public List<Expense> findAllStandardPagination(int first, int max) {
        return em.createNativeQuery("SELECT * FROM expenses ORDER BY concept", Expense.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<Expense> findByDateRange(LocalDate dateFrom, LocalDate dateTo) {
        return em.createNativeQuery("SELECT * FROM expenses WHERE (:dateFrom IS NULL OR date >= :dateFrom) " +
                        "AND (:dateTo IS NULL OR date <= :dateTo) ORDER BY date DESC, amount DESC",
                Expense.class)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();
    }

    @Override
    public List<Expense> findByText(String text) {
        return em.createNamedQuery("Expense.findByText", Expense.class)
                .setParameter("text", text == null ? "%" : "%" + text + "%")
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryNamedQuery() {
        return em.createNamedQuery("Expense.Summary", ExpenseSummaryDTO.class)
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryRaw() {
        List<Object[]> resultList = em.createNativeQuery("SELECT concept, amount, date FROM expenses ORDER BY amount desc, date desc").getResultList();
        return resultList.stream()
                .map(o -> new ExpenseSummaryDTO(
                        (String) o[0],
                        (BigDecimal) o[1],
                        ((java.sql.Date) o[2]).toLocalDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryTuple() {
        List<Tuple> resultList = em.createNativeQuery("SELECT * FROM expenses ORDER BY amount desc, date desc",
                Tuple.class).getResultList();
        return resultList.stream()
                .map(t -> new ExpenseSummaryDTO(
                        t.get("concept", String.class),
                        t.get("amount", BigDecimal.class),
                        t.get("date", java.sql.Date.class).toLocalDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryConstructor() {
        return em.createNativeQuery("SELECT concept, amount, date " +
                "FROM expenses ORDER BY amount desc, date desc", "ExpenseSummaryMapping")
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryCustomResultTransformer() {
        return em.createNativeQuery("SELECT concept, amount, date " +
                " FROM expenses ORDER BY amount desc, date desc")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ExpenseSummaryTransformer())
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryAliasResultTransformer() {
        return em.createNativeQuery("SELECT concept, amount, date FROM expenses ORDER BY amount desc, date desc")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(ExpenseSummaryDTO.class))
                .getResultList();
    }

    @Override
    public List<ExpenseSummaryDTO> getSummaryConstructorResultTransformer() {
        Constructor<ExpenseSummaryDTO> constructor;
        try {
            constructor = ExpenseSummaryDTO.class.getConstructor(String.class, BigDecimal.class, java.sql.Date.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("constructor for " + ExpenseSummaryDTO.class + " not found !!!");
        }
        return em.createNativeQuery("SELECT concept, amount, date FROM expenses ORDER BY amount desc, date desc")
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanConstructorResultTransformer(constructor))
                .getResultList();
    }

    @Override
    public long countExpensesWithProcedure(String concept) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_count_expenses")
                .registerStoredProcedureParameter("concept", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("total", Long.class, ParameterMode.OUT);

        //enables null values for parameters
        //((org.hibernate.procedure.ParameterRegistration) sp.getParameter("concept")).enablePassingNulls(true);

        sp.setParameter("concept", concept);

        sp.execute();
        return (Long) sp.getOutputParameterValue("total");
    }

    @Override
    public long countExpensesWithNamedProcedure(String concept) {
        StoredProcedureQuery sp = em.createNamedStoredProcedureQuery("ExpenseSpCountExpenses")
                .setParameter("concept", concept);
        sp.execute();
        return (Long) sp.getOutputParameterValue("total");
    }

    @Override
    public List<Expense> findCheapExpensesWithProcedure(BigDecimal maxAmount) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_cheap_expenses", Expense.class)
                .registerStoredProcedureParameter("maxAmount", BigDecimal.class, ParameterMode.IN)
                .setParameter("maxAmount", maxAmount);
        //use only for databases that supports REF_CURSOR OUT parameters like PostgreSQL
        //sp.registerStoredProcedureParameter("expenses", void.class, ParameterMode.REF_CURSOR);
        return (List<Expense>) sp.getResultList();
    }

    @Override
    public List<Expense> findCheapExpensesWithNamedProcedure(BigDecimal maxAmount) {
        StoredProcedureQuery sp = em.createNamedStoredProcedureQuery("ExpenseSpCheapExpenses")
                .setParameter("maxAmount", maxAmount);
        return (List<Expense>) sp.getResultList();
    }

    private static class ExpenseSummaryTransformer implements ResultTransformer {

        private static final long serialVersionUID = 1L;

        @Override
        public Object transformTuple(Object[] tuple, String[] aliases) {
            return new ExpenseSummaryDTO(
                    (String) tuple[0],
                    (BigDecimal) tuple[1],
                    ((java.sql.Date) tuple[2]).toLocalDate());
        }

        @Override
        public List transformList(List collection) {
            return collection;
        }

    }

}
