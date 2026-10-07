package com.example.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Circle {
	private int radius, x, y;
	private Style style;
	
	public Circle() {
	}

	public Circle(int radius, int x, int y) {
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public static void main(String[] args) {
		try {
			JAXBContext jc = JAXBContext.newInstance(Circle.class);
			Marshaller m = jc.createMarshaller();
			System.out.println(jc.getClass().getName());
			System.out.println(m.getClass().getName());
			Circle circle = new Circle(1, 2, 10);
			Style style = new Style("red",5);
			circle.setStyle(style);
			m.marshal(circle, System.out);
		} catch (JAXBException jbe) {
			jbe.printStackTrace();
		}
	}

}
