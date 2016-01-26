package com.weather.ws.service;

import java.util.Map;

public interface WeatherService {

	public Map<String, String> getWeather(String city, String appID, String appURL)throws Exception;
}
