package com.example.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.command.Switch;
import com.example.config.AppConfig;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestCommandPatternInSpring {
	public static void main(String[] args) {
		try (ConfigurableApplicationContext container = new AnnotationConfigApplicationContext(AppConfig.class)) {
			Switch swtch = (Switch) container.getBean(Switch.class);
			swtch.flipUp();
			swtch.flipDown();
		}

	}
}
