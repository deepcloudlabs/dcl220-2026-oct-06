package com.example.exercises;

interface U {
	default Number value() {
		return 1;
	}
}

interface V {
	default Integer value() {
		return 2;
	}
}

class W implements U, V {
	public Integer value() {
		return V.super.value();
	}
}

public class Exercise07 {

	public static void main(String[] args) {
		V v = new W();
		U u = (U) v;
		System.out.println(v.value());
		System.out.println(u.value());

	}

}
