package com.senac.aulafull.controller;
import com.senac.aulafull.dto.LoginRequestDto;
import com.senac.aulafull.services.TokenService;
import com.senac.aulafull.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//A resposabilidade dele é autenticar geral, token, controller

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação controller", description = "Controller responsável pela autenticacao da aplicação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Método responsável por efetuar o login do usuario")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){

        if(!usuarioService.validarSenha(request)){
            return ResponseEntity.badRequest().body("Usuario ou senha inválido!");

        }
        var token = tokenService.gerarToken(request);

        return ResponseEntity.ok(token);
    }


}
