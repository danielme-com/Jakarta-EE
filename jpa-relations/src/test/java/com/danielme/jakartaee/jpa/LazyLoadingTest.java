package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.Coupon;
import com.danielme.jakartaee.jpa.entities.Expense;
import com.danielme.jakartaee.jpa.entities.Preferences;
import com.danielme.jakartaee.jpa.entities.cascade.Invoice;
import com.danielme.jakartaee.jpa.entities.cascade.InvoiceItem;
import jakarta.persistence.PersistenceUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@Slf4j
public class LazyLoadingTest extends BaseRelationsTest {

    @Test
    void testLazyOneToMany() {
        Invoice invoice = em.find(Invoice.class, Datasets.INVOICE_ID);

        PersistenceUnitUtil pUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        assertThat(pUnitUtil.isLoaded(invoice, "items")).isFalse();
        log.info("loading...");
        assertThat(invoice.getItems()).hasSize(3);
        assertThat(pUnitUtil.isLoaded(invoice, "items")).isTrue();
    }

    @Test
    void testLazyManyToOne() {
        InvoiceItem invoiceItem = em.find(InvoiceItem.class, Datasets.INVOICE_ID_ITEM_1);

        log.info("loading...");

        //Hibernate.initialize(invoiceItem.getInvoice());
        invoiceItem.getInvoice().getDateTime();

        assertThat(invoiceItem.getInvoice()).isInstanceOf(HibernateProxy.class);
    }

    @Test
    void testUnproxyHibernate() {
        InvoiceItem invoiceItem = em.find(InvoiceItem.class, Datasets.INVOICE_ID_ITEM_1);

        assertThat(invoiceItem.getInvoice()).isInstanceOf(HibernateProxy.class);
        Invoice invoiceUnproxied = (Invoice) Hibernate.unproxy(invoiceItem.getInvoice());
        assertThat(invoiceUnproxied).isNotInstanceOf(HibernateProxy.class);
    }

    @Test
    void testLazyInitializationException() {
        Invoice invoice = em.find(Invoice.class, Datasets.INVOICE_ID);
        em.detach(invoice);

        assertThatExceptionOfType(LazyInitializationException.class)
                .isThrownBy(() -> invoice.getItems().size());
    }

    @Test
    void testOneToOneUnidirectionalLazy() {
        Preferences preferences = em.find(Preferences.class, Datasets.USER_ID);

        assertThat(preferences.getUser()).isInstanceOf(HibernateProxy.class);
    }

    @Test
    void testOneToOneBidirectional() {
        Coupon coupon = em.find(Coupon.class, Datasets.COUPON_ID_SUPER);
        assertThat(coupon.getExpense()).isInstanceOf(HibernateProxy.class);

        Expense expense = em.find(Expense.class, Datasets.EXPENSE_ID_1);
        assertThat(expense.getCoupon()).isNotInstanceOf(HibernateProxy.class);
    }

}
