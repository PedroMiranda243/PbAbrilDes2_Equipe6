package com.example.twitter.demo.controller;

import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.security.JwtUserDetails;
import com.example.twitter.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((JwtUserDetails) userDetails).getId();
        Post createdPost = postService.createPost(post.getText(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/home")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/{id}/like")
    public Post likePost(@PathVariable Long id) {
        return postService.likePost(id);
    }

    @PostMapping("/{id}/unlike")
    public Post unlikePost(@PathVariable Long id) {
        return postService.unlikePost(id);
    }

    @PostMapping("/{id}/repost")
    public ResponseEntity<Post> repost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((JwtUserDetails) userDetails).getId();
        Post repost = postService.repost(id, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(repost);
    }
}
