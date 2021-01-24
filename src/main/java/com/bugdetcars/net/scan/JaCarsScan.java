package com.bugdetcars.net.scan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budgetcars.net.repository.VehicleRepository;
import com.bugdetcars.net.model.Vehicle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JaCarsScan extends GenericScan  {
	
	
	
	public JaCarsScan() {
		 this.setUrl("https://www.jacars.net/cars/?page=%d");
	}
	
	public List<Vehicle> scan(String url) {

		HashMap<String,Vehicle> vehiclesHash = new LinkedHashMap<String,Vehicle>();

		try {
			log.info("Hello");
			this.setDocument(this.getJsoup().connect(url));
			
			Elements newsHeadlines = this.getDocument().select(".announcement-container");
			for (Element headline : newsHeadlines) {
				Vehicle vehicle = new Vehicle();

				Elements imageTags = headline.getElementsByTag("img");
				String yearMakeModel = imageTags.get(0).attr("title");
				String price = headline.select(".announcement-block__price").get(0).ownText();
				Element atag = headline.select(".mask").get(0);
				log.info(price);
				
				if (!StringUtil.isBlank(yearMakeModel)) {
					vehicle.setLink(getLink(atag));
					vehicle.setMake(getMake(yearMakeModel));
					vehicle.setModel(getModel(yearMakeModel));
					vehicle.setYear(getYear(yearMakeModel));
					vehicle.setPrice(getPrice(price));
					//Store unique vehicles
					vehiclesHash.put(vehicle.value(),vehicle);
				}
				
				log.info(vehicle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  new ArrayList<Vehicle>(vehiclesHash.values());
	}

	public ArrayList<Vehicle> scanAll(int maxPageCount) {
		log.info(maxPageCount);
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int pageCount = 1; pageCount <= maxPageCount; pageCount++) {
			String urlString = String.format(this.getUrl(), pageCount);
			log.info(urlString + ":" + this.getPercentage(pageCount,maxPageCount) + "%");
			vehicles.addAll(scan(urlString));
		}
		
		this.getVehicleRepository().saveAll(vehicles);
		
		return vehicles;
	}
	
	public String getYear(String yearMakeModel) {
		int length = yearMakeModel.split(" ").length;
		return yearMakeModel.split(" ")[length - 1];
	}
	
	public String getMake(String yearMakeModel) {
		return yearMakeModel.split(" ")[0];
	}	
	
	public String getModel(String yearMakeModel) {
		return yearMakeModel.split(" ")[1];
	}
	
	public String getLink(Element atag) {
		return "https://www.jacars.net" + atag.attr("href");
	}
	
	public Double getPrice(String price) {
		
		Double priceDouble = 0.0;
		
		try {
			priceDouble = Double.valueOf(price.replaceAll("[^\\d.]", ""));
		}catch(NumberFormatException e) {
			log.info("Price could not be formatted");
		}
		return priceDouble;
	}
	
	public int getMaxPageCount(String homePageUrl) {
		
		Document document = this.getJsoup().connect(homePageUrl);
		
		Elements pageNumbers = document.select(".page-number");
		
		List<Integer> pageNumberList = new ArrayList<Integer>();
		
		for (Element pageNumber : pageNumbers) {
			try {
				pageNumberList.add(Integer.parseInt(pageNumber.text()));
			}catch(Exception ex) {
				log.info(ex.getMessage());
			}
		}
		
		return Collections.max(pageNumberList);

	}
	
	public void setMaxPageCountFromPagination() {
		this.setMaxPageCount(getMaxPageCount("https://www.jacars.net/cars"));
	}
}
