package com.example.exercises;

abstract class Base {
	Base() {
		System.out.print(value() + " "); // VT: 0: value()
	}

	abstract int value();
}

class Derived extends Base {
	private int x = 7;

	@Override
	int value() {
		return x;
	}

	Derived() {
		System.out.print(value()); // 7
	}
}

public class Exercise02 {

	public static void main(String[] args) {
		new Derived(); // Heap: x(0)

	}

}
