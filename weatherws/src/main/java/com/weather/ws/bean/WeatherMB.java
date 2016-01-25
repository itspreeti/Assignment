package com.weather.ws.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weather.ws.PropertyLoader;
import com.weather.ws.pojos.City;
import com.weather.ws.service.WeatherService;

import lombok.Data;

@Data
@ManagedBean
//@Component
@SessionScoped

public class WeatherMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<City> cities;
	private String city;
	private Map<String, String> map;
	private List<String> cityList;
	private static Logger logger = LoggerFactory.getLogger(WeatherMB.class);
	
	@ManagedProperty("#{propFileBean}")
	private PropertyLoader propFileBean;

	
	public PropertyLoader getPropFileBean() {
		return propFileBean;
	}

	public void setPropFileBean(PropertyLoader propFileBean) {
		this.propFileBean = propFileBean;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	WeatherService weatherService=new WeatherService();
	
	@PostConstruct
	void init() {
		logger.info("=================init called===================");
		logger.info("================="+propFileBean+"=============");
		logger.info("================="+propFileBean.getCitiesList());
		
		cityList = (Arrays.asList(propFileBean.getCitiesList().split("#")));
		cities = new ArrayList<>();
		for (String cityName: cityList){
			System.out.println("City name:"+ cityName);
			cities.add(new City(cityName));
		}
	/*	cities.add(new City("Melbourne,aus"));
		cities.add(new City("Sydney,aus"));
		cities.add(new City("Wollongong,aus"));*/
		try {
		city = "Melbourne,aus";
		map=weatherService.getWeather(city,propFileBean.getAppID());
		} catch (JSONException | IOException e) {
			//TODO proper exception handling
			e.printStackTrace();
		}
	}
	
	public void onCityChange(){
		try {			
			map=weatherService.getWeather(city,propFileBean.getAppID());
		} catch (JSONException | IOException e) {
			//TODO proper exception handling
			e.printStackTrace();
		}
	}

}