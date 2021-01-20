package com.bugdetcars.net.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bugdetcars.net.model.Vehicle;

public class Scan {
	public void scan() {

		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		Document doc = null;
		Vehicle vehicle = new Vehicle();
		try {
			doc = Jsoup.connect("https://www.autoadsja.com/search.asp?SearchMaxVehiclePrice=500000&SearchSB=5&page=8")
					.get();

			System.out.println(doc.title());
			Elements newsHeadlines = doc.select(".thumbnail");
			for (Element headline : newsHeadlines) {
				Elements atags = headline.getElementsByTag("a");
				System.out.println("doc.title()");
				Elements descriptionTags = headline.getElementsByClass("description");
				String yearMakeModel = descriptionTags.get(0).getElementsByTag("h2").text();
				vehicle.setLink(getLink(atags.get(0)));
				vehicle.setMake(getMake(yearMakeModel));
				vehicle.setModel(getModel(yearMakeModel));
				System.out.println(vehicle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
