package com.example.exercises;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class TransformProcessor<T, R> extends SubmissionPublisher<R> implements Flow.Processor<T,R> {

	private Function<T,R> function;
	private Subscription subscription;
	
	public TransformProcessor(Function<T, R> function) {
		super();
		this.function = function;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1); // pull-based
	}

	@Override
	public void onNext(T item) {
		submit(function.apply(item));
		this.subscription.request(1); // pull-based		
	}

	@Override
	public void onError(Throwable throwable) {
		
	}

	@Override
	public void onComplete() {
		close();
	}
	
}
