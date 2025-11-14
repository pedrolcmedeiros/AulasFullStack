package com.senac.aulafull.application.services;
import com.senac.aulafull.application.dto.empresa.EmpresaResponseDto;
import com.senac.aulafull.domain.entities.Empresa;
import com.senac.aulafull.domain.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.senac.aulafull.application.dto.empresa.EmpresaRequestDto;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }


    @Transactional
    public EmpresaResponseDto cadastrarNovaEmpresa(EmpresaRequestDto dto) {
        try{
            Empresa novaEmpresa = new Empresa(dto.nome(), dto.cnpj());

            Empresa empresaSalva = empresaRepository.save(novaEmpresa);

            return new EmpresaResponseDto(empresaSalva.getId(), empresaSalva.getNome());

        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar empresa, verifique o nome e cpnj duplicado!");
        }

    }
}