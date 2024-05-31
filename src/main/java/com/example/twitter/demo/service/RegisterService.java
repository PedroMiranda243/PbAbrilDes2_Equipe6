package com.example.twitter.demo.service;

import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.exception.PasswordInvalidException;
import com.example.twitter.demo.exception.UsernameUniqueViolationException;
import com.example.twitter.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Register salvar(Register register) {
        try {
            register.setPassword(passwordEncoder.encode(register.getPassword()));
            return userRepository.save(register);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", register.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Register buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public Register editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        Register register = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, register.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        register.setPassword(passwordEncoder.encode(novaSenha));
        return register;
    }

    @Transactional(readOnly = true)
    public List<Register> buscarTodos() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Register buscarPorUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public Register.Role buscarRolePorUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    @Transactional
    public void excluirPorId(Long id) {
        userRepository.deleteById(id);
    }
}
