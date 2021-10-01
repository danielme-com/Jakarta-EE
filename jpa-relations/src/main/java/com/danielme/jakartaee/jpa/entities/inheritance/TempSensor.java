package com.danielme.jakartaee.jpa.entities.inheritance;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "temp_sensors")
public class TempSensor extends Device {

    private BigDecimal minTemp;

    private BigDecimal maxTemp;
}
