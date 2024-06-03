package main.java.com.example.twitter.demo.controller;

import java.util.Optional;
import main.java.com.example.twitter.demo.entity.User;
import main.java.com.example.twitter.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if(userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if(userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already in use");
        }
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserProfile(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> followUser(@RequestHeader("Authorization") String token, @PathVariable Long userId) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userService.followUser(user.getId(), userId)) {
                return ResponseEntity.ok("Followed successfully");
            }
            return ResponseEntity.badRequest().body("Unable to follow user");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @PostMapping("/unfollow/{userId}")
    public ResponseEntity<?> unfollowUser(@RequestHeader("Authorization") String token, @PathVariable Long userId) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userService.unfollowUser(user.getId(), userId)) {
                return ResponseEntity.ok("Unfollowed successfully");
            }
            return ResponseEntity.badRequest().body("Unable to unfollow user");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }
}