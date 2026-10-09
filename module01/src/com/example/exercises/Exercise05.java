package com.example.exercises;

interface Identity {
	String id();
}

interface Persistence extends Identity {
	void save();
	default void remove() {
		
	}
	static void fun() {}
}

class Entity implements Persistence {

	@Override
	public String id() {
		return "42";
	}

	@Override
	public void save() {
	}


	
}
public class Exercise05 {

	public static void main(String[] args) {
		var entity = new Entity();
		entity.save();
		System.out.println(entity.id());

	}

}
