package com.danielme.jakartaee.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "preferences")
@Getter
@Setter
public class Preferences {

	@Id
	private Long id;

	private boolean enableNotifications;
	private boolean enableSharing;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private User user;

}
