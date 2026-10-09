package com.example.cm.document;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AddressDocument {
	private List<String> lines;
	private String country;
	private String city;
	boolean primaryAddress;

	public AddressDocument() {
	}

	public AddressDocument(String country, String city, boolean primaryAddress,String... lines) {
		this.lines = Arrays.asList(lines);
		this.country = country;
		this.city = city;
		this.primaryAddress = primaryAddress;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(boolean primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, country, lines, Boolean.valueOf(primaryAddress));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDocument other = (AddressDocument) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(lines, other.lines) && primaryAddress == other.primaryAddress;
	}

	@Override
	public String toString() {
		return "AddressDocument [lines=" + lines + ", country=" + country + ", city=" + city + ", primaryAddress="
				+ primaryAddress + "]";
	}

}
