package io.github.parkwoong.gateway.backend;

import io.github.parkwoong.gateway.config.HashMapConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "backend_route")
public class Backend {

    @Id private String pattern;
    private String url;
    private boolean pathUrl;

    @Column(columnDefinition = "INTEGER")
    private Long timeoutSeconds = 30L;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> defaultQueries;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> defaultHeaders;

    public static Backend with(String url) {

        final Backend backend = new Backend();
        backend.url = url;
        backend.pathUrl = true;
        return backend;
    }
}
