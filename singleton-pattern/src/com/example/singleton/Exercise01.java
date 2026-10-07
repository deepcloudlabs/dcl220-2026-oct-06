package com.example.singleton;

public class Exercise01 {

	public static void main(String[] args) {

		BusinessService businessService = null;

		if (true) {
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				var clazz = Class.forName("com.example.singleton.BusinessServiceEnum", false, classLoader);

				System.err.println("Class loaded.");

				var getInstance = clazz.getMethod("getInstance");

				System.err.println("Method discovered.");

				businessService = (BusinessService) getInstance.invoke(null);

				businessService.fun();

			} catch (ReflectiveOperationException e) {
				throw new IllegalStateException("Cannot create BusinessService", e);
			}
		}
	}
}
