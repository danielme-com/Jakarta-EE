package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Coupon_;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponSqlDAOTest extends BaseDaoTest {

    @Inject
    CouponSqlDAO couponSqlDAO;


    @Test
    void findAll() {
        List<Coupon> coupons = couponSqlDAO.findAll();

        assertThat(coupons)
                .extracting(Coupon_.ID)
                .containsExactly(Datasets.COUPON_ID_ACME, Datasets.COUPON_ID_SUPER);
    }

    @Test
    void findAllWithExpense() {
        List<Coupon> coupons = couponSqlDAO.findAllWithExpense();
        em.clear(); //detach entities, so lazy loading fails

        assertThat(coupons)
                .extracting(Coupon_.ID)
                .containsExactly(Datasets.COUPON_ID_SUPER);
        assertThat(coupons.get(0).getExpense().getConcept()).isEqualTo("Lunch menu");
    }

    @Test
    void testBulkUpdate() {
        int updated = couponSqlDAO.updateUsedPrefixBulk();
        Coupon coupon = couponSqlDAO.findById(Datasets.COUPON_ID_SUPER).get();

        assertThat(updated).isEqualTo(1);
        assertThat(coupon.getName())
                .startsWith(Coupon.USED_PREFIX);
    }

}
