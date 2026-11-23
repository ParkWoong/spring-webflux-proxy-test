package io.github.parkwoong.gateway.handler;

import static io.github.parkwoong.gateway.vo.Errors.INTERNAL_CLIENT_ERROR;
import static io.github.parkwoong.gateway.vo.Errors.INVALID_REQUEST_ERROR;

import static org.springframework.http.HttpMethod.resolve;

import static java.time.Duration.ofSeconds;

import io.github.parkwoong.gateway.backend.BackendRepository;
import io.github.parkwoong.gateway.properties.IngressProperties;
import io.github.parkwoong.gateway.util.StringSupporter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class IngressHandler {

    private static final byte[] NO_BYTES = {};
    private final WebClient webClient;
    private final IngressProperties properties;
    private final BackendRepository backendRepository;

    static final String JSESSIONID = "JSESSIONID";
    static final String COOKIE = "Set-Cookie";

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {

        return Mono.justOrEmpty(backendRepository.findById(serverRequest.path()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(INVALID_REQUEST_ERROR)))
                .flatMap(
                        backend ->
                                webClient
                                        .method(
                                                resolve(
                                                        serverRequest
                                                                .queryParam(
                                                                        properties.getMethodKey())
                                                                .orElse(
                                                                        serverRequest
                                                                                .methodName())))
                                        .uri(
                                                backend.getUrl(),
                                                builder -> {
                                                    if (!backend.isPathUrl())
                                                        builder.path(serverRequest.path());

                                                    serverRequest
                                                            .queryParams()
                                                            .forEach(
                                                                    (k, v) -> {
                                                                        if (properties
                                                                                .getMethodKey()
                                                                                .equals(k)) return;
                                                                        if (properties
                                                                                .getTargetKey()
                                                                                .equals(k)) return;
                                                                        if (properties
                                                                                .getTimeoutKey()
                                                                                .equals(k)) return;

                                                                        builder.queryParam(k, v);
                                                                    });

                                                    return builder.build();
                                                })
                                        .httpRequest(
                                                httpRequest ->
                                                        ((HttpClientRequest)
                                                                        httpRequest
                                                                                .getNativeRequest())
                                                                .responseTimeout(
                                                                        ofSeconds(
                                                                                serverRequest
                                                                                        .queryParam(
                                                                                                properties
                                                                                                        .getTimeoutKey())
                                                                                        .filter(
                                                                                                StringSupporter
                                                                                                        ::isNumericString)
                                                                                        .map(
                                                                                                Long
                                                                                                        ::parseLong)
                                                                                        .orElse(
                                                                                                backend
                                                                                                        .getTimeoutSeconds()))))
                                        .headers(
                                                headers -> {
                                                    headers.addAll(
                                                            serverRequest
                                                                    .headers()
                                                                    .asHttpHeaders());
                                                    headers.remove("Host");
                                                })
                                        .body(
                                                serverRequest
                                                        .bodyToMono(byte[].class)
                                                        .defaultIfEmpty(NO_BYTES),
                                                byte[].class)
                                        .exchangeToMono(
                                                r -> {
                                                    log.info("Call External API");
                                                    return r.bodyToMono(byte[].class)
                                                            .defaultIfEmpty(NO_BYTES)
                                                            .flatMap(
                                                                    body ->
                                                                            ServerResponse.status(
                                                                                            r
                                                                                                    .statusCode())
                                                                                    .headers(
                                                                                            $ ->
                                                                                                    $
                                                                                                            .addAll(
                                                                                                                    r.headers()
                                                                                                                            .asHttpHeaders()))
                                                                                    .bodyValue(
                                                                                            body));
                                                })
                                        .onErrorResume(
                                                e -> {
                                                    log.error("Upstream request failed", e);
                                                    return Mono.empty();
                                                })
                                        .switchIfEmpty(
                                                Mono.defer(
                                                        () -> Mono.error(INTERNAL_CLIENT_ERROR))));
    }
}
