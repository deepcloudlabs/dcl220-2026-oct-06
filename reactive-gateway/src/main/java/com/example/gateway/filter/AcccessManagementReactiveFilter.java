package com.example.gateway.filter;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
@Order(2)
public class AcccessManagementReactiveFilter implements GlobalFilter {
	private final LocalTime startTime;
	private final LocalTime endTime;
	

	public AcccessManagementReactiveFilter(
			@Value("${amStartHour}") int startHour, 
			@Value("${amEndHour}") int endHour) {
		this.startTime  = LocalTime.of(startHour, 0);
		this.endTime  = LocalTime.of(endHour, 0);
	}


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		var now = LocalTime.now();
		if (now.isBefore(startTime) || now.isAfter(endTime)) {
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			return exchange.getResponse().setComplete();
		}
		return chain.filter(exchange);
	}

}
