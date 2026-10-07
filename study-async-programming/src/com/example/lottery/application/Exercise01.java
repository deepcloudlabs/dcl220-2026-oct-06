package com.example.lottery.application;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

// Observer Pattern -> Event-Driven Programming
// Entity -> Event -> Observable 
public class Exercise01 {
	public static void main(String[] args) {
		var trades = List.of(new TradeEvent("orcl", 100, 200), new TradeEvent("msft", 150, 300));

		@SuppressWarnings("deprecation")
		Observer o1 = (_, event) -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			}
			System.out.println("Observer #1 has received an event: %s".formatted(event));
		};
		@SuppressWarnings("deprecation")
		Observer o2 = (_, event) -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
			System.out.println("Observer #2 has received an event: %s".formatted(event));
		};
		@SuppressWarnings("deprecation")
		Observer o3 = (_, event) -> {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
			}
			System.out.println("Observer #3 has received an event: %s".formatted(event));
		};
		var observable = new TradeEventObservable();
		observable.addObserver(o1);
		observable.addObserver(o2);
		observable.addObserver(o3);
		trades.forEach(observable::notifyObservers);

	}
}

@SuppressWarnings("deprecation")
class TradeEventObservable extends Observable {

	@Override
	public void notifyObservers(Object event) {
		setChanged();
		super.notifyObservers(event);
	}

}

record TradeEvent(String symbol, double price, double quantity) {
}
