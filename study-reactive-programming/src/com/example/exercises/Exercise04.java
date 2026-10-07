package com.example.exercises;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import com.example.events.RichTradeEvent;
import com.example.events.TradeEvent;

public class Exercise04 {

	public static void main(String[] args) {
		System.err.println("Application is just started!");
		var tradeEvents = List.of(
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200),
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200),
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200),
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200),
				new TradeEvent("orcl", 100,200),
				new TradeEvent("orcl", 101,100),
				new TradeEvent("orcl", 102,200),
				new TradeEvent("orcl", 103,100),
				new TradeEvent("orcl", 104,200)
			);
		var publisher = new SubmissionPublisher<TradeEvent>();
		var volumeMapper = new TransformProcessor<TradeEvent,RichTradeEvent>(RichTradeEvent::new);
		publisher.subscribe(volumeMapper);
		volumeMapper.subscribe(new SlowSubcriber());
		volumeMapper.subscribe(new FastSubcriber());
		tradeEvents.forEach(publisher::submit);
		publisher.close();
		try {TimeUnit.SECONDS.sleep(25);}catch(Exception e) {}
		System.err.println("Application is just completed!");
	}

}

class SlowSubcriber implements Flow.Subscriber<RichTradeEvent> {
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1); // pull-based
	}

	@Override
	public void onNext(RichTradeEvent event) {
		//try {TimeUnit.SECONDS.sleep(5);}catch(Exception e) {}
		System.err.println("[%s]SlowSubcriber: Handling event [%s]".formatted(Thread.currentThread().getName(),event));
		this.subscription.request(1); // pull-based
	}

	@Override
	public void onError(Throwable throwable) {
	}

	@Override
	public void onComplete() {
		System.err.println("All events are completed.");
	}
	
}

class FastSubcriber implements Flow.Subscriber<RichTradeEvent> {
	private Subscription subscription;
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1); // pull-based		
	}
	
	@Override
	public void onNext(RichTradeEvent event) {
		System.err.println("[%s]FastSubcriber: Handling event [%s]".formatted(Thread.currentThread().getName(),event));
		this.subscription.request(1); // pull-based
	}
	
	@Override
	public void onError(Throwable throwable) {
	}
	
	@Override
	public void onComplete() {
		System.err.println("All events are completed.");
	}
	
}