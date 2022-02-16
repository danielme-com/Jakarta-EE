package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.dto.CouponSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Coupon;

import java.util.List;

public interface CouponCriteriaDAO extends GenericDAO<Coupon, Long> {

    List<CouponSummaryDTO> getSummary();

    List<Coupon> findAllWithExpense();

    int updateUsedPrefixBulk();

    int deleteExpired();
}
