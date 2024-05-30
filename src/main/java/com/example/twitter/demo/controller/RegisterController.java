package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.RegisterDto;
import com.example.twitter.demo.entity.Register;
import com.example.twitter.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Register> criar(@RequestBody RegisterDto registerDto){
        Register register = registerService.criar(registerDto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterDto> getById(@PathVariable Long id) {
        Register register = registerService.getById(id);
        if (register != null) {
            RegisterDto registerDto = new RegisterDto();
            registerDto.setId(register.getId());
            registerDto.setFirstName(register.getFirstName());
            registerDto.setLastName(register.getLastName());
            registerDto.setSummary(register.getSummary());
            registerDto.setBirthdate(register.getBirthdate());
            registerDto.setUsername(register.getUsername());
            registerDto.setEmail(register.getEmail());
            registerDto.setPassword(register.getPassword());
            return new ResponseEntity<>(registerDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegisterDto>> getAllRegisters() {
        List<Register> registers = registerService.getAllRegisters();
        List<RegisterDto> registerDtos = new ArrayList<>();

        for (Register register : registers) {
            RegisterDto registerDto = new RegisterDto();
            registerDto.setId(register.getId());
            registerDto.setFirstName(register.getFirstName());
            registerDto.setLastName(register.getLastName());
            registerDto.setSummary(register.getSummary());
            registerDto.setBirthdate(register.getBirthdate());
            registerDto.setUsername(register.getUsername());
            registerDto.setEmail(register.getEmail());
            registerDto.setPassword(register.getPassword());
            registerDtos.add(registerDto);
        }

        return new ResponseEntity<>(registerDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegisterById(@PathVariable Long id) {
        boolean deleted = registerService.deleteRegisterById(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Register with ID " + id + " has been deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Register with ID " + id + " not found");
        }
    }
}
