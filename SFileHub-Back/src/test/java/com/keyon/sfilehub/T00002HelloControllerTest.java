package com.keyon.sfilehub;

import com.alibaba.fastjson2.JSONObject;
import com.keyon.sfilehub.dao.RoleDao;
import com.keyon.sfilehub.dao.UserDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T00002HelloControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    private static MultiValueMap<String, ResponseCookie> cookies;

    @Test
    @Order(1)
    @DisplayName("login admin")
    void login() {
        System.out.println("22222");
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", "admin");
        bodyMap.put("password", "123456");
        String url = "http://localhost:" + port + "/login";
        EntityExchangeResult<String> resp = webTestClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult();
        String result = resp.getResponseBody();
        System.out.println("login res: " + result);
        JSONObject jsonResult = JSONObject.parseObject(result);
        Assertions.assertNotNull(jsonResult);
        Assertions.assertNotNull(jsonResult.get("code"));
        Assertions.assertEquals((int) jsonResult.get("code"), 0);
        cookies = resp.getResponseCookies();
        System.out.println("cookies key: " + cookies.keySet());
        Assertions.assertEquals(cookies.keySet().toString(), "[JSESSIONID]");
    }


    @Test
    @DisplayName("a hello test")
    void greetingTest() {
        System.out.println("cookies key2: " + cookies.keySet());
        String url = "http://localhost:" + port + "/hello/get";
        EntityExchangeResult<String> resp = webTestClient
                .get()
                .uri(url)
                .cookie("JSESSIONID", cookies.get("JSESSIONID").get(0).getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();
        String result = resp.getResponseBody();
        Assertions.assertEquals(result, "Hello World!");
    }
}