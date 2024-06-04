package com.example.twitter.demo.service;

import com.example.twitter.demo.dto.postDto.PostCreateDto;
import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.repository.PostRepository;
import com.example.twitter.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(@Valid PostCreateDto postDto, Long authorId) {
        // Verifica se o autor do post existe no banco de dados
        Optional<Register> optionalAuthor = userRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new IllegalArgumentException("Autor não encontrado com o ID fornecido: " + authorId);
        }

        Register author = optionalAuthor.get();

        // Cria um objeto Post a partir do PostCreateDto
        Post post = new Post();
        post.setText(postDto.getText());
        // Define o autor do post
        post.setAuthor(author);

        // Salva o post no banco de dados
        return postRepository.save(post);
    }

    // Outros métodos do serviço...
}
