package com.example.twitter.demo.dto.registerDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String summary;
    private LocalDate birthDate;
    private String username;
    private String email;
    private String password;
    private String role;
}
