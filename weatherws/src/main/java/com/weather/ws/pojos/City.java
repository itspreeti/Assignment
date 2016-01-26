package com.weather.ws.pojos;

import lombok.Data;

@Data
public class City {

	public City(String string) {
		name=string;
	}
	private String name;
	
	public String toString(){
		return name;
	}
}
