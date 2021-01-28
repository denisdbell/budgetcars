package com.bugdetcars.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budgetcars.net.repository.VehicleRepository;
import com.bugdetcars.net.model.Vehicle;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class Controller {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	
	@GetMapping("/query")
	public Page<Vehicle> query(@RequestParam String make,@RequestParam String  model,@RequestParam String year,int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Vehicle> exampleQuery = Example.of(new Vehicle( make,  model,  year), matcher);
		return vehicleRepository.findAll(exampleQuery, pageRequest);	
	}
}
