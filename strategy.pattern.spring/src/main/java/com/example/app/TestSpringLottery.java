package com.example.app;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.service.LotteryService;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestSpringLottery {
	public static void main(String[] args) {
		ConfigurableApplicationContext container = new AnnotationConfigApplicationContext(AppConfig.class);
		LotteryService lottery = container.getBean(LotteryService.class);
		lottery.draw();
		lottery.printNumbers();
		container.close();
	}
}
