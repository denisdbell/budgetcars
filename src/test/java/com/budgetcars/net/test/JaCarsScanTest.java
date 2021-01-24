package com.budgetcars.net.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.budgetcars.net.repository.VehicleRepository;
import com.budgetcars.net.wrapper.JsoupWrapper;
import com.bugdetcars.net.Application;
import com.bugdetcars.net.model.Vehicle;
import com.bugdetcars.net.scan.JaCarsScan;

import lombok.extern.log4j.Log4j2;


@SpringBootTest(classes = Application.class)
@Log4j2
class JaCarsScanTest {
	
	JaCarsScan jaCarsScan;
	String jaCarsUrl = "http://jacars.com";
	String autoAdsPageValue = null;
	JsoupWrapper mockJsoup = Mockito.mock(JsoupWrapper.class);
	VehicleRepository vehicleRepository = Mockito.mock(VehicleRepository.class);
	
	@BeforeEach
	public void before() throws IOException {
		
		jaCarsScan = new JaCarsScan();
		
		jaCarsScan.setJsoup(mockJsoup);
		jaCarsScan.setMaxPageCount(1);
		jaCarsScan.setVehicleRepository(vehicleRepository);
		
	    File file = new File("src/test/resources/jacars.html");
	    
	    InputStream targetStream = new FileInputStream(file);
		String jaCarsPageValue = IOUtils.toString(
				targetStream,
			      "UTF-8"
			    );
		log.info(jaCarsPageValue);
		Document jaCarsDocument = Jsoup.parse(jaCarsPageValue);
		Mockito.when(mockJsoup.connect(jaCarsUrl)).thenReturn(jaCarsDocument);
	}
	
	@Test
	void testThatScanAllMethodIsReturingTheCorrectAmountOfVehcles() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		assertEquals(vehicles.size(), 67);
	}
	
	@Test
	void testThatScanAllReturningCorrectYear() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getYear(), "2015");

	}
	
	@Test
	void testThatScanAllReturningCorrectMake() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getMake(), "Toyota");

	}
	
	@Test
	void testThatScanAllReturningCorrectModel() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getModel(), "Mark");
	}
	
	@Test
	void testThatScanAllReturningCorrectLink() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getLink(), "https://www.jacars.net/adv/2273483_toyota-mark-x-2-5l-2015/");
	}
	
	@Test
	void testThatScanAllReturningCorrectPrice() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getPrice(), 2680000.0);
	}
}
