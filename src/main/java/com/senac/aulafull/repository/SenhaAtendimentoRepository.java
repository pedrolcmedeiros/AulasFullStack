package com.senac.aulafull.repository;

import com.senac.aulafull.model.SenhaAtendimento;
import com.senac.aulafull.model.StatusSenha;
import com.senac.aulafull.model.TipoSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenhaAtendimentoRepository extends JpaRepository<SenhaAtendimento, Long> {

    Optional<SenhaAtendimento> findFirstByStatusAndTipoOrderByNumeroAsc(StatusSenha status, TipoSenha tipo);
}