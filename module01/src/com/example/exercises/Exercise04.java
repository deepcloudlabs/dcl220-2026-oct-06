package com.example.exercises;

abstract class AA {}
abstract class FF {}
interface I {}
interface J {}
interface K {}
interface M {}

interface L extends I, J, K {}

class BB extends AA implements I, J, K {}


public class Exercise04 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		AA p = new BB();
		// FF q = new BB(); // Error: No relation between FF & BB
        I r = new BB();
        M s = (M) new BB();
	}

}
