package com.example.twitter.demo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;


public class TestConfig {
    @LocalServerPort
    private int port;

    private WebTestClient testClient;

    @BeforeEach
    public void setup() {
        this.testClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }
}
