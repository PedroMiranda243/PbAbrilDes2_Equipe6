package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.RegisterCreateDto;
import com.example.twitter.demo.dto.RegisterResponseDto;
import com.example.twitter.demo.dto.RegisterSenhaDto;
import com.example.twitter.demo.dto.mapper.RegisterMapper;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<RegisterResponseDto> create(@Valid @RequestBody RegisterCreateDto createDto) {
        Register register = registerService.salvar(RegisterMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterMapper.toDto(register));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterResponseDto> getById(@PathVariable Long id) {
        Register register = registerService.buscarPorId(id);
        return ResponseEntity.ok(RegisterMapper.toDto(register));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody RegisterSenhaDto dto) {
        registerService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RegisterResponseDto>> getAll() {
        List<Register> registers = registerService.buscarTodos();
        return ResponseEntity.ok(RegisterMapper.toListDto(registers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        registerService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }
}
