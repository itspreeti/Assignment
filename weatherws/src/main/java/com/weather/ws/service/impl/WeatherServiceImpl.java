package com.weather.ws.service.impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.stereotype.Service;
import com.weather.ws.Constants;
import com.weather.ws.service.WeatherService;

/**
 * Service layer for MyWeatherApp application
 * This class contains methods that call external API to get the weather information
 */

@Service
public class WeatherServiceImpl implements WeatherService{
	
	
	public WeatherServiceImpl(){
	 
	}

	public Map<String, String> getWeather(String city, String appID, String appURL) throws Exception {
		
		JSONObject jsonObject = parseJson(getWeatherFromAPI(city,appID,appURL));
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map.put(Constants.CITY_NAME, city.substring(0, city.indexOf(",")));
		map.put(Constants.TIME, formatDate(new Date()));
		JSONObject weather = (JSONObject) jsonObject.getJSONArray("weather").get(0);
		map.put(Constants.CITY_WEATHER, weather.getString("description"));
		map.put(Constants.CITY_TEMP, convertToCelcius(jsonObject.getJSONObject("main").getString("temp")));
		map.put(Constants.WIND_SPEED, jsonObject.getJSONObject("wind").getString("speed") + "km/hr");
		return map;
	}

	private String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm a");	
		return sdf.format(date);
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

	/**
	 * This method calls the appropriate API to fetch weather information
	 * @param city - name of city for which weather information is to be retrieved
	 * @param appID - unique appID required to call the API
	 * @param appURL - API URL to call 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	private String getWeatherFromAPI(String city, String appID, String appURL) throws IOException, Exception {
		String jsonString = null;
		URL url = new URL(MessageFormat.format(appURL, city, appID));
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
