package com.example.lsp;

import java.util.List;

sealed interface Shape2D permits Rectangle, Square, Shape3D {
	double area();

	double circumference();
}

sealed interface Shape3D extends Shape2D permits Cube {
	double volume();

}

record Rectangle(double width, double height) implements Shape2D {

	@Override
	public double area() {
		return width * height;
	}

	@Override
	public double circumference() {
		return 2.0 * (width + height);
	}

}

record Cube(double edge) implements Shape3D {

	@Override
	public double area() {
		return 6 * edge * edge;
	}

	@Override
	public double circumference() {
		return 12.0 * edge;
	}

	@Override
	public double volume() {
		return edge * area();
	}
}

record Square(double edge) implements Shape2D {

	@Override
	public double area() {
		return edge * edge;
	}

	@Override
	public double circumference() {
		return 4.0 * edge;
	}
}

public class Exercise07 {

	public static void main(String[] args) {
		List<Shape2D> shapes = List.of(new Square(5),new Rectangle(5,9),new Cube(42));
		var totalSurfaceArea = shapes.stream().mapToDouble(Shape2D::area).sum();
		System.out.println(totalSurfaceArea);
	}

}
