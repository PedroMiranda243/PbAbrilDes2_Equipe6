package com.example.twitter.demo.service;

import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.repository.PostRepository;
import com.example.twitter.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, UserDetails userDetails) {
        Register author = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
