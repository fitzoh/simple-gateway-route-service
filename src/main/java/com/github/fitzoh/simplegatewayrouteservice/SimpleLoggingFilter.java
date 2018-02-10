package com.github.fitzoh.simplegatewayrouteservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SimpleLoggingFilter implements GatewayFilter{

    private static final Logger log = LoggerFactory.getLogger(SimpleLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Method:{} Host:{} Path:{} QueryParams:{}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getHost(),
                exchange.getRequest().getURI().getPath(),
                exchange.getRequest().getQueryParams());
        return chain.filter(exchange);
    }
}
