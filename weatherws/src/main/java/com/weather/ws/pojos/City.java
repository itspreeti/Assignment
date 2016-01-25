package com.weather.ws.pojos;

import lombok.Data;

@Data
public class City {

	public City(String string) {
		name=string;
	}
	private String name;
	private String weather_degree;
	private String country;
	
	public String toString(){
		return name;
	}
}
