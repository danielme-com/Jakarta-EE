package com.danielme.jakartaee.jpa.dao.jpql;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.entities.Coupon;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CouponJpqlDAOImpl extends GenericDAOImpl<Coupon, Long> implements CouponJpqlDAO {

    public CouponJpqlDAOImpl() {
        super(Coupon.class);
    }

    @Override
    public int updateUsedPrefixOneByOne() {
        List<Coupon> coupons = em.createQuery("SELECT c FROM Coupon c WHERE c.expense IS NOT NULL " +
                "AND SUBSTRING(c.name, 1, :length) NOT LIKE :prefix", Coupon.class)
                                 .setParameter("length", Coupon.USED_PREFIX.length())
                                 .setParameter("prefix", Coupon.USED_PREFIX)
                                 .getResultList();
        coupons.forEach(c -> c.setName(Coupon.USED_PREFIX + c.getName()));
        return coupons.size();
    }

    @Override
    public int updateUsedPrefixBulk() {
        return em.createQuery("UPDATE Coupon c SET c.name = CONCAT(:prefix, c.name) WHERE c.expense IS NOT NULL " +
                "AND SUBSTRING(c.name, 1, :length) NOT LIKE :prefix")
                 .setParameter("length", Coupon.USED_PREFIX.length())
                 .setParameter("prefix", Coupon.USED_PREFIX)
                 .executeUpdate();
    }

    @Override
    public int deleteExpired() {
        return em.createQuery("DELETE FROM Coupon c WHERE c.expiration < CURRENT_DATE AND c.expense IS NULL")
                 .executeUpdate();
    }

}
