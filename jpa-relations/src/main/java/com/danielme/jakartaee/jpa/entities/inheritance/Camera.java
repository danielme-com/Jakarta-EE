package com.danielme.jakartaee.jpa.entities.inheritance;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cameras")
public class Camera extends Device {

    private BigDecimal resolution;

    private Integer minIso;

    private Integer maxIso;

}
