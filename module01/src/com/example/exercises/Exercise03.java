package com.example.exercises;

abstract interface Base2 {
	int value();
}

class Derived2 implements Base2 {
	private int x = 7;

	@Override
	public int value() {
		return x;
	}

	Derived2() {
		System.out.print(value()); // 7
	}
}
public class Exercise03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
