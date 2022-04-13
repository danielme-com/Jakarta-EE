package com.danielme.jakartaee.jpa.dao.criteria;

import com.danielme.jakartaee.jpa.dao.GenericDAOImpl;
import com.danielme.jakartaee.jpa.dto.CouponSummaryDTO;
import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Coupon_;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Expense_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.*;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CouponCriteriaDAOImpl extends GenericDAOImpl<Coupon, Long> implements CouponCriteriaDAO {

    public CouponCriteriaDAOImpl() {
        super(Coupon.class);
    }

    @Override
    public List<CouponSummaryDTO> getSummary() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CouponSummaryDTO> cq = cb.createQuery(CouponSummaryDTO.class);
        Root<Coupon> couponRoot = cq.from(Coupon.class);

        Join<Coupon, Expense> expense = couponRoot.join(Coupon_.expense, JoinType.LEFT);

        cq.multiselect(
                couponRoot.get(Coupon_.id),
                couponRoot.get(Coupon_.name),
                expense.get(Expense_.concept),
                expense.get(Expense_.date))
          .orderBy(
                  cb.asc(couponRoot.get(Coupon_.name)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Coupon> findAllWithExpense() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq = cb.createQuery(Coupon.class);
        Root<Coupon> couponRoot = cq.from(Coupon.class);

        couponRoot.fetch(Coupon_.expense, JoinType.LEFT);
        cq.select(couponRoot);
        cq.orderBy(cb.asc(couponRoot.get(Coupon_.name)));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public int updateUsedPrefixBulk() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Coupon> cu = cb.createCriteriaUpdate(Coupon.class);
        Root<Coupon> couponRoot = cu.from(Coupon.class);

        cu.set(Coupon_.name,
                cb.concat(Coupon.USED_PREFIX, couponRoot.get(Coupon_.name)))
          .where(cb.and(
                  cb.isNotNull(couponRoot.get(Coupon_.expense)),
                  cb.notLike(
                          couponRoot.get(Coupon_.name),
                          cb.substring(couponRoot.get(Coupon_.name), 1, Coupon.USED_PREFIX.length()))));

        return em.createQuery(cu).executeUpdate();
    }

    @Override
    public int deleteExpired() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Coupon> cd = cb.createCriteriaDelete(Coupon.class);
        Root<Coupon> couponRoot = cd.from(Coupon.class);

        cd.where(cb.and(
                cb.isNull(couponRoot.get(Coupon_.expense)),
                cb.lessThan(
                        couponRoot.get(Coupon_.expiration).as(Date.class),
                        cb.currentDate())));

        return em.createQuery(cd).executeUpdate();
    }

}