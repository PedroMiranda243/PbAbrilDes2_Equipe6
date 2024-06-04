package com.example.twitter.demo.dto.registerDto;

import com.example.twitter.demo.dto.postDto.PostResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<PostResponseDto> posts;
    private String email;
    private String password;
    private String role;
}
