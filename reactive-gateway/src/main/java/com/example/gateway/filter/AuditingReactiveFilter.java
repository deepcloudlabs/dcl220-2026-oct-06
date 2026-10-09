package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
@Order(Integer.MIN_VALUE)
public class AuditingReactiveFilter implements GlobalFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		var request = exchange.getRequest();
		System.out.println("Path           : %s".formatted(request.getPath()));
		System.out.println("Method         : %s".formatted(request.getMethod()));
		System.out.println("Local Address  : %s".formatted(request.getLocalAddress()));
		System.out.println("URI            : %s".formatted(request.getURI()));
		request.getHeaders().forEach((headerName, value) -> {
			System.out.println("%24s: %s".formatted(headerName, value));
		});
		request.getBody().subscribe(System.out::println);
		var response = exchange.getResponse();
		System.out.println("Status code: %s".formatted(response.getStatusCode()));
		return chain.filter(exchange);
	}

}
