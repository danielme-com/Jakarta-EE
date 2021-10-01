package com.danielme.jakartaee.jpa.entities.inheritance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "devices")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device {

    @Id
    private Long id;

    private String manufacturer;

    private String name;

    private BigDecimal weight;
}
