package com.bugdetcars.net.model;

import java.util.Date;
import lombok.Data;

@Data
public class Vehicle {
	String make; 
	String model;
	String link;
	Double price;
	Date year;
}
