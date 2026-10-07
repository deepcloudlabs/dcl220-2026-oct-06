package com.example.singleton;

import static java.util.Objects.isNull;

interface BusinessService {
	public void fun() ;
}

public class LazyBusinessService {
	private static BusinessService instance = null;

	private LazyBusinessService() {
		System.err.println("LazyBusinessService()");
	}

	public void fun() {
		System.err.println("Have fun...");
	}

	public static BusinessService getInstance() {
		if (isNull(instance))
			// instance = new MyBusinessService(); // Once
			instance = () -> {};
		return instance;
	}
	
	static class MyBusinessService implements BusinessService {

		@Override
		public void fun() {
			System.err.println("Have fun...");
		}
		
	}
}
