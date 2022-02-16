package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.entities.Coupon;

public interface CouponJpqlDAO extends GenericDAO<Coupon, Long> {

    int updateUsedPrefixBulk();

    int updateUsedPrefixOneByOne();

    int deleteExpired();
}
