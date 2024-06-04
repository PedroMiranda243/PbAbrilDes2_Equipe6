package com.example.twitter.demo.dto.registerDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterSenhaDto {
    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
    @NotBlank
    private String confirmaSenha;
}
