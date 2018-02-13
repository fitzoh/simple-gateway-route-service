package com.github.fitzoh.simplegatewayrouteservice;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

import static com.github.fitzoh.simplegatewayrouteservice.SimpleGatewayRouteServiceApplication.FORWARDED_URL;
import static org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class RouteServiceForwardingFilter implements GatewayFilter, Ordered {


    @Override
    public int getOrder() {
        return ROUTE_TO_URL_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String forwardUrl = exchange.getRequest().getHeaders().get(FORWARDED_URL).get(0);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI(forwardUrl));
            return chain.filter(exchange);
        } catch (URISyntaxException e) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return Mono.empty();
        }
    }
}