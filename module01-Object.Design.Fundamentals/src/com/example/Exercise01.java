package com.example;

public class Exercise01 {

	public static void main(String[] args) {
		

	}

}

abstract class F {
	private final int x;

	public F(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}

	public void fun() {}
	public abstract void gun(); 
} 

abstract interface G {
	public static int x = 42;
	public abstract void gun(); 
	default void fun() {} 
	static void sun() {} // utility pure functions in fp 
}

abstract interface H {
   void run(); 
}

interface I {} 
interface J {} 
interface K {} 

interface M extends I,J,K {}

class BB extends F implements G,H {

	public BB() {
		super(42);
	}

	@Override
	public void gun() {
	}

	@Override
	public void run() {
	}
	
} 

class AA implements G {

	@Override
	public void gun() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fun() {

	}
	
}

