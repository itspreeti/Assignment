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
	@Value("${appURL}")
	private String appURL;
	
	public PropertyLoader() {
	}
	
	public String getCitiesList() {
		return citiesList;
	}

	public String getAppID() {
		return appID;
	}

	public String getDefaultCity() {
		return defaultCity;
	}

	public String getAppURL() {
		return appURL;
	}

}
