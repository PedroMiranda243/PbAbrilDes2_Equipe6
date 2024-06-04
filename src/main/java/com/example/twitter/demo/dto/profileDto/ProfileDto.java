package com.example.twitter.demo.dto.profileDto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class ProfileDto {

    private Long id;
    private String fullName;
    private String username;
    private String summary;
    private List<Long> follows;
    private List<Long> followers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
