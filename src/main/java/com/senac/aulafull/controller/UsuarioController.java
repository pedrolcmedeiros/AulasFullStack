package com.senac.aulafull.controller;

import com.senac.aulafull.model.Usuario;
import com.senac.aulafull.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controlador de usuários", description = "Camada responsável por controlar os registros de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> consultaPorId(@PathVariable Long id){
        var usuario = usuarioRepository.findById(id).orElse(null);//orElse(null) permite responder qualquer coisa

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);

    }
    @GetMapping
    @Operation(summary = "usuarios", description = "Método responsavel de calcular os custos da folha de pag e apos faz os lancamentos contabeis na tabela: x")
    public ResponseEntity<?> consultarTodos(){

        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping
    @Operation
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario){

        try {

            var usuarioResponse = usuarioRepository.save(usuario);

            return ResponseEntity.ok(usuarioResponse);

        }catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }


}



