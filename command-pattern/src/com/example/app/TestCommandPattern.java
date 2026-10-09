package com.example.app;

import com.example.command.Command;
import com.example.command.FlipDownCommand;
import com.example.command.FlipUpCommand;
import com.example.command.Light;
import com.example.command.Switch;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestCommandPattern {
	public static void main(String[] args) {
		Light lamp = new Light();
		Command switchDown = new FlipDownCommand(lamp);
		Command switchUp = new FlipUpCommand(lamp);
		Switch swtch = new Switch(switchUp, switchDown);
		swtch.flipUp();
		swtch.flipDown();
	}
}
