package com.senac.aulafull.controller;

import com.senac.aulafull.dto.LoginRequestDto;
import com.senac.aulafull.services.TokenService;
import com.senac.aulafull.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação controller", description = "Controller responsável pela autenticação da aplicação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    // Injete o AuthenticationManager diretamente no AuthController
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Método responsável por efetuar o login do usuario")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        try{
            // Use o AuthenticationManager para autenticar
            var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
            var auth = authenticationManager.authenticate(usernamePassword);

            // Se a autenticação for bem-sucedida, gere o token
            var token = tokenService.gerarToken(request);

            return ResponseEntity.ok(token);

        }catch (Exception e){
            // Retorne um erro 401 para credenciais inválidas
            return ResponseEntity.status(401).body("Usuário ou senha inválido!");
        }
    }
}