package com.example;

public class ObjectCachingExample1 {
	public static void main(String[] args) throws Exception {
		String s1 = "Jack"; // Object Pooling -> Flyweight -> Immutable
		String s2 = new String("Jack");
		String s3 = "Jack";
		System.err.println("s1 == s2 : " + (s1 == s2));  // false
		System.err.println("s1 == s3 : " + (s1 == s3));  // true
		s2 = s2.intern(); // internalize
		System.err.println("s1 == s2 : " + (s1 == s2)); // true
		System.err.println("s1 == s3 : " + (s1 == s3)); // true
	}

}
