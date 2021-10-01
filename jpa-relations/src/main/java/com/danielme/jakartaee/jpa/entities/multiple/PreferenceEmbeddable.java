package com.danielme.jakartaee.jpa.entities.multiple;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PreferenceEmbeddable {

    @Column(table = "preferences")
    private boolean enableNotifications;

    @Column(table = "preferences")
    private boolean enableSharing;

}
