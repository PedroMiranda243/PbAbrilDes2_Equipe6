package com.example.twitter.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String summary;
    private LocalDate birthdate;
    private String username;
    private String email;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
