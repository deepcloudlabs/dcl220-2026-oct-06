package com.example.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Component(value = "up")
public class FlipUpCommand implements Command {
	@Autowired(required = true)
	@Qualifier(value = "light")
	private Light light;

	public FlipUpCommand() {
	}

	public FlipUpCommand(Light light) {
		this.light = light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public void execute() {
		light.turnOn();
	}
}
