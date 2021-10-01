package com.danielme.jakartaee.jpa.entities.lob;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pictures")
@Getter
@Setter
public class Picture {

    @Id
    private Long id;

    @Lob
    private byte[] picture;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private UserWithPicture user;

}
