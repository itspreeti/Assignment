package com.weather.ws.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.weather.ws.PropertyLoader;
import com.weather.ws.pojos.City;
import com.weather.ws.service.WeatherService;
import com.weather.ws.service.impl.WeatherServiceImpl;

@ManagedBean
@SessionScoped
public class WeatherMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<City> cities;
	private String city;
	private Map<String, String> displayMap;
	
	private static Logger logger = LoggerFactory.getLogger(WeatherMB.class);

	@Autowired
	private PropertyLoader propFileBean;
	
	@Autowired
	WeatherService weatherService;
	
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

	public Map<String, String> getDisplayMap() {
		return displayMap;
	}

	public void setDisplayMap(Map<String, String> displayMap) {
		this.displayMap = displayMap;
	}

	
	@PostConstruct
	void init() {
		//Code to include autowiring capability in managed bean
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                                   getAutowireCapableBeanFactory().
                                   autowireBean(this);
		//populate java object for city selection drop-down
        populateCityList();
        //populate default city for displaying weather info
		city = propFileBean.getDefaultCity();
		try {
		//get weather information for default city	
			getWeatherDetailsForCity();
		} catch (Exception e ) {
			logger.error("An Exception Occurred :"+e.getMessage());
			addMessageOnError();
		}
	}

	private void populateCityList() {
		List<String> cityList;
		cityList = (Arrays.asList(propFileBean.getCitiesList().split("#")));
		cities = new ArrayList<>();
		for (String cityName: cityList){
			cities.add(new City(cityName));
		}
	}
/**
 * This method will be called when user makes a city selection
 * on the UI
 */
	public void onCityChange(){
		try {			
			getWeatherDetailsForCity();
		} catch (Exception e ) {
			logger.error("An Exception Occurred :"+e.getMessage());
			addMessageOnError();
		}
	}
	
	private void getWeatherDetailsForCity() throws JSONException, IOException, Exception {
		//Call to service class method to get weather details and populate the display map 
		displayMap=weatherService.getWeather(city,propFileBean.getAppID(),propFileBean.getAppURL());
	}

	private void addMessageOnError() {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		facesCtx.addMessage(null, new FacesMessage("An error occurred while fetching the weather info"));
		facesCtx.addMessage(null, new FacesMessage("Please try again later"));
		facesCtx.addMessage(null, new FacesMessage("Contact the system administrator if the problem persists"));
	}

	


}