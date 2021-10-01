package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Preferences;
import com.danielme.jakartaee.jpa.entities.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneToOneTest extends BaseRelationsTest {

    @Test
    void testUpdateCouponUnidirectional() {
        Coupon coupon = em.find(Coupon.class, Datasets.COUPON_ID_ACME);
        coupon.setExpense(em.getReference(Expense.class, Datasets.EXPENSE_ID_2));

        em.flush();
        em.detach(coupon);

        assertThat(em.find(Coupon.class, Datasets.COUPON_ID_ACME).getExpense())
                .isNotNull();
    }

    @Test
    void testUpdateExpenseBidirectional() {
        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID_2);
        expense.setCoupon(em.find(Coupon.class, Datasets.COUPON_ID_ACME));
        expense.getCoupon().setExpense(expense);

        em.flush();
        em.detach(expense);

        assertThat(em.find(Expense.class, Datasets.EXPENSE_ID_2).getCoupon())
                .isNotNull();
    }

    @Test
    void testCreatePreference() {
        User user = new User();
        user.setName("test");
        em.persist(user);
        Preferences preferences = new Preferences();
        preferences.setUser(user);

        em.persist(preferences);
        em.flush();
        em.clear();

        assertThat(em.find(Preferences.class, user.getId())).isNotNull();
    }


}
