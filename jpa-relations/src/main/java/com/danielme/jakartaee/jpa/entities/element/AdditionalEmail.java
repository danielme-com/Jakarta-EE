package com.danielme.jakartaee.jpa.entities.element;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class AdditionalEmail {

    @Column(nullable = false, unique = true)
    private String email;
    private boolean enabled = true;

}
