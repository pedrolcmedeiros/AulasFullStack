package com.senac.aulafull.controller;

import com.senac.aulafull.model.SenhaAtendimento;
import com.senac.aulafull.model.TipoSenha;
import com.senac.aulafull.services.SenhaAtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/senhas")
@Tag(name = "Controlador de Senhas de Atendimento", description = "Endpoints para gerenciar a fila de senhas")
public class SenhaAtendimentoController {

    @Autowired
    private SenhaAtendimentoService senhaAtendimentoService;

    @PostMapping("/gerar/{tipo}")
    @Operation(summary = "Gerar nova senha", description = "Cria uma nova senha na fila (NORMAL ou PREFERENCIAL)")
    public ResponseEntity<SenhaAtendimento> gerarSenha(@PathVariable TipoSenha tipo) {
        SenhaAtendimento novaSenha = senhaAtendimentoService.gerarSenha(tipo);
        return ResponseEntity.ok(novaSenha);
    }

    @GetMapping("/chamar")
    @Operation(summary = "Chamar próxima senha", description = "Chama a próxima senha da fila, priorizando as preferenciais")
    public ResponseEntity<?> chamarProximaSenha() {
        Optional<SenhaAtendimento> proximaSenha = senhaAtendimentoService.chamarProximaSenha();

        if (proximaSenha.isPresent()) {
            return ResponseEntity.ok(proximaSenha.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/finalizar/{id}")
    @Operation(summary = "Finalizar atendimento", description = "Altera o status de uma senha para 'ATENDIDA'")
    public ResponseEntity<?> finalizarAtendimento(@PathVariable Long id) {
        Optional<SenhaAtendimento> senhaFinalizada = senhaAtendimentoService.finalizarAtendimento(id);

        if (senhaFinalizada.isPresent()) {
            return ResponseEntity.ok(senhaFinalizada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}