package com.example.twitter.demo.service;

import com.example.twitter.demo.dto.postDto.PostDTO;
import com.example.twitter.demo.entity.Comment;
import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.exception.EntityNotFoundException;
import com.example.twitter.demo.repository.CommentRepository;
import com.example.twitter.demo.repository.PostRepository;
import com.example.twitter.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String text, Long userId) {
        Register author = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post = new Post();
        post.setText(text);
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found");
        }
        postRepository.deleteById(id);
    }

    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        List<Comment> comments = getCommentsForPost(id);
        post.setComments(comments);
        return post;
    }

    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post likePost(Long id) {
        Post post = getPostById(id);
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    public Post unlikePost(Long id) {
        Post post = getPostById(id);
        post.setLikes(Math.max(0, post.getLikes() - 1));
        return postRepository.save(post);
    }

    public Post repost(Long originalPostId, Long userId) {
        Post originalPost = getPostById(originalPostId);
        Register author = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post repost = new Post();
        repost.setRepostOf(originalPost);
        repost.setAuthor(author);
        return postRepository.save(repost);
    }

    public PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setText(post.getText());
        dto.setLikes(post.getLikes());
        return dto;
    }
}