package com.example.twitter.demo.repository;

import com.example.twitter.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Post, Long> {
}
