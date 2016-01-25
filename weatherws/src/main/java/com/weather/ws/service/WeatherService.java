package com.weather.ws.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.stereotype.Service;

import com.weather.ws.Constants;

@Service
public class WeatherService {

	public Map<String, String> getWeather(String city, String appID) throws JSONException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");	
		JSONObject parseJson = parseJson(getWeatherFromAPI(city,appID));
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(Constants.CITY_NAME, city.substring(0, city.indexOf(",")));
		map.put(Constants.TIME, sdf.format(new Date()));
		JSONObject weather = (JSONObject) parseJson.getJSONArray("weather").get(0);
		map.put(Constants.CITY_WEATHER, weather.getString("description"));
		map.put(Constants.CITY_TEMP, convertToCelcius(parseJson.getJSONObject("main").getString("temp")));
		map.put(Constants.WIND_SPEED, parseJson.getJSONObject("wind").getString("speed") + "km/hr");
		return map;
	}

	private String convertToCelcius(String far) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		Double floatFar = Double.valueOf(far);
		Double celcius = (floatFar - 273.15f);
		return df.format(celcius) + " °C";

	}

	private JSONObject parseJson(String jsonString) throws JSONException {
		return new JSONObject(jsonString);
	}

	private String getWeatherFromAPI(String city, String appID) throws IOException {
		String jsonString = null;

		/*URL url = new URL(
				"http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=2de143494c0b295cca9337e1e96b00e0");*/
		URL url = new URL(
				"http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid="+appID);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		jsonString = br.readLine();
		conn.disconnect();

		return jsonString;
	}

}
