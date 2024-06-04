package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.profileDto.ProfileDto;
import com.example.twitter.demo.dto.registerDto.RegisterCreateDto;
import com.example.twitter.demo.dto.registerDto.RegisterResponseDto;
import com.example.twitter.demo.dto.registerDto.RegisterSenhaDto;
import com.example.twitter.demo.dto.mapper.ProfileMapper;
import com.example.twitter.demo.dto.mapper.RegisterMapper;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.exception.ErrorMessage;
import com.example.twitter.demo.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private RegisterService registerService;

    @Operation(
            summary ="Criar um novo usuario", description = "Recurso de criação de usuário da API",
            responses ={
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "email ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "O recurso processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> create(@Valid @RequestBody RegisterCreateDto createDto) {
        Register register = registerService.salvar(RegisterMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterMapper.toDto(register));
    }

    @Operation(
            summary ="Recuperação de usuário por Id", description = "Recurso de recuperação de usuário por Id da API",
            security = @SecurityRequirement(name = "security"),
            responses ={
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<RegisterResponseDto> getById(@PathVariable Long id) {
        Register register = registerService.buscarPorId(id);
        return ResponseEntity.ok(RegisterMapper.toDto(register));
    }

    @Operation(
            summary ="Atualização de senha", description = "Recurso de atualização de senha, recurso necessita de um token Bearer." +
            "Acesso restrito a Admin|Cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody RegisterSenhaDto dto) {
        registerService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os usuários cadastrados", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RegisterResponseDto.class))))
                    ,@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/all")
    public ResponseEntity<List<RegisterResponseDto>> getAll() {
        List<Register> registers = registerService.buscarTodos();
        return ResponseEntity.ok(RegisterMapper.toListDto(registers));
    }

    @Operation(
            summary ="Exclusão de usuário por Id", description = "Recurso de exclusão de usuário por Id da API." +
            "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses ={
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
                    ,@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        registerService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable Long id) {
        Register register = registerService.buscarPorId(id);
        ProfileDto profileDto = ProfileMapper.toDto(register);
        return ResponseEntity.ok(profileDto);
    }

    @PostMapping("/follow/{userId}/{followerId}")
    public ResponseEntity<Void> followUser(@PathVariable Long userId, @PathVariable Long followerId) {
        registerService.follow(userId, followerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/unfollow/{userId}/{followerId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId, @PathVariable Long followerId) {
        registerService.unfollow(userId, followerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
