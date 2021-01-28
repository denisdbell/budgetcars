package com.bugdetcars.net.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
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

	public Vehicle(String make, String model, String year) {
		super();
		
		 make = StringUtils.isEmpty(make)? null : make;
	     model = StringUtils.isEmpty(model)? null : model;
	     year = StringUtils.isEmpty(year)? null : year;

	    log.info(make + " " + model + " " + year);

		this.make = make;
		this.model = model;
		this.year = year;
		this.price = null;
		this.id = null;
		this.link=null;
	}
	
	
}
