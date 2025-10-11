package com.senac.aulafull.presentation;

import com.senac.aulafull.application.dto.login.LoginRequestDto;
import com.senac.aulafull.application.dto.login.LoginResponseDto;
import com.senac.aulafull.application.services.TokenService;
import com.senac.aulafull.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/login")
    @Operation(summary = "Login", description = "Método responsável por efetuar o login do usuario")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        try{
            if(!usuarioService.login(request)){
                // Se o login falhar no service, retorna 401
                return ResponseEntity.status(401).body("Usuário ou senha inválido!");
            }


            var result = tokenService.gerarToken(request);

            // Retorna o DTO com o token E a role
            return ResponseEntity.ok(new LoginResponseDto(result.getToken(), result.getRole()));

        }catch (Exception e){
            return ResponseEntity.status(401).body("Usuário ou senha inválido!");
        }
    }
}
