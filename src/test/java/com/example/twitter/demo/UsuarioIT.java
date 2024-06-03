package com.example.twitter.demo;

import com.example.twitter.demo.dto.RegisterCreateDto;
import com.example.twitter.demo.dto.RegisterResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @LocalServerPort
    private int port;

    private WebTestClient testClient;

    @BeforeEach
    public void setup() {
        this.testClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        RegisterResponseDto responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterCreateDto("test@gmail.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RegisterResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("test@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ROLE_USUARIO");
    }
}
