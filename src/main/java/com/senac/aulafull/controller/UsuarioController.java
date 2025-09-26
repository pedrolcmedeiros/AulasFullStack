package com.senac.aulafull.controller;

import com.senac.aulafull.dto.UsuarioRequestDto;
import com.senac.aulafull.repository.UsuarioRepository;
import com.senac.aulafull.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controlador de usuários", description = "Camada responsável por controlar os registros de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Métodos de consulta existentes (mantidos, mas observem que ainda retornam a entidade. O ideal seria retornar DTOs)
    @GetMapping("/{id}")
    public ResponseEntity<?> consultaPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioRepository.findById(id).orElse(null));
    }
    @GetMapping
    public ResponseEntity<?> consultarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar novo usuário", description = "Método responsável por criar um novo usuário na aplicação")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRequestDto request){
        try {
            var usuarioSalvo = usuarioService.registrarUsuario(request);
            // Em uma implementação ideal, você retornaria um UsuarioResponseDto
            return ResponseEntity.ok(usuarioSalvo);
        } catch (Exception e ){
            return ResponseEntity.badRequest().body("Erro ao registrar usuário: " + e.getMessage());
        }
    }
}