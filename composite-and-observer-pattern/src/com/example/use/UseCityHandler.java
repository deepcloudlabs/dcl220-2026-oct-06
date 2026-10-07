package com.example.use;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.example.domain.City;
import com.example.handler.GenericHandler;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class UseCityHandler {
	public static void main(String[] args) {

		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			GenericHandler<City> handler = new GenericHandler<City>(City.class);
			parser.setContentHandler(handler);
			parser.parse("resources/cities.xml");
			for (City city : handler.getElements(City.class)) {
				System.err.println("City Id: " + city.getID());
				System.err.println("City name: " + city.getName());
				System.err.println("City population: " + city.getPopulation());
				System.err.println("------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
