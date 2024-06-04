package com.example.twitter.demo.dto.postDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class PostDTO {

    @Id
    private Long id;
    private String text;
    private int likes;
    // Outros campos relevantes

    // Getters e setters
}

