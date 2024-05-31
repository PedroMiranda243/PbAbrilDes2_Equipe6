package com.example.twitter.demo.controller;

import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(post, userDetails);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/home")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
