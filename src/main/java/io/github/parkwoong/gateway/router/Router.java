package io.github.parkwoong.gateway.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.github.parkwoong.gateway.handler.IngressHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class Router {

    @Bean
    public RouterFunction<ServerResponse> ingress(IngressHandler ingressHandler) {
        return route(all(), ingressHandler::handle);
    }
}
