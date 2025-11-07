package com.senac.aulafull.presentation;

import com.senac.aulafull.application.dto.empresa.EmpresaResponseDto;
import com.senac.aulafull.application.services.EmpresaService;
import com.senac.aulafull.domain.entities.Empresa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senac.aulafull.application.dto.empresa.EmpresaRequestDto;

@RestController
@RequestMapping("/v1/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;


    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> cadastrarEmpresa(@RequestBody EmpresaRequestDto dto) {

        EmpresaResponseDto responseDto = empresaService.cadastrarNovaEmpresa(dto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}