package com.example.twitter.demo;


import com.example.twitter.demo.dto.RegisterCreateDto;
import com.example.twitter.demo.dto.RegisterLoginDto;
import com.example.twitter.demo.dto.RegisterSenhaDto;
import com.example.twitter.demo.exception.ErrorMessage;
import com.example.twitter.demo.security.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationIT {

    @Autowired
    private WebTestClient testClient;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String pass = "1234";
    RegisterCreateDto exemplo = new RegisterCreateDto("Jurianino", "Magalhaes", "Summary example", LocalDate.now(), "joao@gmail.com", "ROLE_USUARIO", "joaa1", pass);

    @Test
    public void authenticateWasSuccessfulReturnStatus200WIthBearerToken(){
        JwtToken responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void authenticateWithInvalidDataReturnErrorMessageStatus400() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("invalido@email.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("ana@email.com", "000000"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }


    @Test
    public void authenticateWithInvalidUsernameReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void authenticateWithInvalidPasswordReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterLoginDto("ana@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void LogoutWasSuccessFulReturnStatus204() {
        testClient
                .post()
                .uri("/logout")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    public void logoutWithInvalidDataReturnErrorMessageStatus400() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/logout")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "invalido@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }
    @Test
    public void logoutWithInvalidFieldsReturnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "invalido@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

}
