package com.weather.ws;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class PropertyLoader {
	@Value("${citiesList}")
	private String citiesList;
	@Value("${appID}")
	private String appID;
	@Value("${defaultCity}")
	private String defaultCity;
	

	public String getCitiesList() {
		return citiesList;
	}

	public String getAppID() {
		return appID;
	}

	public PropertyLoader() {
	}

	public String getDefaultCity() {
		return defaultCity;
	}

}
