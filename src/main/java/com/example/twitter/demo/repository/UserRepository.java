package main.java.com.example.twitter.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import main.java.com.example.twitter.demo.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}