package com.keyon.sfilehub;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HelloControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("login admin")
    void login() {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", "admin");
        bodyMap.put("password", "123456");
        String url = "http://localhost:" + port + "/login";
        String res = this.webTestClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .toString();
        System.out.println("login res: " + res);
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
                .expectBody(String.class).isEqualTo("{\"code\":40002,\"msg\":\"尚未登录，请先登录\"}");
    }
}