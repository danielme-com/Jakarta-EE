package com.danielme.jakartaee.jpa.entities.multiple;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@SecondaryTable(name = "preferences",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id"))
@Getter
@Setter
public class UserAndPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(table = "preferences")
    private boolean enableNotifications;

    @Column(table = "preferences")
    private boolean enableSharing;

    //private PreferenceEmbeddable preferences;
}
