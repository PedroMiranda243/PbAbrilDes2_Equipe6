package com.example.twitter.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    @JsonProperty("id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonProperty("authorId")
    private Register author;

    @Column(nullable = false)
    private String text;

    @JsonProperty("likes")
    private int likes;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonProperty("reposts")
    private List<Repost> reposts;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonProperty("numberComments")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repost_of_id")
    private Post repostOf;

}
