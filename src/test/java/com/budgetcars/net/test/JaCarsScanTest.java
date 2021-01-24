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
	
	@BeforeEach
	public void before() throws IOException {
		
		jaCarsScan = new JaCarsScan();
		
		jaCarsScan.setJsoup(mockJsoup);
		jaCarsScan.setMaxPageCount(1);
		
	    File file = new File("src/test/resources/jacars.html");
	    
	    InputStream targetStream = new FileInputStream(file);
		String jaCarsPageValue = IOUtils.toString(
				targetStream,
			      "UTF-8"
			    );
		Document jaCarsDocument = Jsoup.parse(jaCarsPageValue);
		Mockito.when(mockJsoup.connect(jaCarsUrl)).thenReturn(jaCarsDocument);
	}
	
	@Test
	void testThatScanAllMethodIsReturingTheCorrectAmountOfVehcles() {
		jaCarsScan.setUrl(jaCarsUrl);
		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.getMaxPageCount());
		assertEquals(vehicles.size(), 18);
	}
	
//	@Test
//	void testThatScanAllReturningCorrectYear() {
//		jaCarsScan.setUrl(jaCarsUrl);
//		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.maxPageCount);
//		Vehicle vehicle = vehicles.get(0);
//		assertEquals(vehicle.getYear(), "2013");
//
//	}
//	
//	@Test
//	void testThatScanAllReturningCorrectMake() {
//		jaCarsScan.setUrl(jaCarsUrl);
//		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.maxPageCount);
//		Vehicle vehicle = vehicles.get(0);
//		assertEquals(vehicle.getMake(), "Toyota");
//
//	}
//	
//	@Test
//	void testThatScanAllReturningCorrectModel() {
//		jaCarsScan.setUrl(jaCarsUrl);
//		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.maxPageCount);
//		Vehicle vehicle = vehicles.get(0);
//		assertEquals(vehicle.getModel(), "Ractis");
//	}
//	
//	@Test
//	void testThatScanAllReturningCorrectLink() {
//		jaCarsScan.setUrl(jaCarsUrl);
//		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.maxPageCount);
//		Vehicle vehicle = vehicles.get(0);
//		assertEquals(vehicle.getLink(), "https://www.autoadsja.com/J8JT");
//	}
//	
//	@Test
//	void testThatScanAllReturningCorrectPrice() {
//		jaCarsScan.setUrl(jaCarsUrl);
//		List<Vehicle> vehicles = jaCarsScan.scanAll(jaCarsScan.maxPageCount);
//		Vehicle vehicle = vehicles.get(0);
//		assertEquals(vehicle.getPrice(), 1320000.0);
//	}
}
