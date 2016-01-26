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
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.weather.ws.PropertyLoader;
import com.weather.ws.pojos.City;
import com.weather.ws.service.WeatherService;

@ManagedBean
@SessionScoped
public class WeatherMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<City> cities;
	private String city;
	private Map<String, String> map;
	private List<String> cityList;
	//private static Logger logger = LoggerFactory.getLogger(WeatherMB.class);

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
		cityList = (Arrays.asList(propFileBean.getCitiesList().split("#")));
		cities = new ArrayList<>();
		for (String cityName: cityList){
			cities.add(new City(cityName));
		}
		city = propFileBean.getDefaultCity();
		try {
			getWeatherDetailsForCity();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	public void onCityChange(){
		try {			
			getWeatherDetailsForCity();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	private void getWeatherDetailsForCity() throws JSONException, IOException {
		map=weatherService.getWeather(city,propFileBean.getAppID());
	}


}