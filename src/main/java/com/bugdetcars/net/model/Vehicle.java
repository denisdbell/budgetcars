package com.bugdetcars.net.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Vehicle {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "make")
	String make;
	@Column(name = "model")

	String model;
	@Column(name = "link")

	String link;
	@Column(name = "price")

	Double price;
	@Column(name = "year")

	String year;

	public String value() {
		return make + model + link + price + year;
	}
}
