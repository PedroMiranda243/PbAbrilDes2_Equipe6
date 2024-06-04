package com.example.twitter.demo.dto.registerDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterCreateDto {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String summary;

    private LocalDate birthDate;
    @NotBlank
    private String email;

    private String role = "ROLE_USUARIO";

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
