package io.github.parkwoong.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
    private static final int MEMORY_BYTES =
            5 * 1024 * 1024; // 5MB, if MEMORY_BYTES is -1, that means Unlimits

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer codecConfigurer) {
        codecConfigurer.defaultCodecs().maxInMemorySize(MEMORY_BYTES);
    }
}
