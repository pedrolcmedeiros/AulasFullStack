package com.senac.aulafull.domain.repository;

import com.senac.aulafull.domain.enuns.StatusSenha;
import com.senac.aulafull.domain.entities.SenhaAtendimento;
import com.senac.aulafull.domain.enuns.TipoSenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// A interface estende JpaRepository para ter acesso a métodos CRUD básicos
public interface SenhaAtendimentoRepository extends JpaRepository<SenhaAtendimento, Long> {

    Optional<SenhaAtendimento> findFirstByStatusAndTipoOrderByNumeroAsc(StatusSenha status, TipoSenha tipo);
    List<SenhaAtendimento> findByStatusIn(List<StatusSenha> statuses);
    List<SenhaAtendimento> findByStatus(StatusSenha status);
}