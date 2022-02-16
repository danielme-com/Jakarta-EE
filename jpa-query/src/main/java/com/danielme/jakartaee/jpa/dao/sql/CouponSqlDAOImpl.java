package com.danielme.jakartaee.jpa.dao.sql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.entities.Coupon;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CouponSqlDAOImpl extends GenericDAOImpl<Coupon, Long> implements CouponSqlDAO {

    public CouponSqlDAOImpl() {
        super(Coupon.class);
    }

    @Override
    public List<Coupon> findAll() {
        return em.createNativeQuery("SELECT * FROM coupons ORDER BY name", Coupon.class)
                .getResultList();
    }

    @Override
    public List<Coupon> findAllWithExpense() {
        List<Object[]> results = em.createNativeQuery("SELECT c.*, " +
                "e.id as exp_id, e.amount as exp_amount, e.comments, e.concept,e.date, e.category_id FROM coupons c " +
                "JOIN expenses e ON c.expense_id = e.id ORDER BY name", "CouponWithCategory")
                .getResultList();
        return results.stream().map(o -> (Coupon) o[0]).collect(Collectors.toList());
    }

    @Override
    public int updateUsedPrefixBulk() {
        return em.createNativeQuery("UPDATE coupons SET name = CONCAT(:prefix, name) " +
                "WHERE expense_id IS NOT NULL " +
                "AND SUBSTRING(name, 1, :length) NOT LIKE :prefix")
                .setParameter("length", Coupon.USED_PREFIX.length())
                .setParameter("prefix", Coupon.USED_PREFIX)
                .executeUpdate();
    }

}
