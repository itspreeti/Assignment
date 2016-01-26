package com.weather.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.weather.ws.service.WeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WeatherwsApplication.class)
public class WeatherServiceTest {

	@Autowired
	WeatherService weatherService;
	
	@Autowired
	PropertyLoader propertyLoader;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void successTest() throws JSONException, IOException, Exception {
		Map<String, String> resultMap = weatherService.getWeather("Melbourne,Aus", propertyLoader.getAppID(), propertyLoader.getAppURL());
	    assertNotNull(resultMap);
		assertEquals("Expected size",5,resultMap.size());
	}
	
	@Test(expected=JSONException.class)
	public void failueTestUnknownCityName() throws IOException, Exception {
		weatherService.getWeather("xxxyyy,zzzz", propertyLoader.getAppID(), propertyLoader.getAppURL());
	 }
	
	@Test(expected=MalformedURLException.class)
	public void failueTestUnknownURL() throws IOException, Exception {
		weatherService.getWeather("Melbourne,Aus", propertyLoader.getAppID(), "");
	 }
	
	/*@Test(expected=UnknownHostException.class)
	public void failueTestServerNotRechable() throws IOException, Exception {
		    weatherService.getWeather("Melbourne,Aus", propertyLoader.getAppID(), propertyLoader.getAppURL());
	 }*/
	
	@Test(expected=RuntimeException.class)
	public void failureTestBlankAppID() throws JSONException, IOException, Exception {
			weatherService.getWeather("Melbourne,Aus", "", propertyLoader.getAppURL());
		}

}
