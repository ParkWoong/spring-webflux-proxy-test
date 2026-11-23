package io.github.parkwoong.gateway.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ingress")
public class IngressProperties {
    private String methodKey;
    private String targetKey;
    private String timeoutKey;
    private String pathKey;
}
