package com.example.twitter.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Register follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private Register followed;

    @Column(name = "followed_at")
    private LocalDateTime followedAt;
}
