package com.example.exercises;

public class Exercise01 {

	public static void main(String[] args) {
        User user = new User.Builder("binnur")
        		.address("Istanbul")
                .email("binnur@example.com")
                .country("turkiye")
                .phone("+90 555 123 4567")
                .age(45)
                .build();

        System.out.println(user);
	}

}
