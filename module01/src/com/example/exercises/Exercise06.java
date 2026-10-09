package com.example.exercises;

interface Service {
    int x = 108;
	static String name() {
        return "Service";
    }
    default String execute() {
        return name(); // x VT, Service::name
    }
}

class Implementation implements Service {
    static String name() {
        return "Implementation";
    }
}

public class Exercise06 {

	public static void main(String[] args) {
		Service service = new Implementation();
		System.out.println(service.execute()); // execute(service);

	}

}
