package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.postDto.PostDTO;
import com.example.twitter.demo.entity.Post;
import com.example.twitter.demo.exception.ErrorMessage;
import com.example.twitter.demo.security.JwtUserDetails;
import com.example.twitter.demo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(
            summary ="Criar um novo Post", description = "Recurso de criação de Post da API",
            responses ={
                    @ApiResponse(responseCode = "201", description = "Post criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((JwtUserDetails) userDetails).getId();
        Post createdPost = postService.createPost(postDTO.getText(), userId);
        PostDTO createdPostDTO = mapToDTO(createdPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostDTO);
    }

    @Operation(
            summary ="Deletar um Post", description = "Recurso de exclusão de Post da API",
            responses ={
                    @ApiResponse(responseCode = "204", description = "Post deletado com sucesso"),
                    @ApiResponse(responseCode = "422", description = "Recurso processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary ="Recuperar um Post", description = "Recurso de recuperação de Post da API",
            responses ={
                    @ApiResponse(responseCode = "200", description = "Post recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @Operation(summary = "Listar todos os posts do usuario", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os posts do usuario",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PostDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/home")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/{id}/like")
    public Post likePost(@PathVariable Long id) {
        return postService.likePost(id);
    }

    @PostMapping("/{id}/unlike")
    public Post unlikePost(@PathVariable Long id) {
        return postService.unlikePost(id);
    }

    @PostMapping("/{id}/repost")
    public ResponseEntity<Post> repost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((JwtUserDetails) userDetails).getId();
        Post repost = postService.repost(id, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(repost);
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setText(post.getText());
        dto.setLikes(post.getLikes());
        return dto;
    }
}