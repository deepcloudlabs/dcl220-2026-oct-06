package com.example.exercises;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import com.example.events.TradeEvent;

@SuppressWarnings("deprecation")
public class Exercise03 {

	public static void main(String[] args) {
		System.err.println("Application is just started!");
		var tradeEvents = List.of(
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200)
			);
		var observable = new TradeEventObservable();
		Observer slowObserver = (_, event) -> {
			try {TimeUnit.SECONDS.sleep(5);}catch(Exception e) {}
			System.err.println("Slow Observer: Handling event [%s]".formatted(event));
		};
		Observer fastObserver = (_, event) -> {
			System.err.println("Fast Observer: Handling event [%s]".formatted(event));
		};
		observable.addObserver(slowObserver);
		observable.addObserver(fastObserver);
		tradeEvents.forEach(observable::notifyObservers);
		System.err.println("Application is just completed!");		
	}

}

@SuppressWarnings("deprecation")
class TradeEventObservable extends Observable {

	@Override
	public void notifyObservers(Object event) {
		this.setChanged();
		super.notifyObservers(event); // push-based
	}

	
}
