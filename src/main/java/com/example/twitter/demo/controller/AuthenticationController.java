package com.example.twitter.demo.controller;

import com.example.twitter.demo.dto.RegisterLoginDto;
import com.example.twitter.demo.dto.RegisterResponseDto;
import com.example.twitter.demo.exception.ErrorMessage;
import com.example.twitter.demo.security.JwtToken;
import com.example.twitter.demo.security.JwtUserDatailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthenticationController {

    private final JwtUserDatailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(
        summary ="Autenticar pelo login da API", description = "Recurso de autenticação da API",
            responses ={
                @ApiResponse(responseCode = "200", description = "autenticação realizada com sucesso e retorno de um bearer token",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponseDto.class))),
                @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Campo(s) Invalido(s)",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PostMapping("login")
    public ResponseEntity<?> autenticar(@RequestBody @Valid RegisterLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.getUsername());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }

    //Campos comentados em espera de autorização do recurso
    @Operation(
            summary ="Realização de Logout", description = "Recurso realização de Logout da API|Acesso restrito a Admin|Cliente",
            //security = @SecurityRequirement(name = "security"),
            responses ={
                    @ApiResponse(responseCode = "204", description = "Logout realizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    //@ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                    //        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s) Invalido(s)",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null){
            session.invalidate();
            log.info("Logout realizado com sucesso");
            return ResponseEntity.ok().build();
        }else {
            log.warn("Nenhuma sessão encontrada para logout");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não autenticado");
        }
    }
}
