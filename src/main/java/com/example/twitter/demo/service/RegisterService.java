package com.example.twitter.demo.service;

import com.example.twitter.demo.dto.RegisterDto;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.repository.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    public Register criar(RegisterDto registerDTO) {
        Register register = new Register();
        register.setFirstName(registerDTO.getFirstName());
        register.setLastName(registerDTO.getLastName());
        register.setSummary(registerDTO.getSummary());
        register.setBirthdate(registerDTO.getBirthdate());
        register.setUsername(registerDTO.getUsername());
        register.setEmail(registerDTO.getEmail());
        register.setPassword(registerDTO.getPassword());

        return registerRepository.save(register);
    }

    public Register getById(Long id) {
        return registerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada" + id));
    }

    public List<Register> getAllRegisters() {
        return registerRepository.findAll();
    }

    public boolean deleteRegisterById(Long id) {
        if (registerRepository.existsById(id)) {
            registerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
