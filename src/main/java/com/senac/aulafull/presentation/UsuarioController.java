package com.senac.aulafull.presentation;
import com.senac.aulafull.application.dto.usuario.UsuarioRequestDto;
import com.senac.aulafull.application.dto.usuario.UsuarioResponseDto;
import com.senac.aulafull.application.services.UsuarioService;
import com.senac.aulafull.domain.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controlador de usuários", description = "Camada responsável por controlar os registros de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    @Operation(summary = "Lista de usuarios por id", description = "Metodo responsavel por listar o usuario pelo id")
    public ResponseEntity<UsuarioResponseDto> consultaPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.consultarPorId(id));
    }

    @GetMapping("/listarTodos")
    @Operation(summary = "Todos os usuarios listados", description = "Metodo responsavel por listar todos os usuarios cadastrados")
    public ResponseEntity<List<UsuarioResponseDto>> consultarTodos(){

        return ResponseEntity.ok(usuarioService.consultarTodosSemFiltro());
    }

    @GetMapping("/grid")
    @Operation(summary = "Usuarios grid filtrada",description = "Metodo responsavel por consultar dados do usuario paginado e filtrados")
    public ResponseEntity<List<UsuarioResponseDto>>consultarPaginadoFiltrado(
             @Parameter(description = "Parametro de quantidade de registro por pagina!") @RequestParam Long take,
             @Parameter(description = "Parametro de quantidade de paginas!") @RequestParam Long page,
             @Parameter (description = "Parametro de filtro!") @RequestParam(required = false) String filtro
            ){
        return ResponseEntity.ok(usuarioService.consultarPaginadoFiltrado(take, page, filtro));
    }


    @PostMapping("/registrar")
    @Operation(summary = "Registrar novo usuário", description = "Método responsável por criar um novo usuário")
    public ResponseEntity<UsuarioResponseDto> registrarUsuario(@RequestBody UsuarioRequestDto request){
        try {
            var usuarioSalvo = usuarioService.salvarUsuario(request);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }
    }

}