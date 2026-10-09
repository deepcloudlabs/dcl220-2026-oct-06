package com.example.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Component(value = "myswitch")
public class Switch {
	@Autowired(required = true)
	@Qualifier(value = "up")
	private Command flipUpCommand;
	@Autowired(required = true)
	@Qualifier(value = "down")
	private Command flipDownCommand;

	public Switch() {
	}

	public Switch(Command flipUpCommand, Command flipDownCommand) {
		this.flipUpCommand = flipUpCommand;
		this.flipDownCommand = flipDownCommand;
	}

	public void flipUp() {
		flipUpCommand.execute();
	}

	public void flipDown() {
		flipDownCommand.execute();
	}

	public void setFlipDownCommand(Command flipDownCommand) {
		this.flipDownCommand = flipDownCommand;
	}

	public void setFlipUpCommand(Command flipUpCommand) {
		this.flipUpCommand = flipUpCommand;
	}

}
