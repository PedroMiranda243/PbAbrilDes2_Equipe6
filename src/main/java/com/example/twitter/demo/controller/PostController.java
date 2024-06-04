package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.mapper.PostMapper;
import com.example.twitter.demo.dto.postDto.PostCreateDto;
import com.example.twitter.demo.dto.postDto.PostResponseDto;
import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateDto createDto) {
        Post post = postService.createPost(createDto, createDto.getAuthorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toResponseDTO(post));
    }
}
