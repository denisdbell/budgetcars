package com.bugdetcars.net.model;

import lombok.Data;

@Data
public class Vehicle {
	String make; 
	String model;
	String link;
	Double price;
	String year;
	
	public String value() {
		return make+model+link+price+year;
	}
}
