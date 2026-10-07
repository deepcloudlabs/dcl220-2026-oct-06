package com.example.jaxb;

public class Style {
	private String color;
	private int thickness;

	public Style(String color, int thickness) {
		this.color = color;
		this.thickness = thickness;
	}

	public Style() {
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

}
