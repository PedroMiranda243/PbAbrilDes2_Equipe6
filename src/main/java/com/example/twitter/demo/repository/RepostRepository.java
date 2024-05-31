package com.example.twitter.demo.repository;

import com.example.twitter.demo.entity.Repost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepostRepository extends JpaRepository<Repost, Long> {
}
