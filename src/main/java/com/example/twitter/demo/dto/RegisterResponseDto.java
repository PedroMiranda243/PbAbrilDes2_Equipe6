package com.example.twitter.demo.dto;

import lombok.*;

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
    private String birthDate;
    private String username;
    private String email;
    private String password;
    private String role;
}
