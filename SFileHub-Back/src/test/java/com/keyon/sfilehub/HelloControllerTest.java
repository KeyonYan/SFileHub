package com.keyon.sfilehub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class HelloControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("a hello test")
    void greetingTets() {
        String url = "http://localhost:" + port + "/hello/get";
        this.webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello World!");
    }
}
