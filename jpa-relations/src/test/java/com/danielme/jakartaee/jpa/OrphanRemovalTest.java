package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.cascade.Invoice;
import com.danielme.jakartaee.jpa.entities.cascade.InvoiceItem;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DataSet(value = {"/datasets/invoices.yml"})
public class OrphanRemovalTest extends BaseRelationsTest {

    @Test
    void testDelete() {
        Invoice invoice = em.find(Invoice.class, Datasets.INVOICE_ID);
        InvoiceItem invoiceItem = invoice.getItems().get(0);
        invoice.getItems().remove(invoiceItem);
        invoiceItem.setInvoice(null);

        em.flush();
        em.clear();

        assertThat(em.find(InvoiceItem.class, invoiceItem.getId()))
                .isNull();
        assertThat(em.find(Invoice.class, Datasets.INVOICE_ID).getItems().size())
                .isEqualTo(invoice.getItems().size());
    }

}
