package com.ranjankumarmandal.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

	public JwtAuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			HttpHeaders headers = exchange.getRequest().getHeaders();
			String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				log.info("JWT token found in request");
			}

			return chain.filter(exchange);
		};
	}

	public static class Config {
	}
}
