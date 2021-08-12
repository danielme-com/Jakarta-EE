package com.danielme.jakartaee.jpa.entities;

import com.danielme.jakartaee.jpa.converters.BooleanAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Embeddable
@Getter
@Setter
public class Preferences {

    //@Convert(converter = BooleanAttributeConverter.class)
    @Column(columnDefinition = "CHAR(1)")
    private Boolean notifications;

    @OneToOne
    @JoinColumn(name = "lan_id")
    private Language language;

}
