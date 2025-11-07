package com.senac.aulafull.presentation;
import com.senac.aulafull.domain.entities.SenhaAtendimento;
import com.senac.aulafull.domain.enuns.TipoSenha;
import com.senac.aulafull.application.services.SenhaAtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/senhas")
@Tag(name = "Controlador de Senhas de Atendimento", description = "Endpoints para gerenciar a fila de senhas")
public class SenhaAtendimentoController {

    @Autowired
    private SenhaAtendimentoService senhaAtendimentoService;

    // Ação: Gerar Senha (Permitido para PACIENTE e ATENDENTE)
    @PostMapping("/gerar/{tipo}")
    @Operation(summary = "Gerar nova senha", description = "Cria uma nova senha na fila (NORMAL ou PREFERENCIAL)")
    @PreAuthorize("hasRole('PACIENTE') or hasRole('ATENDENTE')")
    public ResponseEntity<SenhaAtendimento> gerarSenha(@PathVariable TipoSenha tipo) {
        SenhaAtendimento novaSenha = senhaAtendimentoService.gerarSenha(tipo);
        //Retornar DTOs

        return ResponseEntity.ok(novaSenha);
    }

    // Ação: Chamar Próxima Senha (Permitido APENAS para ATENDENTE)
    @GetMapping("/chamar")
    @Operation(summary = "Chamar próxima senha", description = "Chama a próxima senha da fila, priorizando as preferenciais")
    @PreAuthorize("hasRole('ATENDENTE')")
    public ResponseEntity<?> chamarProximaSenha() {
        Optional<SenhaAtendimento> proximaSenha = senhaAtendimentoService.chamarProximaSenha();

        if (proximaSenha.isPresent()) {
            return ResponseEntity.ok(proximaSenha.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Ação: Finalizar Atendimento (Permitido APENAS para ATENDENTE)
    @PutMapping("/finalizar/{id}")
    @Operation(summary = "Finalizar atendimento", description = "Altera o status de uma senha para 'ATENDIDA'")
    @PreAuthorize("hasRole('ATENDENTE')")
    public ResponseEntity<?> finalizarAtendimento(@PathVariable Long id) {
        Optional<SenhaAtendimento> senhaFinalizada = senhaAtendimentoService.finalizarAtendimento(id);

        if (senhaFinalizada.isPresent()) {
            return ResponseEntity.ok(senhaFinalizada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Ação: Listar Senhas Ativas (AGUARDANDO e CHAMADA)
    @GetMapping("/listar/ativas")
    @Operation(summary = "Listar senhas ativas", description = "Retorna uma lista de senhas 'AGUARDANDO' ou 'CHAMADA'")
    @PreAuthorize("hasRole('ATENDENTE')")
    public ResponseEntity<List<SenhaAtendimento>> listarSenhasAtivas() {
        List<SenhaAtendimento> atendimentos = senhaAtendimentoService.listarSenhasAtivas();

        //Retornar DTOs  List<SenhaAtendimento> atendimentos = senhaAtendimentoService.listarSenhasAtivas();

        return ResponseEntity.ok(atendimentos);
    }

    // Ação: Listar Senhas Atendidas/Finalizadas (ATENDIDA)
    @GetMapping("/listar/atendidas")
    @Operation(summary = "Listar senhas atendidas", description = "Retorna um histórico das últimas senhas com status 'ATENDIDA'")
    @PreAuthorize("hasRole('ATENDENTE')")
    public ResponseEntity<List<SenhaAtendimento>> listarSenhasAtendidas() {
        // AGORA CORRIGIDO: Chamando o método do Service
        List<SenhaAtendimento> atendimentosFinalizados = senhaAtendimentoService.listarSenhasAtendidas();
        //Retornar DTOs
        return ResponseEntity.ok(atendimentosFinalizados);
    }
}