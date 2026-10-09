package com.example.cm.document;

import java.util.Objects;

public class BirthDateDocument {
	private int year;
	private int month;
	private int day;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BirthDateDocument other = (BirthDateDocument) obj;
		return day == other.day && month == other.month && year == other.year;
	}

	@Override
	public String toString() {
		return "BirthDateDocument [year=" + year + ", month=" + month + ", day=" + day + "]";
	}

}
