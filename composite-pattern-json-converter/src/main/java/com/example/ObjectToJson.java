package com.example;

import java.io.IOException;

import com.example.world.entity.City;
import com.example.world.entity.Country;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJson {

	public static void main(String[] args) {
		ObjectMapper Obj = new ObjectMapper();

		try {
			Country turkey = new Country("TUR", "Turkey", "Asia", 1, 2, 3);
			City ankara = new City(turkey, 1, "Ankara", 1);
			City istanbul = new City(turkey, 2, "Istanbul", 2);
			turkey.setCapital(ankara);
			turkey.getCities().add(ankara);
			turkey.getCities().add(istanbul);
			String jsonStr = Obj.writeValueAsString(turkey);
			System.out.println(jsonStr);
			Country deserializedCountry = Obj.readValue(jsonStr, Country.class);
			System.out.println(deserializedCountry);
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
