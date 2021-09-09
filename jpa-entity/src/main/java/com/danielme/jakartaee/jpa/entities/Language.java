package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

@Entity
@Table(name = "languages")
@Setter
public class Language {
    @Id
    private Long id;

    @Column
    private String code;

}
