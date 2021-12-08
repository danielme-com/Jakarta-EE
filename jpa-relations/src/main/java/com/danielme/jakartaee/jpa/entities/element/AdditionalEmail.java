package com.danielme.jakartaee.jpa.entities.element;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalEmail {

    @Column(nullable = false, unique = true)
    private String email;

    private boolean enabled = true;

}
