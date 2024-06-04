package com.example.twitter.demo;

import com.example.twitter.demo.dto.RegisterCreateDto;
import com.example.twitter.demo.dto.RegisterResponseDto;
import com.example.twitter.demo.dto.RegisterSenhaDto;
import com.example.twitter.demo.exception.ErrorMessage;
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
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    private WebTestClient testClient;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String pass = "1234";
    RegisterCreateDto exemplo = new RegisterCreateDto("Jurianino", "Magalhaes", "Summary example",LocalDate.now(), "joao@gmail.com", "ROLE_USUARIO", "joaa1", pass);


    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
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

    @Test
    public void createUsuario_ComFirstNameInvalido_RetornarErrorMessage422(){
        exemplo.setFirstName("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComLastNameInvalido_RetornarErrorMessage422(){
        exemplo.setLastName("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComSummaryInvalido_RetornarErrorMessage422(){
        exemplo.setSummary("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComEmailInvalido_RetornarErrorMessage422(){
        exemplo.setEmail("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorMessage422(){
        exemplo.setUsername("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorMessage422(){
        exemplo.setPassword("");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComEmailRepetido_RetornarErrorMessage409(){
       exemplo.setEmail("Jolano@gmail.com");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createUsuario_ComUsernameRepetido_RetornarErrorMessage409(){
        exemplo.setUsername("joaoaa");
        ErrorMessage responseBody = testClient
                .post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exemplo)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }


    @Test
    public void GetUserByIdReturnUserWithStatus200(){
        RegisterResponseDto responseBody = testClient
                .get()
                .uri("/register/12")
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getFirstName()).isEqualTo("Joao");
        org.assertj.core.api.Assertions.assertThat(responseBody.getLastName()).isEqualTo("Silva");
        org.assertj.core.api.Assertions.assertThat(responseBody.getSummary()).isEqualTo("Usuario cadastro Joao");
        org.assertj.core.api.Assertions.assertThat(responseBody.getBirthDate()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("Jolano@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("USUARIO");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("joaosilva");
        //org.assertj.core.api.Assertions.assertThat(passwordEncoder.matches("senha123", responseBody.getPassword())).isEqualTo(true);
        org.assertj.core.api.Assertions.assertThat(responseBody.getPassword()).isEqualTo("senha123");
    }

    @Test
    public void GetUserByIdWithoutAuthorizationReturnErrorMessageStatus403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "carla@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void GetUserByIdWithNonExistentUserReturnErrorMessageStatus404NotFound(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/register/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void editPassworWithValidDataReturnStatus204() {
        testClient
                .patch()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "Jolano@gmail.com@gmail.com", "senha123"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterSenhaDto("senha123", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    public void editPasswordWithInvalidDataReturnStatus400() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterSenhaDto("senha123", "123456", "000000"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    public void editPasswordWithWrongFormatFieldsReturnStatus400() {
    exemplo.setEmail("");
    exemplo.setFirstName("");
    ErrorMessage responseBody = testClient
            .patch()
            .uri("register/12")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, " ana@emaasaail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(exemplo)
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void getUsersWithAuthorizationReturnUsersListWithStatus204() {
        List<RegisterResponseDto> responseBody = testClient
                .get()
                .uri("/register")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RegisterResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }

    @Test
    public void listarUsuarios_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/register")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void getByIdWithoutAuthorizationReturnStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePasswordWithoutAuthorizationReturnStatus403() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterSenhaDto("senha123", "123456", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void deleteUserWithValidDataReturnStatus204(){
        testClient
                .delete()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "senha123"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void deleteUserWithouthValidDataReturnErrorMessage403(){
        ErrorMessage responseBody = testClient
                .patch()
                .uri("/register/12")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RegisterSenhaDto("413241", "123456", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}
