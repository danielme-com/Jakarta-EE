package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.entities.Coupon;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CouponJpqlDAOTest extends BaseDaoTest {

    @Inject
    CouponJpqlDAO couponJpqlDAO;


    @Test
    void testUpdate() {
        int updated = couponJpqlDAO.updateUsedPrefixOneByOne();

        assertThat(updated).isEqualTo(1);
    }

    @Test
    void testBulkUpdate() {
        int updated = couponJpqlDAO.updateUsedPrefixBulk();

        assertThat(updated).isEqualTo(1);
    }

    @Test
    void testBulkUpdateBudgetAlreadyLoaded() {
        Coupon coupon = couponJpqlDAO.findById(Datasets.COUPON_ID_SUPER).get();
        assertThat(coupon.getName())
                .doesNotStartWith(Coupon.USED_PREFIX);

        couponJpqlDAO.updateUsedPrefixBulk();

        assertThat(coupon.getName())
                .doesNotStartWith(Coupon.USED_PREFIX);
    }

    @Test
    void testBulkUpdateBudgetNotLoaded() {
        couponJpqlDAO.updateUsedPrefixBulk();

        Coupon coupon = couponJpqlDAO.findById(Datasets.COUPON_ID_SUPER).get();
        assertThat(coupon.getName())
                .startsWith(Coupon.USED_PREFIX);
    }

    @Test
    void testBulkUpdateBudgetRefresh() {
        Coupon coupon = couponJpqlDAO.findById(Datasets.COUPON_ID_SUPER).get();
        assertThat(coupon.getName())
                .doesNotStartWith(Coupon.USED_PREFIX);

        couponJpqlDAO.updateUsedPrefixBulk();
        couponJpqlDAO.refresh(coupon);

        assertThat(coupon.getName())
                .startsWith(Coupon.USED_PREFIX);
    }

    @Test
    void testBulkDeleteExpired() {
        int deleted = couponJpqlDAO.deleteExpired();

        assertThat(deleted).isEqualTo(1);
        assertThat(couponJpqlDAO.findById(Datasets.COUPON_ID_ACME))
                .isEmpty();
    }

}
