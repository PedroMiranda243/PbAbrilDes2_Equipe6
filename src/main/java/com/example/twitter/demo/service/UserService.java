package main.java.com.example.twitter.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import main.java.com.example.twitter.demo.entity.User;
import main.java.com.example.twitter.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUserBio(Long userId, String summary) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSummary(summary);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean followUser(Long userId, Long followUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followUserOptional = userRepository.findById(followUserId);
        if (userOptional.isPresent() && followUserOptional.isPresent()) {
            User user = userOptional.get();
            User followUser = followUserOptional.get();
            user.getFollowing().add(followUser);
            followUser.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(followUser);
            return true;
        }
        return false;
    }

    public boolean unfollowUser(Long userId, Long unfollowUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> unfollowUserOptional = userRepository.findById(unfollowUserId);
        if (userOptional.isPresent() && unfollowUserOptional.isPresent()) {
            User user = userOptional.get();
            User unfollowUser = unfollowUserOptional.get();
            user.getFollowing().remove(unfollowUser);
            unfollowUser.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(unfollowUser);
            return true;
        }
        return false;
    }

    public Optional<User> getUserProfile(Long userId) {
        return userRepository.findById(userId);
    }
}