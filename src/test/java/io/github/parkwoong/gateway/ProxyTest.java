package io.github.parkwoong.gateway;

import static org.junit.jupiter.api.Assertions.*;

import io.github.parkwoong.gateway.backend.BackendRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProxyTest {
    @Autowired BackendRepository repository;

    @Test
    void onlyConfiguredRouteExists() {
        assertTrue(repository.findById("/sample").isPresent());
        assertFalse(repository.findById("/unknown").isPresent());
    }
}
