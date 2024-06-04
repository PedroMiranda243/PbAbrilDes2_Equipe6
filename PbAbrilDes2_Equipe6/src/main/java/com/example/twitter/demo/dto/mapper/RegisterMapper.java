package com.example.twitter.demo.dto.mapper;

import com.example.twitter.demo.dto.registerDto.RegisterCreateDto;
import com.example.twitter.demo.dto.registerDto.RegisterResponseDto;
import com.example.twitter.demo.entity.Register;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterMapper {

    public static Register toUsuario(RegisterCreateDto createDto) {
        return new ModelMapper().map(createDto, Register.class);
    }

    public static RegisterResponseDto toDto(Register register) {
        String role = register.getRole().name().substring("ROLE_".length());
        PropertyMap<Register, RegisterResponseDto> props = new PropertyMap<Register, RegisterResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(register, RegisterResponseDto.class);
    }

    public static List<RegisterResponseDto> toListDto(List<Register> registers) {
        return registers.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
