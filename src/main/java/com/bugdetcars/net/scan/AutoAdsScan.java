package com.bugdetcars.net.scan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.bugdetcars.net.model.Vehicle;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class AutoAdsScan extends GenericScan {
	
	public AutoAdsScan() {
		this.setUrl("https://www.autoadsja.com/search.asp?SearchSB=5&page=%d");
		this.setMaxPageCount(350);
	}
	
	public List<Vehicle> scan(String url) {

		HashMap<String,Vehicle> vehiclesHash = new LinkedHashMap<String,Vehicle>();

		try {
			this.setDocument(this.getJsoup().connect(url));
			
			Elements newsHeadlines = this.getDocument().select(".thumbnail");
			for (Element headline : newsHeadlines) {
				Vehicle vehicle = new Vehicle();

				Elements atags = headline.getElementsByTag("a");
				Elements descriptionTags = headline.getElementsByClass("description");
				String yearMakeModel = descriptionTags.get(0).getElementsByTag("h2").text();
				String price = descriptionTags.get(0).getElementsByTag("span").get(1).text();
				
				if (!StringUtil.isBlank(yearMakeModel)) {
					vehicle.setLink(getLink(atags.get(0)));
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
		return yearMakeModel.split(" ")[0];
	}
	
	public String getMake(String yearMakeModel) {
		return yearMakeModel.split(" ")[1];
	}	
	
	public String getModel(String yearMakeModel) {
		return yearMakeModel.split(" ")[2];
	}
	
	public String getLink(Element atag) {
		return atag.attr("href");
	}
	
	public Double getPrice(String price) {
		return Double.valueOf(price.replaceAll("[^\\d.]", ""));
	}

	@Override
	public int getMaxPageCount(String homePageUrl) {
		// TODO Auto-generated method stub
		return 0;
	}
}
