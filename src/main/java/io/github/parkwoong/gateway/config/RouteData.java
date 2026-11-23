package io.github.parkwoong.gateway.config;

import io.github.parkwoong.gateway.backend.Backend;
import io.github.parkwoong.gateway.backend.BackendRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteData {
    @Bean
    CommandLineRunner route(BackendRepository repository) {
        return args -> {
            Backend backend = Backend.with("http://127.0.0.1:9090");
            backend.setPattern("/sample");
            backend.setPathUrl(false);
            repository.save(backend);
        };
    }
}
