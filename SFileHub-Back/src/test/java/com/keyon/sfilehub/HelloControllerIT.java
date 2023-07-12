package com.keyon.sfilehub;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HelloControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @BeforeEach
    @DisplayName("login admin")
    void login() {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", "admin");
        bodyMap.put("password", "123456");
        String url = "http://localhost:" + port + "/login";
        EntityExchangeResult<String> result = this.webTestClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult();
        System.out.println("login res: " + result.getResponseBody());
    }


    @Test
    @DisplayName("a hello test")
    void greetingTets() {
        String url = "http://localhost:" + port + "/hello/get";
        this.webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
    }
}