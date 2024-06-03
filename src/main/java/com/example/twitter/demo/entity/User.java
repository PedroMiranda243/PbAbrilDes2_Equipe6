package main.java.com.example.twitter.demo.entity;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import com.example.twitter.demo.entity.Post;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String summary;
    private String username;
    private String email;
    private String password;
    private LocalDate birthdate;
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followers;
    
    @ManyToMany(mappedBy = "followers")
    private List<User> following;
}