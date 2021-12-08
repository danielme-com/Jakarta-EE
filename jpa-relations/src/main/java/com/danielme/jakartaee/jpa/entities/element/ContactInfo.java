package com.danielme.jakartaee.jpa.entities.element;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "contact_info")
@Getter
@Setter
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String mainEmail;

    /*@ElementCollection
    @CollectionTable(name = "contact_additional_emails",
            joinColumns = @JoinColumn(name = "contact_info"))
    @Column(name = "email", nullable = false, unique = true)
    private List<String> additionalEmails;*/
    @ElementCollection
    @CollectionTable(name = "contact_additional_emails",
            joinColumns = @JoinColumn(name = "contact_info"))
    private List<AdditionalEmail> additionalEmails;

}
