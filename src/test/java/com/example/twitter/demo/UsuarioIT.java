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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "1234";
        RegisterCreateDto exemplo = new RegisterCreateDto(12L,"Joao", "Silva", "Summary example",LocalDate.now(), "joao@gmail.com", "ROLE_USUARIO", "joaa1", pass);
        RegisterResponseDto responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RegisterResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("Joao");
        org.assertj.core.api.Assertions.assertThat(responseBody.getLastName()).isEqualTo("Silva");
        org.assertj.core.api.Assertions.assertThat(responseBody.getSummary()).isEqualTo("Summary example");
        org.assertj.core.api.Assertions.assertThat(responseBody.getBirthDate()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("joao@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("USUARIO");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("joaa1");
        org.assertj.core.api.Assertions.assertThat(passwordEncoder.matches(exemplo.getPassword(), responseBody.getPassword())).isEqualTo(true);
    }
}
