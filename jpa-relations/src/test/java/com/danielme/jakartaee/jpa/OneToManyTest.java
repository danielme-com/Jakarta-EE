package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Category;
import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.User;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OneToManyTest extends BaseRelationsTest {

    @Test
    void testBidirectional() {
        Category category = em.find(Category.class, Datasets.CATEGORY_ID_FOOD);
        //expenses lazy loading
        assertThat(category.getExpenses()).hasSize(2);
    }

    @Test
    @DataSet(executeStatementsBefore = {"TRUNCATE users_coupons"})
    void testUnidirectional() {
        Coupon coupon = new Coupon();
        coupon.setAmount(new BigDecimal("24"));
        coupon.setName("test");
        User user = new User();
        user.setName("test coupon");
        user.setCoupons(List.of(coupon));

        em.persist(user);
        em.flush();
        em.detach(user);

        User userInserted = em.find(User.class, user.getId());
        assertThat(userInserted.getCoupons()).hasSize(1);

        em.remove(userInserted);
        em.flush();
    }

    @Test
    void testFetchCategoryExpenses() {
        List<Expense> expenses = em.createQuery("SELECT e From Expense e WHERE e.category.id = :id " +
                "ORDER BY e.date DESC", Expense.class)
                                   .setParameter("id", Datasets.CATEGORY_ID_FOOD)
                                   .setFirstResult(0)
                                   .setMaxResults(10)
                                   .getResultList();

        assertThat(expenses)
                .extracting("id")
                .containsExactly(Datasets.EXPENSE_ID_2, Datasets.EXPENSE_ID_1);
    }

}
