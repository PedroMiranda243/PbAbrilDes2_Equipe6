package com.example.twitter.demo.repository;

import com.example.twitter.demo.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Register, Long> {

        Optional<Register> findByUsername(String username);

        @Query("select u.role from Register u where u.username like :username")
        Register.Role findRoleByUsername(String username);
    }

