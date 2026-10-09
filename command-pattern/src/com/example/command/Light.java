package com.example.command;

import org.springframework.stereotype.Component;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Component(value = "light")
public class Light {

	public Light() {
	}

	public void turnOn() {
		System.out.println("The light is on!");
	}

	public void turnOff() {
		System.out.println("The light is off!");
	}
}
