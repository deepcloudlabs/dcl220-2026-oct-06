package module02;

interface Shape {
	public double area();

	public double circumference();
}

record Rectangle(double width, double height) implements Shape {
	@Override
	public double area() {
		return (width * height);
	}

	@Override
	public double circumference() {
		return (2. * (width + height));
	}
}

record Square(double edge) implements Shape {

	@Override
	public double area() {
		return edge * edge;
	}

	@Override
	public double circumference() {
		return 4 * edge;
	}
	
}

public class Exercise03 {
	public static void testLSP(Shape shape) {
		
		
	}

	public static void main(String[] args) {
		Rectangle r = new Rectangle(10.0, 20.0);
		testLSP(r);
		Square s = new Square(30);
		testLSP(s);
	}

}
