package com.weather.ws;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

	public String getCitiesList() {
		return citiesList;
	}

	public String getAppID() {
		return appID;
	}

	public PropertyLoader() {
	}

	@PostConstruct
	public void postConstruct(){
		logger.info("Property One: " + citiesList);
	}

}
