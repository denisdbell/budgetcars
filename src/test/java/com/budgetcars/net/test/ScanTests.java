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
import com.bugdetcars.net.scan.Scan;


@SpringBootTest(classes = Application.class)
class ScanTests {
	
	Scan autoAdScan;
	String autoAdsUrl = "http://autoads.com";
	String autoAdsPageValue = null;
	JsoupWrapper mockJsoup = Mockito.mock(JsoupWrapper.class);
	
	@BeforeEach
	public void before() throws IOException {
		
		autoAdScan = new Scan();
		
		autoAdScan.setJsoup(mockJsoup);
		autoAdScan.setMaxPageCount(1);
		
	    File file = new File("src/test/resources/autoads.html");
	    
	    InputStream targetStream = new FileInputStream(file);
		String autoAdsPageValue = IOUtils.toString(
				targetStream,
			      "UTF-8"
			    );
		System.out.println("file " + file);
		System.out.println("autoAdsPageValue " + autoAdsPageValue);
		Document autoAdsdocument = Jsoup.parse(autoAdsPageValue);
		Mockito.when(mockJsoup.connect(autoAdsUrl)).thenReturn(autoAdsdocument);
	}
	
	@Test
	void testThatScanAllMethodIsReturingTheCorrectAmountOfVehcles() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		assertEquals(vehicles.size(), 8);
	}
	
	@Test
	void testThatScanAllReturningCorrectYear() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getYear(), "2013");

	}
	
	@Test
	void testThatScanAllReturningCorrectMake() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getMake(), "Toyota");

	}
	
	@Test
	void testThatScanAllReturningCorrectModel() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getModel(), "Ractis");
	}
	
	@Test
	void testThatScanAllReturningCorrectLink() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getLink(), "https://www.autoadsja.com/J8JT");
	}
	
	@Test
	void testThatScanAllReturningCorrectPrice() {
		autoAdScan.setUrl(autoAdsUrl);
		List<Vehicle> vehicles = autoAdScan.scanAll(autoAdScan.maxPageCount);
		Vehicle vehicle = vehicles.get(0);
		assertEquals(vehicle.getPrice(), 1320000.0);
	}
}
