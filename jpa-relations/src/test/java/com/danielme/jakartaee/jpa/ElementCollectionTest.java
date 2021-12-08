package com.danielme.jakartaee.jpa;

import com.danielme.jakartaee.jpa.entities.element.AdditionalEmail;
import com.danielme.jakartaee.jpa.entities.element.ContactInfo;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;

@DataSet(value = "/datasets/contacts.yml")
public class ElementCollectionTest extends BaseRelationsTest {

    @Test
    void testAddEmail() {
        ContactInfo contactInfo = em.find(ContactInfo.class, Datasets.CONTACT_INFO_ID_1);

        contactInfo.getAdditionalEmails().add(new AdditionalEmail("mail4", false));
        em.flush();
    }

}
