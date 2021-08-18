package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CityPK implements Serializable {

    private String name;

    @Column(name = "reg_code", length = 3)
    private String regionCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityPK cityPK = (CityPK) o;
        return name.equals(cityPK.name) && regionCode.equals(cityPK.regionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regionCode);
    }

}
