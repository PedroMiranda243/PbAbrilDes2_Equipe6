package com.example.twitter.demo.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class RegisterLoginDto {

    private String username;
    private String password;
}