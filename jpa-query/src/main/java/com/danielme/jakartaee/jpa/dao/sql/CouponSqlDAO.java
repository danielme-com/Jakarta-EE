package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.dao.GenericDAO;
import com.danielme.jakartaee.jpa.entities.Coupon;

import java.util.List;

public interface CouponSqlDAO extends GenericDAO<Coupon, Long> {

    List<Coupon> findAll();

    List<Coupon> findAllWithExpense();

    int updateUsedPrefixBulk();
}
