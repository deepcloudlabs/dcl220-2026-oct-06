package com.example.exercises;

import java.util.Objects;

@SuppressWarnings("unused")
public class User {

    private final String username;
    private final String email;
    private final int age;
    private final String phone;
    private final String address;
	private final String country;

    private User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
        this.country = builder.country;
    }

    public static class Builder {

        // Required
        private final String username;

        // Optional
        private String email;
        private int age;
        private String phone;
        private String address;
        private String country;
        
        public Builder(String username) {
            this.username = username;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }
        public Builder country(String country) {
        	this.country = country;
        	return this;
        }

        public User build() {
        	Objects.requireNonNull(country,"country cannot be empty or null");
        	// object-pooling -> flyweight pattern
            if (username == null || username.isBlank()) {
                throw new IllegalStateException(
                        "Username must not be empty"
                );
            }

            if (age < 0) {
                throw new IllegalStateException(
                        "Age cannot be negative"
                );
            }
            return new User(this);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}