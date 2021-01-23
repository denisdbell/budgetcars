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
import com.budgetcars.net.wrapper.JsoupWrapper;
import com.bugdetcars.net.model.Vehicle;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@Component
public class JaCarsScan extends GenericScan  {
	public String url = "https://www.jacars.net/cars/?page=%d";
	public int maxPageCount = 350;
	public Document document;
	public JsoupWrapper jsoup = new JsoupWrapper();
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	public List<Vehicle> scan(String url) {

		HashMap<String,Vehicle> vehiclesHash = new LinkedHashMap<String,Vehicle>();

		Document document = null;
		try {
			
			document = jsoup.connect(url);
			
			Elements newsHeadlines = document.select(".announcement-container");
			for (Element headline : newsHeadlines) {
				Vehicle vehicle = new Vehicle();

				Elements imageTags = headline.getElementsByTag("img");
				String yearMakeModel = imageTags.get(0).attr("title");
				log.info(headline.getElementsByClass("announcement-block__price").text());
				String price = headline.getElementsByClass("announcement-block__price").text();				
				
				if (!StringUtil.isBlank(yearMakeModel)) {
					vehicle.setLink(getLink(imageTags.get(0)));
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
		maxPageCount = getMaxPageCount("https://www.jacars.net/cars");
		log.info(maxPageCount);
		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
		for (int pageCount = 1; pageCount <= maxPageCount; pageCount++) {
			String urlString = String.format(this.url, pageCount);
			log.info(urlString + ":" + this.getPercentage(pageCount,maxPageCount) + "%");
			vehicles.addAll(scan(urlString));
		}
		
		vehicleRepository.saveAll(vehicles);
		
		return vehicles;
	}
	
	public String getYear(String yearMakeModel) {
		return yearMakeModel.split(" ")[3];
	}
	
	public String getMake(String yearMakeModel) {
		return yearMakeModel.split(" ")[0];
	}	
	
	public String getModel(String yearMakeModel) {
		return yearMakeModel.split(" ")[1];
	}
	
	public String getLink(Element atag) {
		return atag.attr("src");
	}
	
	public Double getPrice(String price) {
		log.info(price);
		return Double.valueOf(price.replaceAll("[^\\d.]", ""));
	}
	
	public int getMaxPageCount(String homePageUrl) {
		
		Document document = jsoup.connect(homePageUrl);
		
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
}
