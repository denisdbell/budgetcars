package com.bugdetcars.net.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.Jsoup;
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


public abstract class GenericScan {

	public String url = "";
	public int maxPageCount = 0;
	public Document document = null;
	public JsoupWrapper jsoup = new JsoupWrapper();
	
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
