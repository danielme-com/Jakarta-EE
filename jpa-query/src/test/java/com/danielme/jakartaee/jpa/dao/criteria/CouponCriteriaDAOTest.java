package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.Datasets;
import com.danielme.jakartaee.jpa.dao.BaseDaoTest;
import com.danielme.jakartaee.jpa.dto.CouponSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Coupon_;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CouponCriteriaDAOTest extends BaseDaoTest {

    @Inject
    CouponCriteriaDAO couponCriteriaDAO;

    @Test
    void testGetSummary() {
        List<CouponSummaryDTO> summaries = couponCriteriaDAO.getSummary();

        assertThat(summaries)
                .extracting("id")
                .containsExactly(Datasets.COUPON_ID_ACME,
                        Datasets.COUPON_ID_SUPER);
    }

    @Test
    void testFindAllWithExpense() {
        List<Coupon> coupons = couponCriteriaDAO.findAllWithExpense();

        assertThat(coupons)
                .extracting(Coupon_.ID)
                .containsExactly(Datasets.COUPON_ID_ACME,
                        Datasets.COUPON_ID_SUPER);
    }

    @Test
    void testBulkUpdate() {
        int updated = couponCriteriaDAO.updateUsedPrefixBulk();
        Coupon coupon = couponCriteriaDAO.findById(Datasets.COUPON_ID_SUPER).get();

        assertThat(updated).isEqualTo(1);
        assertThat(coupon.getName())
                .startsWith(Coupon.USED_PREFIX);
    }

    @Test
    void testBulkDeleteExpired() {
        int deleted = couponCriteriaDAO.deleteExpired();

        assertThat(deleted).isEqualTo(1);
        assertThat(couponCriteriaDAO.findById(Datasets.COUPON_ID_ACME))
                .isEmpty();
    }

}
