package com.example.twitter.demo.repository;

import com.example.twitter.demo.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Register, Long> {
    Optional<Register> findByUsername(String username);

    @Query("SELECT r.role FROM Register r WHERE r.username = :username")
    Register.Role findRoleByUsername(@Param("username") String username);
}