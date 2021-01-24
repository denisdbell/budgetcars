package com.bugdetcars.net.scan;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.budgetcars.net.repository.VehicleRepository;
import com.budgetcars.net.wrapper.JsoupWrapper;
import com.bugdetcars.net.model.Vehicle;

import lombok.Data;

@Data
public abstract class GenericScan {

	private String url = "";
	private int maxPageCount = 0;
	private Document document = null;
	private JsoupWrapper jsoup = null;
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public abstract List<Vehicle> scan(String url);

	public abstract ArrayList<Vehicle> scanAll(int maxPageCount);
	
	public abstract String getYear(String value);

	public abstract String getMake(String value);

	public abstract String getModel(String value);

	public abstract String getLink(Element value) ;
	
	public abstract Double getPrice(String value);
	
	public abstract int getMaxPageCount(String homePageUrl);
	
	public int getPercentage(int pageCount, int maxPageCount) {
		return 100 * pageCount / maxPageCount;
	}
}
