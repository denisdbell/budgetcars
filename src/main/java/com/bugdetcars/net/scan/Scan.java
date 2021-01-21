package com.bugdetcars.net.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bugdetcars.net.model.Vehicle;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Scan {

	public String url = "https://www.autoadsja.com/search.asp?SearchSB=5&page=%d";
	public int maxPageCount = 350;

	public List<Vehicle> scan(String url) {

		HashMap<String,Vehicle> vehiclesHash = new HashMap<String,Vehicle>();

		Document doc = null;
		Vehicle vehicle = new Vehicle();
		try {
			doc = Jsoup.connect(url).get();
			Elements newsHeadlines = doc.select(".thumbnail");
			for (Element headline : newsHeadlines) {
				Elements atags = headline.getElementsByTag("a");
				Elements descriptionTags = headline.getElementsByClass("description");
				log.debug("descriptionTags ", descriptionTags.size());
				String yearMakeModel = descriptionTags.get(0).getElementsByTag("h2").text();
				String price = descriptionTags.get(0).getElementsByTag("span").get(1).text();
				
				if (!StringUtil.isBlank(yearMakeModel)) {
					vehicle.setLink(getLink(atags.get(0)));
					vehicle.setMake(getMake(yearMakeModel));
					vehicle.setModel(getModel(yearMakeModel));
					vehicle.setYear(getYear(yearMakeModel));
					vehicle.setPrice(getPrice(price));
					vehiclesHash.put(vehicle.value(),vehicle);
				}

				System.out.println(vehicle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  new ArrayList<Vehicle>(vehiclesHash.values());

	}

	public void scanAll(int maxPageCount) {
		for (int pageCount = 0; pageCount <= maxPageCount; pageCount++) {
			String urlString = String.format(this.url, pageCount);
			scan(urlString);
		}
	}

	private String getYear(String yearMakeModel) {
		return yearMakeModel.split(" ")[0];
	}

	private String getMake(String yearMakeModel) {
		return yearMakeModel.split(" ")[1];
	}

	private String getModel(String yearMakeModel) {
		return yearMakeModel.split(" ")[2];
	}

	private String getLink(Element atag) {
		return atag.attr("href");
	}
	
	private Double getPrice(String price) {
		return Double.valueOf(price.replaceAll("[^\\d.]", ""));

	}
}
