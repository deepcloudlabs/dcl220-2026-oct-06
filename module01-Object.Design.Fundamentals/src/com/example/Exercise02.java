package com.example;

public class Exercise02 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		U u1 = new U();
		U u2 = new V();
		U u3 = new W();
		// U u4 = new Z(); // error
		P p = (P) new W();
	}

}

class U implements P {}
class V extends U {}
class W extends V {}
class Z {}

interface P {}
interface Q {}
