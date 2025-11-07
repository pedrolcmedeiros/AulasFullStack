package com.senac.aulafull.domain.repository;

import com.senac.aulafull.domain.entities.Empresa;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository {
    Empresa save(Empresa empresa);
    Optional<Empresa> findById(UUID id);
    // Outros métodos de busca
}
