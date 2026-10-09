package com.example.cm.domain;

import com.example.ddd.helper.ValueObject;

@ValueObject
public record Address(AddressLines lines, Country country, City city,boolean defaultAddress) {
	public static Address valueOf(String countryValue, String cityValue,boolean defaultAddress, String... lines) {
		var addressLines = AddressLines.of(lines);
		var country = new Country(countryValue);
		var city = new City(cityValue);
		return new Address(addressLines, country, city,defaultAddress);
	}

}
